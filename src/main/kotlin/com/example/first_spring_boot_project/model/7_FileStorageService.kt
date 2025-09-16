package com.example.first_spring_boot_project.model

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

/*
Now we have to create a new service that is responsible for saving files.
this is a simple storage implementation

@Service

This file is specialized component whose only job is to handle the logic of saving the files

This service acts aas a specialist.The controller receives the file and hands it over to this service
saying "Please store this safely" making the code much cleaner and easier to maintain

*/


@Service  // This annotation tells the spring  boot to create a single instance i.e a bean of this class
// It marks this class as a provider of a specific business service
//
class FileStorageService{

    /*
    We create a private read only i.e val property to hold the path
    paths.get("uploads") creates a path object that represents the uploads folder
    in the root of the project . this is the modern way to handle file paths
    */

    private val  uploadedLocation=Paths.get("uploads")

    /*
    the inti block is special kotlin code that runs oce when tthe FileStorageService is first created
    it's a perfect place for the setup logic

    if(!Files.exists(uploadedLocation)-->
    This checks if the uploads folder already exists
    and if it does not exist this line creates it for me automatically

    */

    init{
        //Create the directory if it doesn't exist when the service is initialized
        if(!Files.exists(uploadedLocation)){
            Files.createDirectory(uploadedLocation)
        }
    }

    fun save(file: MultipartFile): String{
        // This code is basically for the file upload like when I will be uploading any file on the server
        // This file will be stored on the cloud i.e the mongodb database so when I want to retrieve the file from there I have to first convert that into string format

        // Logic to save the file to a local folder like "uploads"
        // and return the part or url to the file
        //for example: "/uploads/product-image.jpg"

        // Now the code is for the checking of the file like the file should be only an image nothing else
        // Define the list of allowed file types
        val allowedTypes=listOf("image/jpeg","image/png","image/webp")

        // Adding the validation block
        if(file.contentType !in allowedTypes){
            throw RuntimeException("Invalid file type.Please upload a JPEG, PNG or WEBP image")
        }
        val uniqueFileName=UUID.randomUUID().toString()+"_"+file.originalFilename

        try{
            //Resolve the final path where the file will be saved
            val destinationPath=this.uploadedLocation.resolve(uniqueFileName)

            //Copy the file's input stream to the destination path
            // This is the line that actually saves the file
            Files.copy(file.inputStream,destinationPath)

            //return the unique filename so it can be stored in the database
            return uniqueFileName
        }catch(e: Exception){
            throw RuntimeException("Could not store the file:Error:${e.message}")
        }
    }
}