package org.julio.gregorio.adidas.subscription.api;

import java.net.URI;
import java.util.List;

import org.julio.gregorio.adidas.subscription.service.NewsLetterService;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterDTO;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterRequestDTO;
import org.julio.gregorio.adidas.subscription.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "api/newsletter")
public class NewsLetterController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsLetterService newsLetterService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.") })
	@GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NewsLetterDTO>> searchNewsLetters(Pageable pageable) {
		log.debug("REST request received: searchNewsLetters");

		Page<NewsLetterDTO> page = newsLetterService.searchNewsLetters(pageable);

		HttpHeaders paginationHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "api/newsletter/search");

		return ResponseEntity.ok().headers(paginationHeaders).body(page.getContent());
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NewsLetterDTO> getNewsLetter(
			@PathVariable(name = "id", required = true) @ApiParam(value = "NewsLetter id to be recovered", required = true) Long id) {
		log.debug("REST request received - getNewsLetter: id {}", id);

		NewsLetterDTO result = newsLetterService.getNewsLetter(id);

		if (result == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createNewsLetter(@ModelAttribute NewsLetterRequestDTO newsLetterRequestDTO) {
		log.debug("REST request received - createNewsLetter: {}", newsLetterRequestDTO);

		Long createdNewsLetterId = newsLetterService.createNewsLetter(newsLetterRequestDTO);

		if (createdNewsLetterId != null) {
			URI location = UriComponentsBuilder.fromUriString("/api/newsletter/").build().toUri();
			return ResponseEntity.created(location).body(createdNewsLetterId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateNewsLetter(
			@PathVariable(name = "id", required = true) @ApiParam(value = "NewsLetter id to be updated", required = true) Long id,
			@ModelAttribute NewsLetterRequestDTO newsLetterRequestDTO) {
		log.debug("REST request received - updateNewsLetter: id {} with: {}", id, newsLetterRequestDTO);

		newsLetterService.updateNewsLetter(id, newsLetterRequestDTO);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> removeNewsLetter(
			@PathVariable(name = "id", required = true) @ApiParam(value = "NewsLetter id to be deleted", required = true) Long id) {
		log.debug("REST request received . removeNewsLetter: id {}", id);

		newsLetterService.removeNewsLetter(id);

		return ResponseEntity.ok().build();
	}

}