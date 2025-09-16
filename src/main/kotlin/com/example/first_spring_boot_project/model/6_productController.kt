package com.example.first_spring_boot_project.model

import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import kotlin.math.min

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productRepository: ProductRepository,
    private val productService: ProductService
){
    @PostMapping
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ResponseEntity<Product>{

        //This code only runs if the @Valid check passes
        val product =Product(
            name=request.name,
            sku=request.sku,
            price = request.price,
            categoryId = request.categoryIds,
            description = request.description
        )

        val savedProduct=productRepository.save(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)

    }

    @PostMapping("/{id}/images")
    fun uploadProductImages(
        @PathVariable id: ObjectId,
        @RequestParam("images")files:Array<MultipartFile>
    ): ResponseEntity<Product>{
        val product=productRepository.findById(id).orElseThrow(){ Exception("Product not found ") }

        //Loop through each uploaded file

        val imageUrls=files.map{
            file->
            FileStorageService.save(file)
        }
        //Add the new image URLs to the product's list
        val updatedProduct=product.copy(images=product.images + imageUrls)
        productRepository.save(updatedProduct)
        return ResponseEntity.ok(updatedProduct)
    }



    // Below class was created on day 4 so we have to revise in that order only

    @GetMapping("/search")
    fun searchProducts(
        @RequestParam(required = false)query:String?,
        @RequestParam(required = false)categoryId:ObjectId?,
        @RequestParam(required = false)minPrice:Double?,
        @RequestParam(required = false)maxPrice:Double?
    ): ResponseEntity<ProductSearchResponse>{
        // we will delegate the complex logic to a service
        val response=productService.search(query,categoryId, minPrice,maxPrice)
        return ResponseEntity.ok(response)
        /*
        ResponseEntity.ok() is a shortcut in spring boot for creating a standard successful HTTP response with a status code of 200 OK

        The purpose of it is Communication Success-->

        When the mobile app sends a requests to the API, the API needs to send back a response. This response has two main parts

        1--> The Data("The Body"):--> The actual information requested  like the list of the products. In this code t his is the response object
        2--> The Status Code--> A standardized number that quickly communicates the outcome of the request

        The 200OK status code is the universal signal for "Everything worked perfectly"

        Why ResponseEntity is used-->

        The ResponseEntity class is a powerful tool in spring that lets you control the HTTP response including the status code, headers and the body.
        By wrapping the ProductSearchResponse in a ResponseEntity we give the client i.e the mobile app clear information about the result of its request

        This request will not be reflected on the UI directly but it is critically important for how the UI behaves

        # Here is the behind the scene flow in the mobile app

        Request Sent-->The user performs a search.
        The app shows a loading spinner and sends the request to the API

        Response received: The server sends back the ResponseEntity

        Status code checked--> The first thing the app's code does is check  the status code of the response

        ----->if(statusCode==200): This means Success

        That means the App's code will then proceed to read the json data from the response body.
        hide the loading spinner and display the list of products on the screen


        ----->if(statusCode==400):-->This means Not Found
        The app will hide the loading spinner and  show a user friendly message like "No products match your search"

        ----->else if(statusCode==500):--->Server error
        The app will hide the loading and show an error message like something went wrong. Please try again later.

        */
    }
}