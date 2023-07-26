package com.momentum.releaser.domain.user.dto;

import javax.validation.constraints.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AuthRequestDto {
    /**
     * 2.1 회원가입
     *
     * 회원가입 요청 DTO
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInfoReqestDTO {

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 1, max = 20)
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;

        @Builder
        public UserInfoReqestDTO(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    /**
     * 2.2 이메일 로그인
     *
     * 로그인 요청 DTO
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLoginReqestDTO {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;

        @Builder
        public UserLoginReqestDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

}