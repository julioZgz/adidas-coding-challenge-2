package org.julio.gregorio.adidas.email.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.julio.gregorio.adidas.email.service.dto.EmailRequestDTO;
import org.julio.gregorio.adidas.email.service.util.EmailSenderIface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class EmailService {

	@Autowired
	private EmailSenderIface sender;

	@Async
	public void sendEmail(@NotNull @Valid EmailRequestDTO emailRequestDTO) {
		sender.sendEmail(emailRequestDTO);
	}

}