package com.metaphorce.shop_all.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Metaphorce challenged - API Shop-All",
                description = "Es una api sobre las funcionalidades generales de un carrito de compras en el que puedes agregar y eliminar productos de el",
                contact = @Contact(
                        name = "Saúl Ramírez",
                        url = "https://www.linkedin.com/in/sayul-ramirez/"
                ),
                version = "0.0.1"
        )
)
@Configuration
public class SwaggerConfiguration {
}
