# API - SHOP-ALL  :moneybag: :convenience_store:
_____

El proyecto shop-all es un challenged traido por Metaphorce en el cual 
se realizo una api para un carrito de compras en el que puedes agregar o 
remover productos.

____

## Funcionalidades :clipboard:

### 1. Controlador de usuarios 

#### :woman: :man: Obtener todos los usuarios utilizando la ruta "/api/v1/user/getAll"
````
RESPUESTA:
[
    {
        "id": 1,
        "name": "Juan Martín",
        "email": "juan1234@example.com",
        "active": true 
    },
    {
        "id": 2,
        "name": "Ana Ramírez",
        "email": "ana4321@example.com",
        "active": false 
    }
]
````

####  :orange_book: :heavy_check_mark: Registra un nuevo usuario utilizando la ruta "/api/v1/user/register"

Se deberá de mandar los siguientes datos en el body de la petición:

````
{
    "name": "Juan Martín",
    "email": "juan1234@example.com"
}
````
Y la respuesta será el usuario registrado como se muestra a continuación:

````
RESPUESTA:
{
    "id": 1,
    "name": "Juan Martín",
    "email": "juan1234@example.com",
    "active": true 
}
````

#### :orange_book: :x: Eliminar un usuario o desactivar su cuenta mediante la siguiente ruta "/api/v1/user/{id}"

Parametros {id}: deberá de ir el id del usuario al que se quiere desactivar su cuenta.

Respuesta: Se devolvera un código 404.

### 2. Controlador de carritos

#### :heavy_plus_sign: :beers: Agregar productos al carrito mediante la ruta: "/api/v1/cart/add"

Se pasará por el body de la petición el id del producto y el usuario y la cantidad de piezas como se muestra a continuación:

````
{
    "product_id": 2,
    "user_id": 1,
    "pieces": 4
}
````

Y se devolverá una respuesta 200.


#### :book: Obtener los detalles generales del carrito mediante la ruta "/api/v1/cart/general/{id}"

Parametros {id}: el id del usuario.

La respuesta será la siguiente:

````
RESPUESTA: 

{
    "name": "Juan Martín",
    "number_products": 4,
    "amount": 97.6
}
````

#### :beers: :wine_glass: Obtener los detalles del carrito mediante la siguiente ruta "/api/v1/cart/details/{userId}"

Parametros {userId}: el id del usuario.

Obtendrá una lista de los productos que tiene en su carrito, así como las piezas y el monto individual, como se muestra a continuación:

````
RESPUESTA:
[
    {
        "description": "Heineken 355 ml lata",
        "code": "1234",
        "category": "CERVEZA",
        "number_pieces": 4,
        "amount": 97.6
    },
    {
        "description": "Kosako 2 litros botella pet",
        "code": "8901",
        "category": "VODKA",
        "number_pieces": 2,
        "amount": 221.2
    }
]
````

#### :heavy_minus_sign: :beer: Remover algún producto mediante la siguiente ruta: "/api/v1/cart/user/{userId}/product/{productId}"

Parametros:  
{userId} -> id del usuario
{productId} -> id del producto

Respuesta: 404

### 3. Controlador de productos

#### :beer: :beer: Mostrar todos los productos disponibles usando la ruta "/api/v1/products"

La ruta devolverá todos los productos de la tienda, como se muestra en el siguiente ejemplo:

````
[
    {
        "description": "Lambrusco 750 ml",
        "category": "VINO",
        "stock": 20,
        "price": 138.4
    },
    {
        "description": "Centenario reposado 750 ml botella",
        "category": "TEQUILA",
        "stock": 16,
        "price": 310.74
    }
]
````



### También se puede ver la documentación con swagger descargando el proyecto e iniciandolo y entrando a la ruta "http://localhost:8080/swagger-ui/index.html" en el navegador y modificando el usuario y contraseña del usuario de la base de datos