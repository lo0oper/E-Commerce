## Assumptions
1. There are unlimited items of each type
2. User has unlimited money to but any item

## VIDEO DEMO:
1. Application Demo: [Link](https://www.loom.com/share/a3ba7993377a45c4947a50f16eb3f2a0)
2. UnitTests Demo: [Link](https://www.loom.com/share/a3ba7993377a45c4947a50f16eb3f2a0?highlightComment=21355778&t=3)

### Instructions
 - This is a basic E-commerce application with in memory-databse.
 - You can clone the repo from [here]() and run it by running mvn build in your IDE. For further instructions on how to run Springboot application refer to the Help.md file in repo.

##### Steps to Use
##### Functionalities
This application will expose these endpoints 
1. GET `\api\v1\user` to fetch all the users in DB
2. GET `\api\v1\item` to fetch all the items in DB
3. GET `\api\v1\user?userId=${userId}` to fetch a specific user details
4. GET `\api\v1\item?itemId=${itemId}` to fetch a specific Item details
5. POST `\api\v1\user` to create a user
req body: 
```json

{
    "email":"ram3@gmail.com"
}
```
6. POST `\api\v1\user\order` to place an order
req body:
```json
{
    "userId": "1",
    "orderItems": [
        {
            "itemId": "1",
            "quantity": 2
        },
        {
            "itemId": "2",
            "quantity": 2
        }
    ]
}

```
7. POST `\api\v1\user\discount` to check if user has any discounts or not.
req body:
```json
{
    "userId":"1",
    "discountNumber":"2"
}
```
8. POST `\api\v1\item` to create a user
req body
```json
{
    "cost":2000,
    "itemType":"toy3"
}
```

### How to check working
1. Create some users by hitting the POST `\api\v1\user`.
2. Create some item by hitting the POST `\api\v1\item` .
3. Now place order by hitting  POST `\api\v1\user\order` request.
4. Check wether user is eligible for discount or not using POST `\api\v1\user\discount`.
5. Place some more order and check again if user is eligible for discounts.


