package org.julio.gregorio.adidas.email.api;

import org.julio.gregorio.adidas.email.service.EmailService;
import org.julio.gregorio.adidas.email.service.dto.EmailRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/email")
public class EmailController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailService emailService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
		log.debug("REST request received . sendEmail: EmailRequestDTO {}", emailRequestDTO);

		emailService.sendEmail(emailRequestDTO);

		return ResponseEntity.ok().build();
	}

}