package com.arom.with_travel.global.security.token.properties;

public class JwtProperties {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60; // 1시간
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30; // 1달
}
