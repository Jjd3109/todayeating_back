package org.example.todayeating_back.dto.response;

import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.entity.MemberInfo;

public record FindEmail(
        String email
) {
    public static FindEmail response(MemberInfo memberInfo) {
        return new FindEmail(
                memberInfo.getEmail()
        );
    }
}
