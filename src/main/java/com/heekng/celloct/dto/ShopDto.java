package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShopDto {

    @Getter
    @NoArgsConstructor
    public static class createRequest {
        private String name;
        private String phone;
        private String info;

        @Builder
        public createRequest(String name, String phone, String info) {
            this.name = name;
            this.phone = phone;
            this.info = info;
        }

        public Shop toEntity() {
            return Shop.builder()
                    .name(name)
                    .phone(phone)
                    .info(info)
                    .build();
        }
    }

    @Getter
    public static class updateRequest {
        private Long id;
        private String phone;
        private String info;

        @Builder
        public updateRequest(Long id, String phone, String info) {
            this.id = id;
            this.phone = phone;
            this.info = info;
        }
    }


}
