# coffeestore
Coffee Store Backend API

Spring Boot version: v3.3.1
***

API is configured for H2 in memory database (for demonstration purposes)

# H2 Console settings:

Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:mem:coffestoredb

******************************************************************

Server port is 8080

# Coffee Store Backend API endpoint list and example Requestbodies
******************************************************************

/POST localhost:8080/order/createorder
--------------------------------------
{
"userId": "user1",
"orderItems": [
{
"drinkDTO": {
"drinkId": 3
},
"toppingDTOList": [
{
"toppingId": 1
},
{
"toppingId": 3
}
]
},
{
"drinkDTO": {
"drinkId": 1
},
"toppingDTOList": [
{
"toppingId": 1
},
{
"toppingId": 2
}
]
},
{
"drinkDTO": {
"drinkId": 4
},
"toppingDTOList": [
{
"toppingId": 4
}
]
}
]
}
***

/POST localhost:8080/admin/createdrink
--------------------------------------
{
"name": "Black Coffee",
"price": 4
}
***

/GET localhost:8080/admin/getAllDrinks
--------------------------------------
***

/DELETE localhost:8080/admin/deletedrink/{drinkId}
--------------------------------------------------
/DELETE localhost:8080/admin/deletedrink/2
***

/PUT localhost:8080/admin/updateDrink/{drinId}
----------------------------------------------
/PUT localhost:8080/admin/updateDrink/3

{
"name": "LatteNew",
"price": 20
}
***

/POST localhost:8080/admin/createtopping
----------------------------------------
{
"name": "Hazelnut syrup",
"price": 3
}
***

/GET localhost:8080/admin/getAllToppings
----------------------------------------
***

/DELETE localhost:8080/admin/deletetopping/{toppingId}
------------------------------------------------------
/DELETE localhost:8080/admin/deletetopping/2
***

/PUT localhost:8080/admin/updateTopping/{toppingId}
-----------------------------------------
/PUT localhost:8080/admin/updateTopping/5
{
"name": "Hazelnut syrupNEW",
"price": 20
}
***

/GET localhost:8080/admin/mostUsedToppings
------------------------------------------

****************************************************************
