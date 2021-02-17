package org.julio.gregorio.adidas.test.subscription;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/features/subscription_service",
		"json:target/features/subscription_service.json" }, features = "classpath:features/subscription_service.feature")
public class SubscriptionServiceTest {

}
