package com.heekng.celloct.config.oauth.dto

import com.heekng.celloct.entity.Member

class SessionMember(
    val id: Long,
    val name: String,
    val email: String,
    val picture: String,
) : java.io.Serializable {

    companion object {
        fun fixture(member: Member): SessionMember {
            return SessionMember(
                id = member.id!!,
                name = member.name,
                email = member.email,
                picture = member.picture!!,
            )
        }
    }
}