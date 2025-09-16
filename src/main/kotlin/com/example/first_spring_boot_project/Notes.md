# Setting up the spring boot project

Make a new project in mongodb atlas
create a cluster in it
copy the connection string from there
then in the spring boot project in the application.properties file write
spring.data.mongodb.uri=mongodb+srv://faizzaidi3105:12345%40Faizzaidi@springbootfirstprojectf.shi0r0r.mongodb.net/SpringBootFirstProject?retryWrites=true&w=majority&appName=SpringBootFirstProjectFirstCluster
remember here in the above string we have used %4o to replace @ and similarly we have to use for other special characters as well 
and after .net/ we have to add the database name initially we won't have any database so we can give any database name that does not matter

