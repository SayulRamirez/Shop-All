# API - SHOP-ALL  :moneybag: :convenience_store:
_____

El proyecto shop-all es un challenged traido por Metaphorce en el cual 
se realizo una api para un carrito de compras en el que puedes agregar o 
remover productos.

____

## Requisitos privios para el funcionamiento de la aplicación
- Java 17
- Maven 3.9.6

----

## Tecnologías y herramientas utilizadas
* Spring Boot 
* Spring Data JPA (para acceso a la base de datos)
* Spring Web 
* Spring Security 
* Lombok 
* Springdoc OpenAPI 
* JSON Web Tokens (JWT) 
* MySQL Connector 
* Spring Boot Validation (para la validación de los datos)
* Spring Boot Test
    - JUnit (para las pruebas unitarias)
    - Mockito (para la simulación de componentes)
* Git
* GitHub

----

## Seguridad 

Para la seguridad y protección de los endpoint de la aplicación se agrego la autenticación con Json Web Token (JWT) 
en el que solo los usuarios registrados podrán tener acceso a los servicios de la aplicación, también para mejorar la
seguridad las contraseñas se hashearon.


> [!IMPORTANT]
> La aplicación cuenta con un usuario predeterminado con un rol de ADMIN para poder acceder a algunos endpoints 
> protegidos y que además son solo accesibles por un usuario con rol ADMIN, además de que ayudara para acceder a 
> futuras rutas que serán igualmente solo accesibles con un usuario con esté rol.
> Las credenciales predeterminadas son: email -> admin@admin.com y password -> admin1234.

### Registro de nuevos usuarios.

Para poder registrar nuevos usuarios se debe de direccionar a la ruta "http://localhost:8080/auth/register"
y enviar la información en el body de la petición, como se muestra en el siguiente ejemplo:

````
{
    "name": "nombre del usuraio",
    "email": "email del usuario",
    "password": "contraseña"
}
````

Una consideración que se debe de tener, solo se puede crear un usuario por correo.

### Login de la aplicación

Una vez realizado el registro del usuario, podra logearse en la ruta "http://localhost:8080/auth/login" enivado en el 
body de la aplicación las credenciales del usuario, como se muestra en el siguiente ejemplo:

````
{
    "email": "email_user@example.com",
    "password": "secretpassword1234"
}
````

Si el login fue exitoso se devolverá como respuesta un token con duración de un día, mismo que deberá enviar en el header de las peticiones
posteriores para poder acceder a los servicios.

En dado caso que el login no sea exitoso se deberá a alguna de las siguientes problemas:
1. El usuario no se ha registrado.
2. Las credenciales son incorrectas, ya sea el email o password o ambas.

----

## Funcionalidades :clipboard:

### 1. Controlador de usuarios 

#### :woman: :man: Obtener todos los usuarios utilizando la ruta "/api/v1/user/getAll"

> [!IMPORTANT]
> El token generado en el login deberá ser enviado en el header de la petición.
> Además, solo los usuarios con un rol de "ADMIN" podrán acceder a este servicio.

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

> [!IMPORTANT]
> El token generado en el login deberá ser enviado en el header de la petición.

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

> [!IMPORTANT]
> El token generado en el login deberá ser enviado en el header de la petición.

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

> [!IMPORTANT]
> El token generado en el login deberá ser enviado en el header de la petición.

#### :heavy_minus_sign: :beer: Remover algún producto mediante la siguiente ruta: "/api/v1/cart/user/{userId}/product/{productId}"

Parametros:  
{userId} -> id del usuario
{productId} -> id del producto

Respuesta: 404

> [!IMPORTANT]
> El token generado en el login deberá ser enviado en el header de la petición.

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

> [!IMPORTANT]
> El token generado en el login deberá ser enviado en el header de la petición.

----

### Tests

La aplición cuenta con test unitarios usando JUnit y Mockito de los servicios de la aplicaión

----

> [!NOTE] También se puede ver la documentación con swagger descargando el proyecto e iniciandolo y 
> entrando a la ruta "http://localhost:8080/swagger-ui/index.html" en el navegador y modificando el 
> usuario y contraseña del usuario de la base de datos en el archivo properties de la aplicación.
> cambiando las siguientes proiedades:
> spring.datasource.url=jdbc:mysql://localhost:3306/nombre_de_la_base_de_datos
> spring.datasource.username=usuario_de_la_base_de_datos
> spring.datasource.password=contraseña_de_acceso