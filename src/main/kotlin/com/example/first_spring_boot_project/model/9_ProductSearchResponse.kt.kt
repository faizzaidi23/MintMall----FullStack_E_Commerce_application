package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId

data class ProductSearchResponse(
    /*
    Below is the first item in the container.
    it will hold the list of the product object that matches the user's search and the filter criteria.
    */
    val products:List<Product>,
    val categoryFacets:Map<ObjectId, Long> // Map of CategoryId to the count of Products
)

/*
This file is a custom container or the package
Its job is to hold all the different pieces of information the API needs to send back to the mobile app after a complex search

A simple search might just return a list of products. But a smart search like the one we are  building , returns the products and helpful
filtering data.
This file is the blueprint for that combined response
*/

/*
# Why there is no annotations used here like the @NotBlank , @Size etc



Validation annotations are for checking incoming data.
They act as a security guard at the entrance to the API
ensuring that the data sent from the cliend is clean and correct before you use it

The ProductSearchResponse is for the outgoing data.
it is the package of information that the server is sending back to the client
since the server created the data itself it already trusts it.
There is nothing to validate

*/

/*
# WHy no spring component annotations like @Service @Component etc

Component annotations tell spring boot to manage the class.
They are for the active components that performs the tasks

@Service--> For the business logic
@Repository--> For the database access
@Controller-->For handling the web requests


But here ProductSearchResponse is just a plain old kotlin data class i.e a POKO Plain old kotlin object.
It does not do anything.
Its only job is to hold data temporarily before it gets converted into a JSON response


In short this file is a simple blueprint for a data structure not an active managed part of the spring framework


*/


/*
// why do we use these spring boot annotations

we use these annotations to tell spring boot what role a specific class plays in the application.
1-->@Component-->A generic all purpose component
we have to use it when we have a spring managed class that does not fit any of the more specific categories below .
this is like a general utilities or the helper class


2--> @Repository-->
       For any class that directly accesses the database. This is the Data Access layer.


       It enables spring's exception translation .
       This means it automatically converts low level database errors into standard spring exceptions makinng the error handling code cleaner.


3--->@Service-->
    This is for the classes that contain the core business logic . This is the business logic layer.

    We have to use it on any class that implements the core rules and processes of the application

4--->RestController-->
This is for the classes that handle incoming web requests and send back data usually JSON. This is the web or API player.

we will use this for all the controllers in this project like the ProductController.

5---->@Configuration--->Have to use it for the classes that define  the applications setup and configurations.
This is like an archetect that wires everything together before the application starts.

We have to use it on any class where we need to define the beans programmatically like the WebConfig class we created to make the uploads folder public
*/