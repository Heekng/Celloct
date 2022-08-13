package com.heekng.celloct.config.oauth.dto

import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Role

class OAuthAttributes(
    val attributes: Map<String, Any>,
    val nameAttributeKey: String,
    val name: String,
    val email: String,
    val picture: String,
) {

    companion object {
        fun of(
            registrationId: String,
            userNameAttributeName: String,
            attributes: Map<String, Any>,
        ): OAuthAttributes {
            return ofGoogle(userNameAttributeName, attributes)
        }

        fun ofGoogle(
            userNameAttributeName: String,
            attributes: Map<String, Any>,
        ): OAuthAttributes {
            return OAuthAttributes(
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                picture = attributes["picture"] as String,
                attributes = attributes,
                nameAttributeKey = userNameAttributeName,
            )
        }
    }

    fun toEntity(): Member {
        return Member(
            name = name,
            email = email,
            picture = picture,
            role = Role.USER,
        )
    }
}