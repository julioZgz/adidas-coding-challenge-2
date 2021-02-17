package org.julio.gregorio.adidas.email.service.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.julio.gregorio.adidas.email.service.dto.EmailRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.emailEnabled", havingValue = "true")
public class EmailSender implements EmailSenderIface {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender sender;

	@Override
	@Async
	public void sendEmail(@Valid EmailRequestDTO emailRequestDTO) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(emailRequestDTO.getFromEmail());
			helper.setTo(emailRequestDTO.getDestEmail());
			helper.setText(emailRequestDTO.getEmailContent(), true);
			helper.setSubject(emailRequestDTO.getEmailSubject());
			sender.send(message);
		} catch (MessagingException e) {
			log.error("Send email error: {}", e);
		}
	}

}
