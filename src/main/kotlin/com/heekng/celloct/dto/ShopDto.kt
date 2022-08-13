package com.heekng.celloct.dto

import com.heekng.celloct.entity.Shop
import com.heekng.celloct.entity.Shop.Companion.fixture

class ShopDto {
    data class CreateRequest (
        val name: String? = null,
        val phone: String? = null,
        val info: String? = null,
        val managerName: String? = null,
    ) {
        fun toEntity(): Shop {
            return fixture(
                phone = phone,
                name = name ?: "name",
                info = info
            )
        }
    }

    data class UpdateRequest (var id: Long?, val phone: String, val info: String)

    data class ListResponse (val shop: Shop) {
        val id: Long?
        val name: String
        val info: String?
        val staffCount: Int

        init {
            id = shop.id
            name = shop.name
            info = shop.info
            staffCount = shop.staffList.size
        }
    }

    data class ShopDetailResponse (val shop: Shop) {
        val id: Long?
        val phone: String?
        val name: String
        val info: String?

        init {
            id = shop.id
            phone = shop.phone
            name = shop.name
            info = shop.info
        }
    }

    data class HomeShopManagerResponse (val shop: Shop) {
        val id: Long?
        val name: String

        init {
            id = shop.id
            name = shop.name
        }
    }

    data class HomeShopStaffResponse (val shop: Shop) {
        val id: Long?
        val name: String

        init {
            id = shop.id
            name = shop.name
        }
    }
}