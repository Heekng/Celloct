package com.heekng.celloct.entity

import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        name = "join_request_member_id_shop_id_unique",
        columnNames = ["member_id", "shop_id"]
    )]
)
class JoinRequest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "join_request_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    val shop: Shop,
) : BaseTimeEntity() {

    companion object {
        fun fixture(
            member: Member,
            shop: Shop,
        ): JoinRequest {
            return JoinRequest(
                member = member,
                shop = shop
            )
        }
    }

}
