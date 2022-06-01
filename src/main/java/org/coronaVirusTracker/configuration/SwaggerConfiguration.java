package org.coronaVirusTracker.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

/**
 * Class for configuration of the swagger framework
 * 
 * @author ruan
 * @see <a href=http://localhost:8100/swagger-ui.html>Swagger Gui</a>
 * @see <a href=http://localhost:8100/v3/api-docs>Swagger App Info</a>
 */
@OpenAPIDefinition(info = @Info(title = "Corona Virus Tracker API", version = "v1", description = "Documentation of Corona Virus Tracker API"))
public class SwaggerConfiguration {
	public OpenAPI customOpenAPI() {

		return new OpenAPI().components(new Components())
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Corona Virus Tracker API")
						.version("v1")
						.license(new License()
								.name("Apache 2.0")
								.url("https://springdoc.org")));
	}

}
