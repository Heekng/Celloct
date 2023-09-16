package com.heekng.celloct.entity

import javax.persistence.*

@Entity
class Shop(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "shop_id")
    val id: Long? = null,

    @Column(name = "phone")
    var phone: String? = null,

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @Column(name = "info")
    var info: String? = null,

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.REMOVE])
    val staffList: MutableList<Staff> = mutableListOf(),

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.REMOVE])
    val joinRequests: MutableList<JoinRequest> = mutableListOf(),

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL])
    val managers: MutableList<Manager> = mutableListOf(),
) : BaseTimeEntity() {

    companion object {
        fun fixture(
            phone: String? = null,
            name: String,
            info: String? = null,
        ): Shop {
            return Shop(
                phone = phone,
                name = name,
                info = info
            )
        }
    }

    fun update(phone: String, info: String) {
        this.phone = phone
        this.info = info;
    }

    fun addManager(manager: Manager) {
        this.managers.add(manager)
    }
}
