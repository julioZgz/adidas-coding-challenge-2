package org.julio.gregorio.adidas.subscription.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.julio.gregorio.adidas.subscription.error.BadRequestException;
import org.julio.gregorio.adidas.subscription.error.NotFoundException;
import org.julio.gregorio.adidas.subscription.service.dto.EmailRequestDTO;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionDTO;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionRequestDTO;
import org.julio.gregorio.adidas.subscription.service.mappers.SubscriptionMapper;
import org.julio.gregorio.adidas.subscription.service.model.NewsLetter;
import org.julio.gregorio.adidas.subscription.service.model.Subscription;
import org.julio.gregorio.adidas.subscription.service.repository.NewsLetterRepository;
import org.julio.gregorio.adidas.subscription.service.repository.SubscriptionRepository;
import org.julio.gregorio.adidas.subscription.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private NewsLetterRepository newsLetterRepository;

	@Autowired
	private SubscriptionMapper subscriptionMapper;

	@Autowired
	private EmailUtil emailUtil;

	public Page<SubscriptionDTO> searchSubscriptions(Pageable pageable) {
		return subscriptionRepository.findAll(pageable != null ? pageable : Pageable.unpaged())
				.map(subscriptionMapper::toDto);
	}

	public SubscriptionDTO getSubscription(@NotNull Long id) {
		Optional<Subscription> subscription = subscriptionRepository.findById(id);
		if (!subscription.isPresent()) {
			throw new NotFoundException("Subscription doesnt exist");
		}

		return subscriptionMapper.toDto(subscription.get());
	}

	public Long createSubscription(String authorization,
			@NotNull @Valid SubscriptionRequestDTO subscriptionRequestDTO) {
		Optional<NewsLetter> newsLetter = newsLetterRepository.findById(subscriptionRequestDTO.getNewsLetterId());
		if (!newsLetter.isPresent()) {
			throw new BadRequestException("The Newsletter doesnt exist");
		}

		Subscription subscription = subscriptionMapper.toEntity(subscriptionRequestDTO);

		subscription.setNewsletter(newsLetter.get());

		subscription = subscriptionRepository.save(subscription);

		notifyNewSubscription(authorization, subscription);

		return subscription != null ? subscription.getId() : null;
	}

	private void notifyNewSubscription(String authorization, Subscription subscription) {
		EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
		emailRequestDTO.setFromEmail("no-repply@adidas.com");
		emailRequestDTO.setDestEmail(subscription.getEmail());
		emailRequestDTO.setEmailSubject("Subscription added");
		emailRequestDTO.setEmailContent("<div>You are now subscrived for campaing "
				+ subscription.getNewsletter().getCampaignYear() + " newsletter</div>");

		emailUtil.sendEmail(authorization, emailRequestDTO);
	}

	public void updateSubscription(String authorization, @NotNull Long id,
			@NotNull @Valid SubscriptionRequestDTO subscriptionRequestDTO) {
		Optional<NewsLetter> newsLetter = newsLetterRepository.findById(subscriptionRequestDTO.getNewsLetterId());
		if (!newsLetter.isPresent()) {
			throw new BadRequestException("The Newsletter doesnt exist");
		}

		Optional<Subscription> currentSubscription = subscriptionRepository.findById(id);
		if (!currentSubscription.isPresent()) {
			throw new NotFoundException("Subscription doesnt exist");
		}

		Subscription subscription = subscriptionMapper.toEntity(subscriptionRequestDTO);
		subscription.setNewsletter(newsLetter.get());
		subscription.setId(currentSubscription.get().getId());

		subscriptionRepository.save(subscription);

		notifySubscriptionModification(authorization, subscription);
	}

	private void notifySubscriptionModification(String authorization, Subscription subscription) {
		EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
		emailRequestDTO.setFromEmail("no-repply@adidas.com");
		emailRequestDTO.setDestEmail(subscription.getEmail());
		emailRequestDTO.setEmailSubject("Subscription modified");
		emailRequestDTO.setEmailContent("<div>Your subscription for campaign "
				+ subscription.getNewsletter().getCampaignYear() + " newsletter has been modified</div>");

		emailUtil.sendEmail(authorization, emailRequestDTO);
	}

	public void removeSubscription(String authorization, @NotNull Long id) {
		Optional<Subscription> currentSubscription = subscriptionRepository.findById(id);
		if (!currentSubscription.isPresent()) {
			throw new NotFoundException("Subscription doesnt exist");
		}

		subscriptionRepository.delete(currentSubscription.get());

		notifySubscriptionDelete(authorization, currentSubscription.get());
	}

	private void notifySubscriptionDelete(String authorization, Subscription subscription) {
		EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
		emailRequestDTO.setFromEmail("no-repply@adidas.com");
		emailRequestDTO.setDestEmail(subscription.getEmail());
		emailRequestDTO.setEmailSubject("Subscription removed");
		emailRequestDTO.setEmailContent("<div>Your subscription for campaign "
				+ subscription.getNewsletter().getCampaignYear() + " newsletter has been removed</div>");

		emailUtil.sendEmail(authorization, emailRequestDTO);
	}

}