package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/*
This file is an interface that acts as the database manager for the reviews
by creating this simple file spring will automatically provide
 you with all the code needed tto save find delete and update reviews
 in the mongodb collection
*/

@Repository
interface ReviewRepository: MongoRepository<Review, ObjectId>{
    fun findByProductId(productId: ObjectId):List<Review>
}