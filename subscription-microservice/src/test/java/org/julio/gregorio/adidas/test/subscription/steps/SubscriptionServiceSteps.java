package org.julio.gregorio.adidas.test.subscription.steps;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import javax.validation.ConstraintViolationException;

import org.julio.gregorio.adidas.subscription.error.NotFoundException;
import org.julio.gregorio.adidas.subscription.service.SubscriptionService;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionDTO;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionRequestDTO;
import org.julio.gregorio.adidas.test.TestAppInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration
@SpringBootTest(classes = TestAppInitializer.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SubscriptionServiceSteps {

	@Autowired
	private SubscriptionService subscriptionService;

	private Long requestedId;

	private SubscriptionRequestDTO subscriptionRequestDTO;

	private Object receivedResponse;

	private Exception receivedException;

	private Pageable pageableRequest;

	@Given("^I have a subscriptionRequestDTO$")
	public void i_have_a_subscriptionRequestDTO() throws Throwable {
		subscriptionRequestDTO = new SubscriptionRequestDTO();
	}

	@Given("^I have a valid subscriptionRequestDTO$")
	public void i_have_a_valid_subscriptionRequestDTO() throws Throwable {	
		Calendar c = Calendar.getInstance();
		
		subscriptionRequestDTO = new SubscriptionRequestDTO();
		subscriptionRequestDTO.setConsent(true);
		c.set(1984, 4, 18);
		subscriptionRequestDTO.setDateOfBirth(c.getTime());
		subscriptionRequestDTO.setEmail("test@email.es");
		subscriptionRequestDTO.setFirstName("test user");
		subscriptionRequestDTO.setGender("M");
		subscriptionRequestDTO.setNewsLetterId(1l);
	}

	@Given("^I have a valid id$")
	public void i_have_a_valid_id() throws Throwable {
		requestedId = 1l;
	}

	@Given("^I have an invalid id$")
	public void i_have_an_invalid_id() throws Throwable {
		requestedId = -1l;
	}

	@Given("^I have a pageSize: (\\d+)$")
	public void i_have_a_pageSize(int size) throws Throwable {
		pageableRequest = PageRequest.of(0, size);
	}

	@When("^I create subscription$")
	public void i_create_subscription() throws Throwable {
		try {
			receivedResponse = subscriptionService.createSubscription(null, subscriptionRequestDTO);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I update subscription$")
	public void i_update_subscription() throws Throwable {
		try {
			subscriptionService.updateSubscription(null, requestedId, subscriptionRequestDTO);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I get subscription$")
	public void i_get_subscription() throws Throwable {
		try {
			receivedResponse = subscriptionService.getSubscription(requestedId);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I remove subscription$")
	public void i_remove_subscription() throws Throwable {
		try {
			subscriptionService.removeSubscription(null, requestedId);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I search subscriptions$")
	public void i_search_subscription() throws Throwable {
		try {
			receivedResponse = subscriptionService
					.searchSubscriptions(pageableRequest != null ? pageableRequest : Pageable.unpaged());
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@Then("^I will receive a ConstraintViolationException$")
	public void i_will_receive_a_ConstraintViolationException() throws Throwable {
		assertNotNull(receivedException);
		assertTrue(receivedException instanceof ConstraintViolationException);
	}

	@Then("^I will receive a NotFoundException$")
	public void i_will_receive_a_NotFoundException() throws Throwable {
		assertNotNull(receivedException);
		assertTrue(receivedException instanceof NotFoundException);
	}

	@Then("^I will receive no error$")
	public void i_will_receive_no_error() throws Throwable {
		assertNull(receivedException);
	}

	@Then("^I will receive an id$")
	public void i_will_receive_an_id() throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof Long);
	}

	@Then("^I will receive a subscription$")
	public void i_will_receive_a_subscription() throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof SubscriptionDTO);
	}

	@Then("^I will receive a page of subscriptions$")
	public void i_will_receive_a_page_of_subscriptions() throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof Page);
	}

	@SuppressWarnings("rawtypes")
	@Then("^I will receive a page of subscriptions with size: (\\d+)$")
	public void i_will_receive_a_page_of_subscriptions_with_size(int expectedSize) throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof Page
				&& ((Page) receivedResponse).getSize() == expectedSize);
	}
}
