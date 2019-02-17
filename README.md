# csv-parser

This project is a parser for two csv files which you can find in /resources. All of the objects are put in postgresql database. 
Database url, username and password can be changed in application.properties. 

Spring Boot API has two endpoints:
 - /attributes -> gives all the attributes with their options from db
 - /attributes/{code}?lang="" -> gives attribute found by its code and all its options in chosen language.
 
 Languages available: CZ, NL, DK, PL, IT, NO, AT, ES, BE, FR, DE, FI, SE, US, GB, CH, IE
 
