package com.heekng.celloct.entity

import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "picture")
    var picture: String? = null,

    @Enumerated(EnumType.STRING)
    val role: Role? = null,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE])
    val joinRequests: MutableList<JoinRequest> = mutableListOf(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE])
    val staffList: MutableList<Staff> = mutableListOf(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE])
    val managers: MutableList<Manager> = mutableListOf(),
) {

    companion object {
        fun fixture(
            name: String,
            email: String,
        ): Member {
            return Member(
                name = name,
                email = email,
            )
        }
    }

    fun updatePicture(picture: String): Member {
        this.picture = picture
        return this
    }
}
