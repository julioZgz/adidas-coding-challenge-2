package org.julio.gregorio.adidas.test.email;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/features/email_service",
		"json:target/features/email_service.json" }, features = "classpath:features/email_service.feature")
public class EmailServiceTest {

}
