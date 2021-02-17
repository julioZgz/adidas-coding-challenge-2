package org.julio.gregorio.adidas.test.email.step;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintViolationException;

import org.julio.gregorio.adidas.email.service.EmailService;
import org.julio.gregorio.adidas.email.service.dto.EmailRequestDTO;
import org.julio.gregorio.adidas.test.TestAppInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration
@SpringBootTest(classes = TestAppInitializer.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmailServiceSteps {

	@Autowired
	private EmailService emailService;

	private EmailRequestDTO emailRequestDTO;

	private Exception receivedException;

	@Given("^I have a emailRequestDTO$")
	public void i_have_a_emailRequestDTO() throws Throwable {
		emailRequestDTO = new EmailRequestDTO();
	}

	@Given("^I have a valid emailRequestDTO$")
	public void i_have_a_valid_emailRequestDTO() throws Throwable {
		emailRequestDTO = new EmailRequestDTO();
		
		emailRequestDTO.setFromEmail("from@email.es");
		emailRequestDTO.setDestEmail("to@email.es");
		emailRequestDTO.setEmailSubject("test email subject");
		emailRequestDTO.setEmailContent("This is a test email");
	}

	@When("^I send email$")
	public void i_send_email() throws Throwable {
		try {
			emailService.sendEmail(emailRequestDTO);
		} catch (Exception e) {
			receivedException = e;
		}
	}

	@Then("^I will receive a ConstraintViolationException$")
	public void i_will_receive_a_ConstraintViolationException() throws Throwable {
		assertNotNull(receivedException);
		assertTrue(receivedException instanceof ConstraintViolationException);
	}

	@Then("^I will receive no error$")
	public void i_will_receive_no_error() throws Throwable {
		assertNull(receivedException);
	}

}
