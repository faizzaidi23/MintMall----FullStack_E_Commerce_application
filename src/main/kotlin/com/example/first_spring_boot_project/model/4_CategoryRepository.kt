package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: MongoRepository<Category, ObjectId>{

}