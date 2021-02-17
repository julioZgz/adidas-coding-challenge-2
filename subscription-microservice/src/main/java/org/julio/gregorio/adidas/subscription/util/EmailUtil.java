package org.julio.gregorio.adidas.subscription.util;

import org.julio.gregorio.adidas.subscription.service.dto.EmailRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class EmailUtil {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${email-microservice.host}")
	private String emailMicroserviceHost;

	public void sendEmail(String authorization, EmailRequestDTO emailRequestDTO) {
		if (emailRequestDTO != null) {
			try {
				String serviceURL = emailMicroserviceHost + "/api/email";

				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				mapper.registerModule(new JavaTimeModule());
				String requestBody;
				try {
					requestBody = mapper.writer().writeValueAsString(emailRequestDTO);

					log.debug("Before sending request to {} with body: {}", serviceURL, requestBody);
				} catch (JsonProcessingException e) {
					log.debug("Error parsin body for log", e);
				}

				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
				if (!StringUtils.isEmpty(authorization)) {
					headers.add(HttpHeaders.AUTHORIZATION, authorization);
				}

				HttpEntity<?> requestEntity = new HttpEntity<>(emailRequestDTO, headers);

				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
				restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter());

				ResponseEntity<Void> response = restTemplate.postForEntity(serviceURL, requestEntity, Void.class);

				if (response == null) {
					log.debug("Null response from {}", serviceURL);
				} else if (!HttpStatus.OK.equals(response.getStatusCode())) {
					log.debug("Response from {} with status: {}", serviceURL, response.getStatusCode());
				}
			} catch (Exception e) {
				log.error("Error sending email", e);
			}
		}
	}

}
