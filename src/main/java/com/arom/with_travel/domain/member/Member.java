package com.arom.with_travel.domain.member;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.chat.model.Chat;
import com.arom.with_travel.domain.chat.model.ChatPart;
import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community_reply.CommunityReply;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.domain.shorts_reply.ShortsReply;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.global.entity.BaseEntity;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String oauthId;

    private String email;

    private LocalDate birth;
    @Enumerated(EnumType.STRING) private Gender gender;
    private String phone;
    @Enumerated(EnumType.STRING) private LoginType loginType;
    private String name;

    @Column(unique = true)
    private String nickname;
    private String introduction;
    @Enumerated(EnumType.STRING) private TravelType travelType;
    @Enumerated(EnumType.STRING) private Role role;
    private Boolean additionalDataChecked = false;

    public enum Role {
        USER,
        GUEST
    }

    public enum TravelType {
        USER,
        ADMIN
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum LoginType {
        KAKAO
    }

    public Member(String memberName, String email, Role role) {
        super();
    }

    private Member(String name, String email, String oauthId){
        this.name    = name;
        this.email   = email;
        this.oauthId = oauthId;
        this.role = Role.GUEST;
    }

    public static Member create(String memberName, String email, Role role) {
        return new Member(memberName, email, role);
    }

    public static Member create(String memberName, String email, String oauthId) {
        return new Member(memberName, email, oauthId);
    }

    @OneToMany(mappedBy = "member")
    private List<Shorts> shorts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ShortsReply> shortsReply = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Accompany> accompanies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AccompanyApply> accompanyApplies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommunityReply> communityReplies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatPart> chatParts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Image image;

    @Builder
    public Member(Long id, String oauthId, String email, String name, LocalDate birth, Gender gender,
                  String phone, LoginType loginType, String nickname, String introduction,
                  TravelType travelType, Role role) {
        this.id = id;
        this.oauthId = oauthId;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.loginType = loginType;
        this.nickname = nickname;
        this.introduction = introduction;
        this.travelType = travelType;
        this.role = role;
    }

    public void validateNotAlreadyAppliedTo(Accompany accompany) {
        boolean alreadyApplied = accompanyApplies.stream()
                .anyMatch(apply -> apply.getAccompany().equals(accompany));
        if (alreadyApplied) {
            throw BaseException.from(ErrorCode.ACCOMPANY_ALREADY_APPLIED);
        }
    }

    // 신규 회원 최초 가입 처리
    public static Member signUp(String email, String oauthId) {
        return Member.builder()
                .email(email)
                .oauthId(oauthId)
                .loginType(LoginType.KAKAO)
                .role(Role.USER)        // 최초 가입 시 USER
                .build();
    }

    // 신규 회원 추가 정보 등록; 닉네임/생년월일/성별
    public void updateExtraInfo(String nickname, LocalDate birth, Gender gender, String introduction) {
        this.nickname = nickname;
        this.birth    = birth;
        this.gender   = gender;
        this.introduction = introduction;
    }

    public void markAdditionalDataChecked() {
        this.additionalDataChecked = true;
    }

    public void uploadImage(Image image){
        this.image = image;
    }
}