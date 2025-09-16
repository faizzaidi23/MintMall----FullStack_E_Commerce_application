package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.core.query.TextQuery
import org.springframework.stereotype.Service

/*
This annotation marks the class as the business logic component
spring boot will create and manage it
*/

@Service
/*
The class takes MongoTemplate in its constructor.
Spring will automatically inject it. We need MongoTemplate for building powerful, custom queries.
*/
class ProductService(
    private val mongoTemplate: MongoTemplate,
    private val productRepository: ProductRepository // This was added on day 5 after the creation of 10 files
){

    // This method was added on day 5 after the creation of 10 files but other than this method alll the methods were added before

    @Cacheable(cacheNames =["products"],key="id") // we have to pre-configure the caches the application will be using. This gives much better control over their behavior like setting expiration times and makes the code clearer
    fun findProductById(id: ObjectId):Product?{
        println("-------Fetching from database----")
        return productRepository.findById(id).orElse(null)
    }

    // This was also added before after the above method
    @CacheEvict(cacheNames = ["products"], key = "#product.id")
    fun updateProduct(product: Product): Product {
        println("--- EVICTING CACHE for id ${product.id} ---")
        return productRepository.save(product) // Save the updated product to the database
    }

    fun search(query: String?, categoryId: ObjectId?, minPrice: Double?, maxPrice: Double?): ProductSearchResponse {
        // 1. Create a list for all filter criteria OTHER THAN the text search.
        val filterCriteriaList = mutableListOf<Criteria>()

        categoryId?.let {
            filterCriteriaList.add(Criteria.where("categoryIds").`is`(it))
        }
        if (minPrice != null || maxPrice != null) {
            val priceCriteria = Criteria.where("price")
            minPrice?.let { priceCriteria.gte(it) }
            maxPrice?.let { priceCriteria.lte(it) }
            filterCriteriaList.add(priceCriteria)
        }

        // 2. Conditionally create the base query object.
        val finalQuery: Query = query?.let {
            // If a text search 'query' exists, create a TextQuery.
            TextQuery.query(TextCriteria.forDefaultLanguage().matching(it))
        } ?: run {
            // Otherwise, create a standard Query.
            Query()
        }

        // 3. Add the other filter criteria to the query we just created.
        if (filterCriteriaList.isNotEmpty()) {
            finalQuery.addCriteria(Criteria().andOperator(*filterCriteriaList.toTypedArray()))
        }

        // 4. Execute the query.
        val products = mongoTemplate.find(finalQuery, Product::class.java)

        // ... rest of your method ...
        val facets = emptyMap<ObjectId, Long>()
        return ProductSearchResponse(products, facets)
    }
}

/*
Todo--> The purpose of this file and the flow too like how this file is suited in the app's flow

* This file is the business logic brain of the search feature.
its purpose is to handle the complex task of building and executing a dynamic database query based on user input

the productController is the front desk it handles incoming requets. the productRepository is a basic tool for simple data access.

The ProductService sits  between them it is the specialist that takes a complex request from the controller adn uses advanced tools
MongoTemplate to get the job done


Controller---> Manages web requests and responses

Service--> Manges the business logic like how to build a search

Repository--> manges direct, simple database communication
*/


/*
TODO--> About the function we are using here and why those parameters are used

This function is a flexible and powerful search engine for the products.
its purpose is to find products that match a combination of optional filters

TOdo-->11-->query:String

Purpose---> This parameter is for the user's text search
It represents the word or phrase a user types into the main search bar of the app.
The service will use this value to perform a full text search against the product's name and description to find the relevant matches

egg--> If  an user searches for "wireless mouse" the query parameter will be the string "wireless mouse"

todo--> 2-->categoryID:ObjectId?
This parameter is used to filter products by a specific category
It represents the user's action of selecting a category from a filter menu.
The mobile app will send the unique ID i.e the ObjectId of the selected category.
The service will use this ID to  find only the products that belong that specific category

and because of ? the user is can provide this or leave that blank too like the user has a right that it will not select it

todo->3-->minPrice:Double?

This sets the minimum price for the price range filter
It represents the lower bound of a price slider or a "Price From" text field in the app's UI.
This service will use this value to find products that are more expensive that or equal to this price


todo-->4-->maxPrice:Double?
This sets the maximum price for a price range filter
It represents the upper bound of a price slider or a price to text field




todo-->val criteriaList=mutableListOf<Criteria>()
This line creates an empty flexible list where we will collect all the individual filter rules before combining them into a final database query



We use mutableListOf() here because we are building our query dynamically.
 We don't know ahead of time which filters the user will provide.
 Sometimes they might search only by price, other times by price and category.
 We need a flexible list that we can add filter rules to inside our if statements.


 <Criteria> - The Items That Go Inside
 The <Criteria> part specifies that this list can only hold objects of type Criteria

 A Criteria object is a class from Spring Data MongoDb that represents a single condition or rule for the query

 Think of the final query as a complete instruction sentence. Each Criteria object is a single phrase in that sentence:

Phrase 1: "where the price is greater than 10000" (Criteria.where("price").gte(10000))

Phrase 2: "where the category is 'Electronics'" (Criteria.where("categoryIds").is(...))

So, the line val criteriaList = mutableListOf<Criteria>() translates to:

"Create a new, empty, and changeable shopping basket named criteriaList that is designed to hold only Criteria rule objects."
*/