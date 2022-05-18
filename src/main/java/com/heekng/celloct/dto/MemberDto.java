package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class WithDraw {

        private String email;

        @Builder
        public WithDraw(String email) {
            this.email = email;
        }
    }

}
