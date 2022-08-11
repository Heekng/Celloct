package com.heekng.celloct.entity

import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        name = "manager_member_id_shop_id_unique",
        columnNames = ["member_id", "shop_id"]
    )]
)
class Manager(
    @Id @GeneratedValue @Column(name = "manager_id")
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Shop,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,
) : BaseTimeEntity() {

    companion object {
        fun fixture(
            shop: Shop,
            member: Member,
            name: String,
        ): Manager {
            val manager = Manager(
                shop = shop,
                member = member,
                name = name,
            )
            shop.managers.add(manager)
            member.managers.add(manager)
            return manager
        }
    }

    fun updateInfo(name: String) {
        this.name = name
    }

    fun addShop(shop: Shop) {
        this.shop = shop
        shop.managers.add(this)
    }

    fun addMember(member: Member) {
        this.member = member
        member.managers.add(this)
    }
}