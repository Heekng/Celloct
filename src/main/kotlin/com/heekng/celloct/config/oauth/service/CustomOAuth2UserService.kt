package com.heekng.celloct.config.oauth.service

import com.heekng.celloct.config.oauth.dto.OAuthAttributes
import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.entity.Member
import com.heekng.celloct.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class CustomOAuth2UserService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest!!.clientRegistration.registrationId
        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        val attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.attributes)

        val member = saveOrUpdate(attributes)

        httpSession.setAttribute("member", SessionMember.fixture(member))

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(member.role!!.key)), attributes.attributes, attributes.nameAttributeKey
        )
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): Member {
        val member = memberRepository.findByEmail(attributes.email)?.updatePicture(attributes.picture) ?: attributes.toEntity()
        return memberRepository.save(member)
    }

}