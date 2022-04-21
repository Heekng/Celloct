package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ShopDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class createRequest {
        private String name;
        private String phone;
        private String info;
        private String managerName;

        @Builder
        public createRequest(String name, String phone, String info, String managerName) {
            this.name = name;
            this.phone = phone;
            this.info = info;
            this.managerName = managerName;
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
    @Setter
    @NoArgsConstructor
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ListResponse {
        private Long id;
        private String name;
        private String info;
        private Integer staffCount;

        @Builder
        public ListResponse(Shop shop) {
            this.id = shop.getId();
            this.name = shop.getName();
            this.info = shop.getInfo();
            this.staffCount = shop.getStaffList().size();
        }
    }


}
