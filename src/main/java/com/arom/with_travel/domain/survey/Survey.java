package com.arom.with_travel.domain.survey;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE survey SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Survey 엔티티가 answers 문자열 리스트를 갖고 있고, 이 값들을 별도의 테이블에 저장하도록 리팩토링
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "survey_answers",
            joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "answer")
    private List<String> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Survey create(Member member,List<String> answers) {
        validateAnswers(answers);

        Survey survey = new Survey();
        survey.member = member;
        survey.answers = answers;
        return survey;
    }

    private static void validateAnswers(List<String> answers){
        if(answers == null || answers.isEmpty())
            throw BaseException.from(ErrorCode.INVALID_SURVEY_ANSWER);
    }
}
