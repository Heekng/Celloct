package com.heekng.celloct.annotation

import com.heekng.celloct.annotation.enum.UserType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RoleCheck(
    val userType: UserType = UserType.STAFF,
    val isRest: Boolean = false,
)
