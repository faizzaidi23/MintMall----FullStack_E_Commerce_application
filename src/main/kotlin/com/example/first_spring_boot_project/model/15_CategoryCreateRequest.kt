package com.example.first_spring_boot_project.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.bson.types.ObjectId

/*
This is the DTO that means we have to separate the data we receive from the API request object from the database model i.e category object
*/


data class CategoryCreateRequest(
    @field:NotBlank(message="Category name cannot be blank")
    @field:Size(min=2, max=50, message="Category name must be between 2 and 50 characters")
    val name: String,

    //This is optional. If an user provides it, it means they are creating a sub-category

    val parentId: ObjectId?=null
)