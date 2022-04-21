package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Member;
import lombok.Builder;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class withDraw {

        private String email;

        @Builder
        public withDraw(String email) {
            this.email = email;
        }
    }

}
