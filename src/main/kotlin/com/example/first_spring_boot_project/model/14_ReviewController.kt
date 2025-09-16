package com.example.first_spring_boot_project.model

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/*
This marks the class as a REST Controller, which means it is designed to handle the API
requests and send back the data like JSON directly
 */
@RestController
/*
This sets the base url for all endpoints defined within this class
Every url will start with "/api/v1/reviews"
*/
@RequestMapping("/api/v1/reviews")
/*
This is a documentation annotation from SpringDoc. It groups all the endpoints in this
controller under the "Review API" heading in the UI
*/
@Tag(name="Review API", description="Endpoints for managing product reviews")
/*
The controller needs the ReviewRepository to talk to the database
spring boot automatically provides injects the repository instance through the constructor
*/

class ReviewController(private val reviewRepository: ReviewRepository){

    //This annotation adds a description to this specific endpoint in the swagger UI
    @Operation(summary="Create a new review for a product")

    /*
    This function defines the logic for creating a review
    @Valid--> This triggers the validation rules on the Review object
    @RequestBody-->Tells spring to take the json from the request and convert it into a 'Review' object
    */

    @PostMapping
    fun createReview(@Valid @RequestBody review: Review): ResponseEntity<Review>{
        // Here we use the injected repository to save the new review object to the database
        val savedReview=reviewRepository.save(review)
        // We send back a response with an HTTP status of 201 Created which is the
        //Standard for succcessfully creating a new resource. The saved review is included in the body
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview)
    }


    // This adds a description for the "get reviews" endpoint in the swagger UI
    @Operation(summary="Get all the reviews for a specific product")
    /*
    This marks the method to handle HTTP GET requests. The URL is more specific
    "/api/v1/reviews/product/{productId}". GET is used for fetching data
    */
    @GetMapping("/products/{productId}")
    /*
    This function defines the logic for fetching reviews
    @PathVariable:Tells Spring to take the value from the URL's {productId} placeholder
    and use it as the 'productId' variable in the function
    */
    fun getReviewsForProduct(@PathVariable productId: ObjectId): ResponseEntity<List<Review>>{
        // We use the custom method we defined in our repository to find all reviews
        //that much the given product ID
        val reviews=reviewRepository.findByProductId(productId)
        // we send back a response with an HTTP status of 200 OK, which is the standard
        // for a successful request. The list of reviews is included in the body

        return ResponseEntity.ok(reviews)
    }

}
