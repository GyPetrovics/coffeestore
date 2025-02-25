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

/POST localhost:8080/cart/addCartItem
-------------------------------------

{
"userId": "user1",
"cartOrderItem": [
{
"drinkDTO": {
"drinkId": 3
},
"toppingDTOList": [
{
"toppingId": 3
},
{
"toppingId": 2
}
]
}
]
}

/POST localhost:8080/order/finalizeOrder
----------------------------------------

{
"userId": "user1",
"orderFinalized": true
}


/POST localhost:8080/admin/createdrink
--------------------------------------
{
"userId": "user1"
"name": "Black Coffee",
"price": 4
}


/GET localhost:8080/admin/getAllDrinks/{userId}
--------------------------------------
/GET localhost:8080/admin/getAllDrinks/user1


/DELETE localhost:8080/admin/deletedrink/{drinkId}/{userId}
--------------------------------------------------
/DELETE localhost:8080/admin/deletedrink/2/user1


/PUT localhost:8080/admin/updateDrink/{drinId}
----------------------------------------------
/PUT localhost:8080/admin/updateDrink/3
{
"userId": "user1"
"name": "LatteNew",
"price": 20
}


/POST localhost:8080/admin/createtopping
----------------------------------------
{
"userId": "user1"
"name": "Hazelnut syrup",
"price": 3
}


/GET localhost:8080/admin/getAllToppings/{userId}
-------------------------------------------------
/GET localhost:8080/admin/getAllToppings/user1


/DELETE localhost:8080/admin/deletetopping/{toppingId}/{userId}
------------------------------------------------------
/DELETE localhost:8080/admin/deletetopping/2/user1


/PUT localhost:8080/admin/updateTopping/{toppingId}
-----------------------------------------
/PUT localhost:8080/admin/updateTopping/5
{
"userId": "user1"
"name": "Hazelnut syrupNEW",
"price": 20
}


/GET localhost:8080/admin/mostUsedToppings/{userId}
---------------------------------------------------
/GET localhost:8080/admin/mostUsedToppings/user1

****************************************************************
