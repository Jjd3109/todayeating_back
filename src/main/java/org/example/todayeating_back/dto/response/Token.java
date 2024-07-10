package org.example.todayeating_back.dto.response;

public record Token(
        String accessToken,
        String refreshToken
) {

    public static Token token(String accessToken, String refreshToken){
        return new Token(
                accessToken,
                refreshToken
        );
    }

}
