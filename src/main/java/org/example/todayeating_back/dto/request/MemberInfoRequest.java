package org.example.todayeating_back.dto.request;

public record MemberInfoRequest(
        String email,
        String password,
        String nickName) {
}
