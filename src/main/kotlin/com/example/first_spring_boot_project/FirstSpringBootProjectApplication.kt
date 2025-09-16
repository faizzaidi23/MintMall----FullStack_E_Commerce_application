package com.example.first_spring_boot_project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching   // this annotations was added on day 5 after the formation of the 10 files
class FirstSpringBootProjectApplication

fun main(args: Array<String>) {
	runApplication<FirstSpringBootProjectApplication>(*args)
}
