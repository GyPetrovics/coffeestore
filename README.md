# coffeestore
Coffee Store Backend API

Spring Boot version: v3.3.1

# Coffee Store Backend API endpoint list and example Requestbodies:
*****************************************************************

/POST localhost:8080/order/createorder
--------------------------------------
{
"userId": "user10",
"orderItems": [
{
"drinkDTO": {
"drinkId": 5
},
"toppingDTOList": [
{
"toppingId": 1
},
{
"toppingId": 4
}
]
},
{
"drinkDTO": {
"drinkId": 6
},
"toppingDTOList": [
{
"toppingId": 3
}
]
}
]
}


/POST localhost:8080/admin/createdrink
--------------------------------------
{
"name": "Black Coffee",
"price": 4
}


/GET localhost:8080/admin/getAllDrinks
--------------------------------------


/DELETE localhost:8080/admin/deletedrink/{drinkId}
--------------------------------------------------
/DELETE localhost:8080/admin/deletedrink/{2}


/PUT localhost:8080/admin/updateDrink/{drinId}
----------------------------------------------
/PUT localhost:8080/admin/updateDrink/3
{
"name": "LatteNew",
"price": 20
}


/POST localhost:8080/admin/createtopping
----------------------------------------
{
"name": "Hazelnut syrup",
"price": 3
}


/GET localhost:8080/admin/getAllToppings
----------------------------------------


/DELETE localhost:8080/admin/deletetopping/{toppingId}
------------------------------------------------------
/DELETE localhost:8080/admin/deletetopping/2


/PUT localhost:8080/admin/updateTopping/{toppingId}
-----------------------------------------
/PUT localhost:8080/admin/updateTopping/5
{
"name": "Hazelnut syrupNEW",
"price": 20
}

****************************************************************
