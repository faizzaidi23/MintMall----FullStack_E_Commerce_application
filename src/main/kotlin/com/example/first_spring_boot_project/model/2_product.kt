package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document("products")
data class Product(
    @Id // This is the unique identifier
    val id: ObjectId=ObjectId.get(),
    val name: String,
    @Indexed(unique=true) // Create a fast , unique index for SKU
    val sku: String, // That is a stock keeping unit
    val price: BigDecimal,
    val categoryId:List<ObjectId>, // A list of Category IDs this product belongs to
    val description: String,

    // Now this image thing is added  on the day 3 of the first week
    val images:List<String> = listOf()
)

/*
The @Indexed annotation tells MongoDB to create an index page for the sku field.
it maintains a separate list of all the skus that points directly to the full product document . so
when you ask find me the product with sku abc 123 the database does not scan  all one million products. It uses its special index to locate it instantly

For a field like sku which we will be using frequently to look up specific products an index is essential for performance


By adding unique=true we are making this a non negotiable rule.
If  we write code that accidently tries to save a new product with an sku that already exits. the database itself will reject the operation and throw an error
This is a powerful way to protect the integrity of the data and prevent mistakes



*/
