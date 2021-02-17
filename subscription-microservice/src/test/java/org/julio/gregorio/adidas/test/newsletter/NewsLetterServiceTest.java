package org.julio.gregorio.adidas.test.newsletter;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/features/newsletter_service",
		"json:target/features/newsletter_service.json" }, features = "classpath:features/newsletter_service.feature")
public class NewsLetterServiceTest {

}
