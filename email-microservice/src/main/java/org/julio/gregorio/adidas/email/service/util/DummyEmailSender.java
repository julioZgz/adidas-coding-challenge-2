package org.julio.gregorio.adidas.email.service.util;

import javax.validation.Valid;

import org.julio.gregorio.adidas.email.service.dto.EmailRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.emailEnabled", havingValue = "false")
public class DummyEmailSender implements EmailSenderIface {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	@Async
	public void sendEmail(@Valid EmailRequestDTO emailRequestDTO) {
		log.info("Email sent {}", emailRequestDTO);
	}

}
