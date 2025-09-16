package com.example.first_spring_boot_project.model

import org.bson.types.ObjectId
import java.math.BigDecimal
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class ProductCreateRequest(
    @field:NotBlank(message="Product name cannot be blank")
    @field:Size(min=3,max=150)
    val name: String,

    @field:NotBlank(message="This field can not be blank also")
    val sku: String,

    //This is a standard validation annotation
    @field:DecimalMin(value="0.0",inclusive=false,message="Price must be positive")
    val price: BigDecimal,

    @field:NotEmpty(message="Product must belong to at least one category")
    val categoryIds:List<ObjectId>,

    val description: String
)

/*
@field--->

In kotlin when we declare a property in a data class constructor like val name:String,
the compiler creates several thing for you behind the scenes

A parameter in the constructor
An internal field to actually store the data i.e like the value "i phone or something"
A getter method to access the data like getName()

This creates a small confusion when you write @NotBlank where should kotlin apply this rule
To the constructor parameter? To the getter? Or to the actual field where the value is stored

The @field: prefix is a specific instruction that resolves this confusion it says

Hey kotlin I want you to apply this @Notblank annotation directly to the internal backing field
that will hold the data for name
For validation annotations this is almost always what you want.
you want the rule to be attacked to the piece of data itself

*/


/*
Here NotBlank, Size, DecimalMin and NotEmpty are the  annotations
They are not keywords in the kotlin language and while you use them heavily in spring boot
they are not exclusive to it

These are just like the sticky notes. that we attach to the code
These notes do not do anything on their own
but they provide  instructions or metadata for other tools that read your code
i
They come from a standard specification called Jakarta Bean Validation
Spring Has a validation engine that is build to read these specific sticky notes and enforce the rules they describe


@NotBlank-->
Rule is--> This string must not be empty and must contain at least one non whitespace character
On the sku field it ensures the sku can not be null  "" i.e an empty string or " " a string with only spaces

@Size(min=3,max=150);
Rule:The size of this item must be between the min and the max value
On the name field it ensures the product's name is between 3 and 150 characters long

@DecimalMin(value="0.0", inclusive=false)
Rule-->This  number must be greater than the specified value
On the price field inclusive=false means the price must be strictly greater than 0.0 It can not be zero or a negative number

@NotEmpty-->
Rule-->
This list or some other collection must not be empty
On the categoryIds field: It ensures that when you create a product you must assign it to at least one category. An empty list will be rejected



The difference between NotEmpty and NotBlank is-->

NotEmpty checks if a value is not null and its size or length is greater than zero.
It can be used on collections like list map set as well as on string

however it fails for null and "" i.e an empty string. However it passes for a string containing only
spaces like " " because its length is greater than 0


## @NotBlank ✍
This annotation is more specific and can only be used on Strings. It checks if a string is not null and contains at least one non-whitespace character.

It's designed specifically for validating user text input where you want to ensure there's actual content.
*/