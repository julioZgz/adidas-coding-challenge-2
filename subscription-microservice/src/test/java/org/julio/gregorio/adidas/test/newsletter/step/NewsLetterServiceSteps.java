package org.julio.gregorio.adidas.test.newsletter.step;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintViolationException;

import org.julio.gregorio.adidas.subscription.error.NotFoundException;
import org.julio.gregorio.adidas.subscription.service.NewsLetterService;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterDTO;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterRequestDTO;
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
public class NewsLetterServiceSteps {

	@Autowired
	private NewsLetterService newsLetterService;

	private Long requestedId;

	private NewsLetterRequestDTO newsLetterRequestDTO;

	private Object receivedResponse;

	private Exception receivedException;

	private Pageable pageableRequest;

	@Given("^I have a newsLetterRequestDTO$")
	public void i_have_a_newsLetterRequestDTO() throws Throwable {
		newsLetterRequestDTO = new NewsLetterRequestDTO();
	}

	@Given("^I have an invalid campaignYear$")
	public void i_have_an_invalid_campaignYear() throws Throwable {
		newsLetterRequestDTO.setCampaignYear("INVALID");
	}

	@Given("^I have a valid campaignYear$")
	public void i_have_a_valid_campaignYear() throws Throwable {
		newsLetterRequestDTO.setCampaignYear("2021");
	}

	@Given("^I have a valid id$")
	public void i_have_a_valid_id() throws Throwable {
		requestedId = 2l;
	}

	@Given("^I have an invalid id$")
	public void i_have_an_invalid_id() throws Throwable {
		requestedId = -1l;
	}

	@Given("^I have a pageSize: (\\d+)$")
	public void i_have_a_pageSize(int size) throws Throwable {
		pageableRequest = PageRequest.of(0, size);
	}

	@When("^I create newsletter$")
	public void i_create_newsletter() throws Throwable {
		try {
			receivedResponse = newsLetterService.createNewsLetter(newsLetterRequestDTO);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I update newsletter$")
	public void i_update_newsletter() throws Throwable {
		try {
			newsLetterService.updateNewsLetter(requestedId, newsLetterRequestDTO);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I get newsletter$")
	public void i_get_newsletter() throws Throwable {
		try {
			receivedResponse = newsLetterService.getNewsLetter(requestedId);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I remove newsletter$")
	public void i_remove_newsletter() throws Throwable {
		try {
			newsLetterService.removeNewsLetter(requestedId);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@When("^I search newsletters$")
	public void i_search_newsletter() throws Throwable {
		try {
			receivedResponse = newsLetterService
					.searchNewsLetters(pageableRequest != null ? pageableRequest : Pageable.unpaged());
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

	@Then("^I will receive a newsletter$")
	public void i_will_receive_a_subscription() throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof NewsLetterDTO);
	}

	@Then("^I will receive a page of newsletters$")
	public void i_will_receive_a_page_of_newsletters() throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof Page);
	}

	@SuppressWarnings("rawtypes")
	@Then("^I will receive a page of newsletters with size: (\\d+)$")
	public void i_will_receive_a_page_of_newsletters_with_size(int expectedSize) throws Throwable {
		assertNull(receivedException);
		assertTrue(receivedResponse != null && receivedResponse instanceof Page
				&& ((Page) receivedResponse).getSize() == expectedSize);
	}

}
