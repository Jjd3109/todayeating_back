package org.example.todayeating_back.dto.response;

import org.example.todayeating_back.entity.MemberInfo;

public record FindEmailAll(
        String email,
        String nickName
) {
    public static FindEmailAll response(MemberInfo memberInfo) {
        return new FindEmailAll(
                memberInfo.getEmail(),
                memberInfo.getNickName()
        );
    }
}
