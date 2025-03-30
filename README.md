Order microapp kopplad till 2 andra microappar: produkter och användare. 
URL: http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/
Kommandon att köra i postman:

För att få fram produktdetaljer:
Get:
http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/orders/products/{orderId}

För att få fram en specifik order med detaljer om vem som beställt den:
Get:
http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/orders/users/{orderId}


För att få fram alla ordrar:
Get:
http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/orders

För att lägga en order:
Post:
http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/orders
Body:
{
    
    "userId": 2,
    "product": "Päron"
    
}

För att ta bort en order:
Delete:
http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/orders/{orderId}

För att ändra en order:
Put:
http://ordermicro-env.eba-ha662mef.eu-north-1.elasticbeanstalk.com/orders
Body:
{
    "id":2,
    "userId": 2,
    "product": "Päron"
}
