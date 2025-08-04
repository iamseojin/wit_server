//package com.arom.with_travel.global.oauth2.util;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.util.SerializationUtils;
//
//import java.util.Base64;
//import java.util.Optional;
//
//// 차현철
//public class CookieUtil {
//    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(name)) {
//                    return Optional.of(cookie);
//                }
//            }
//        }
//        return Optional.empty();
//    }
//
//    // 요청값(이름, 값, 만료기간)을 바탕으로 쿠키 추가
//    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath("/");
//        cookie.setMaxAge(maxAge);
//        response.addCookie(cookie);
//    }
//
//    // 쿠키 이름으로 쿠키 삭제
//    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(name)) {
//                    cookie.setMaxAge(0);
//                    cookie.setValue("");
//                    cookie.setPath("/");
//                    response.addCookie(cookie);
//                }
//            }
//        }
//    }
//
//    // 객체를 직렬화해 쿠키 값으로 변환
//    public static String serialize(Object object) {
//        return Base64.getUrlEncoder()
//                .encodeToString(SerializationUtils.serialize(object));
//    }
//
//    // 객체를 역직렬화해 객체로 변환
//    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
//        return cls.cast(
//                SerializationUtils.deserialize(
//                        Base64.getUrlDecoder().decode(cookie.getValue())
//                )
//        );
//    }
//}
