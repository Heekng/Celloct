package com.heekng.celloct.service

import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.entity.Manager
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.util.fail
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ShopService(
    private val shopRepository: ShopRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun makeShop(createRequest: ShopDto.CreateRequest, memberId: Long): Long {
        val shop = createRequest.toEntity()
        val member = memberRepository.findByIdOrThrow(memberId)
        val managerName = if (createRequest.managerName.equals("")) member.name else createRequest.managerName
        validateDuplicateShop(shop)
        val manager = Manager(
            shop = shop,
            member = member,
            name = managerName!!,
        )
        shop.addManager(manager)

        shopRepository.save(shop)
        return shop.id!!
    }

    fun existName(shopName: String): Boolean {
        val shop = shopRepository.findByName(shopName)
        return shop != null
    }

    @Transactional
    fun updateShop(updateRequest: ShopDto.UpdateRequest) {
        val shop = shopRepository.findByIdOrThrow(updateRequest.id)
        shop.update(updateRequest.phone, updateRequest.info)
    }

    @Transactional
    fun delete(shopId: Long) {
        val shop = shopRepository.findByIdOrThrow(shopId)
        shopRepository.delete(shop)
    }

    fun findShop(shopId: Long): Shop {
        return shopRepository.findByIdOrThrow(shopId)
    }

    fun findListResponseListByNameContaining(name: String): List<ShopDto.ListResponse> {
        return shopRepository.findListByNameContaining(name)
            .map { shop -> ShopDto.ListResponse(shop) }
    }

    private fun validateDuplicateShop(shop: Shop) {
        val findShop = shopRepository.findByName(shop.name)
        if (findShop != null) fail()
    }
}