package com.example.first_spring_boot_project.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


/*
This data class ie basically different from the simple category data class and the product data class

This review.kt is a DTO file that's why we are using these annotations that we are not using in the category.kt file or the product.kt file
this is the difference basically

*/

@Document("reviews")
data class Review(
    @Id
    val id: ObjectId =ObjectId.get(),

    val productId: ObjectId,

    @field:NotBlank(message="Reviewer name can not be blank")
    val reviewName: String,

    @field:Min(value=1, message="Rating must be at least 1")
    @field:Max(value=5,message="Rating must be at most 5")
    val rating: Int,

    val comment: String?

)