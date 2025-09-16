package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Objects

/*
This category data class groups similar products together
like we can create categories like electronics men's fashion books and home and kitchen type of things
a product like iPhone or  something would then be place in the electronics category
*/

@Document("categories") // Store these in the categories collection
data class Category(
    @Id
    val id: ObjectId=ObjectId.get(),
    val name: String,
    val parentId: ObjectId?=null // used for sub-categories, can be empty
)