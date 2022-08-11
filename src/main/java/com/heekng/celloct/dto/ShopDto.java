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
    public static class CreateRequest {
        private String name;
        private String phone;
        private String info;
        private String managerName;

        @Builder
        public CreateRequest(String name, String phone, String info, String managerName) {
            this.name = name;
            this.phone = phone;
            this.info = info;
            this.managerName = managerName;
        }

        public Shop toEntity() {
            return Shop.Companion.fixture(phone, name, info);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRequest {
        private Long id;
        private String phone;
        private String info;

        @Builder
        public UpdateRequest(Long id, String phone, String info) {
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ShopDetailResponse {
        private Long id;
        private String phone;
        private String name;
        private String info;

        @Builder
        public ShopDetailResponse(Shop shop) {
            this.id = shop.getId();
            this.phone = shop.getPhone();
            this.name = shop.getName();
            this.info = shop.getInfo();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HomeShopManagerResponse {
        private Long id;
        private String name;

        @Builder
        public HomeShopManagerResponse(Shop shop) {
            this.id = shop.getId();
            this.name = shop.getName();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HomeShopStaffResponse {
        private Long id;
        private String name;

        @Builder
        public HomeShopStaffResponse(Shop shop) {
            this.id = shop.getId();
            this.name = shop.getName();
        }

    }

}
