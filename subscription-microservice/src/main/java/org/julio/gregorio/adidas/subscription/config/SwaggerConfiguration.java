package org.julio.gregorio.adidas.subscription.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.julio.gregorio.adidas.subscription.api.NewsLetterController;
import org.julio.gregorio.adidas.subscription.api.SubscriptionController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(name = "app.api.swagger.enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfiguration {

	@Value("${app.api.version}")
	private String version;

	@Value("${app.api.title}")
	private String title;

	@Value("${app.api.group-name}")
	private String groupName;

	@Value("${app.api.description}")
	private String description;

	@Value("${app.api.contact-name}")
	private String contactName;

	@Value("${app.api.contact-email}")
	private String contactEmail;

	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static final String DEFAULT_INCLUDE_PATTERN = ".*";

	@Bean
	public Docket ordersDocket() {

		Contact contact = new Contact(contactName, null, contactEmail);

		@SuppressWarnings("rawtypes")
		List<VendorExtension> vendorExtensions = new ArrayList<>();

		ApiInfo apiInfo = apiInfo(contact, vendorExtensions);

		return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).pathMapping("/").apiInfo(apiInfo)
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()))
				.useDefaultResponseMessages(false).select() //
				.apis(includeOnly(SubscriptionController.class, NewsLetterController.class)) //
				.paths(PathSelectors.any()).build();

	}

	@SuppressWarnings("rawtypes")
	private ApiInfo apiInfo(Contact contact, List<VendorExtension> vendorExtensions) {
		return new ApiInfoBuilder().title(title).description(description).version(version)
				.contact(new Contact(contactName, null, contactEmail)).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}

	private Predicate<RequestHandler> includeOnly(Class<?>... classesToInclude) {
		return new Predicate<RequestHandler>() {
			@SuppressWarnings("deprecation")
			@Override
			public boolean apply(RequestHandler input) {
				Class<?> classToCheck = input.getHandlerMethod().getBeanType();
				return Arrays.stream(classesToInclude).anyMatch(classToCheck::equals);
			}
		};
	}

}
