package com.arom.with_travel.global.exception.handler;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                           HttpServletRequest request) {
        log.warn("❗ 잘못된 HTTP 메서드 요청 - [{}] {} -> {}", ex.getMethod(), request.getRequestURI(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("지원하지 않는 HTTP 메서드입니다: " + request.getRequestURI());
    }


    // HTTP Method 불일치 에러
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ErrorResponse.generateFrom(ErrorCode.METHOD_NOT_ALLOWED);
    }

    // @RequestBody JSON 파싱 에러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleJsonParseException(HttpMessageNotReadableException e) {
        return ErrorResponse.generateFrom(ErrorCode.INVALID_JSON_FORMAT);
    }

    // 필수 @RequestParam 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handleMissingParam(MissingServletRequestParameterException e) {
        return ErrorResponse.generateFrom(ErrorCode.MISSING_PARAMETER);
    }

    // @RequestParam 타입 불일치
    @ExceptionHandler(TypeMismatchException.class)
    public ErrorResponse handleTypeMismatchException(TypeMismatchException e) {
        return ErrorResponse.generateFrom(ErrorCode.INVALID_PARAMETER_TYPE);
    }

    // 데이터 무결성 위반
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ErrorResponse.generateFrom(ErrorCode.ERR_DATA_INTEGRITY_VIOLATION);
    }

    @ExceptionHandler(BaseException.class)
    public ErrorResponse onThrowException(BaseException baseException) {
        return ErrorResponse.generateFrom(baseException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse onThrowException(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        return ErrorResponse.generateWithCustomMessage(ErrorCode.REQ_BODY_ERROR, message);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ErrorResponse onThrowException(HandlerMethodValidationException exception){
        String message = exception
                .getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Validation Failed");
        return ErrorResponse.generateWithCustomMessage(ErrorCode.REQ_PARAMS_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(Exception e) {
        return ErrorResponse.generateFrom(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
