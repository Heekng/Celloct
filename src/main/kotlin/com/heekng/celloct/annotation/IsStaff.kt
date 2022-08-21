package com.heekng.celloct.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsStaff(
    val isRest: Boolean = false,
)
