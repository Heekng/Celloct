package com.heekng.celloct

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CelloctApplication

fun main(args: Array<String>) {
    runApplication<CelloctApplication>(*args)
}