package com.heekng.celloct.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsManager(
    val isRest: Boolean = false,
)
