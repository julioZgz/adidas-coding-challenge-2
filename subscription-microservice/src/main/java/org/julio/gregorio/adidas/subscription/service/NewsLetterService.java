package org.julio.gregorio.adidas.subscription.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.julio.gregorio.adidas.subscription.error.NotFoundException;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterDTO;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterRequestDTO;
import org.julio.gregorio.adidas.subscription.service.mappers.NewsLetterMapper;
import org.julio.gregorio.adidas.subscription.service.model.NewsLetter;
import org.julio.gregorio.adidas.subscription.service.repository.NewsLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class NewsLetterService {

	@Autowired
	private NewsLetterRepository newsLetterRepository;

	@Autowired
	private NewsLetterMapper newsLetterMapper;

	public Page<NewsLetterDTO> searchNewsLetters(Pageable pageable) {
		return newsLetterRepository.findAll(pageable != null ? pageable : Pageable.unpaged()).map(newsLetterMapper::toDto);
	}

	public NewsLetterDTO getNewsLetter(@NotNull Long id) {
		Optional<NewsLetter> newsLetter = newsLetterRepository.findById(id);

		if (!newsLetter.isPresent()) {
			throw new NotFoundException("NewsLetter doesnt exist");
		}

		return newsLetterMapper.toDto(newsLetter.get());
	}

	public Long createNewsLetter(@NotNull @Valid NewsLetterRequestDTO newsLetterRequestDTO) {
		NewsLetter newsLetter = newsLetterMapper.toEntity(newsLetterRequestDTO);

		newsLetter = newsLetterRepository.save(newsLetter);

		return newsLetter != null ? newsLetter.getId() : null;
	}

	public void updateNewsLetter(@NotNull Long id, @NotNull @Valid NewsLetterRequestDTO newsLetterRequestDTO) {
		Optional<NewsLetter> currentNewsLetter = newsLetterRepository.findById(id);

		if (!currentNewsLetter.isPresent()) {
			throw new NotFoundException("NewsLetter doesnt exist");
		}

		NewsLetter newsLetter = newsLetterMapper.toEntity(newsLetterRequestDTO);
		newsLetter.setId(currentNewsLetter.get().getId());

		newsLetterRepository.save(newsLetter);
	}

	public void removeNewsLetter(@NotNull Long id) {
		Optional<NewsLetter> currentNewsLetter = newsLetterRepository.findById(id);
		if (!currentNewsLetter.isPresent()) {
			throw new NotFoundException("NewsLetter doesnt exist");
		}

		newsLetterRepository.delete(currentNewsLetter.get());
	}
}