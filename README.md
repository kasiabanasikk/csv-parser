# csv-parser

This project is a parser for two csv files which you can find in /resources. All of the objects 
are put in h2 sql embedded database

Spring Boot API has two endpoints:
 - localhost:8080/attributes -> gives all the attributes with their options from db
 - localhost:8080/attributes/{code}?lang="" -> gives attribute found by its code and all its options in chosen language.
 
 Languages available: CZ, NL, DK, PL, IT, NO, AT, ES, BE, FR, DE, FI, SE, US, GB, CH, IE
 
