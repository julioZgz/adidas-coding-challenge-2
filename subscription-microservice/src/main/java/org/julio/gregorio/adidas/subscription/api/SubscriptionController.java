package org.julio.gregorio.adidas.subscription.api;

import java.net.URI;
import java.util.List;

import org.julio.gregorio.adidas.subscription.service.SubscriptionService;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionDTO;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionRequestDTO;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "api/subscription")
public class SubscriptionController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubscriptionService subscriptionService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.") })
	@GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SubscriptionDTO>> searchSubscriptions(Pageable pageable) {
		log.debug("REST request received: searchSubscriptions");

		Page<SubscriptionDTO> page = subscriptionService.searchSubscriptions(pageable);

		HttpHeaders paginationHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "api/subscription/search");

		return ResponseEntity.ok().headers(paginationHeaders).body(page.getContent());
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SubscriptionDTO> getSubscription(
			@PathVariable(name = "id", required = true) @ApiParam(value = "Subscription id to be recovered", required = true) Long id) {
		log.debug("REST request received - getSubscription: id {}", id);

		SubscriptionDTO result = subscriptionService.getSubscription(id);

		if (result == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createSubscription(@ApiIgnore @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization,
			@ModelAttribute SubscriptionRequestDTO subscriptionRequestDTO) {
		log.debug("REST request received - createSubscription: {}", subscriptionRequestDTO);

		Long createdSubscriptionId = subscriptionService.createSubscription(authorization, subscriptionRequestDTO);

		if (createdSubscriptionId != null) {
			URI location = UriComponentsBuilder.fromUriString("/api/subscription/").build().toUri();
			return ResponseEntity.created(location).body(createdSubscriptionId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateSubscription(@ApiIgnore @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization,
			@PathVariable(name = "id", required = true) @ApiParam(value = "Subscription id to be updated", required = true) Long id,
			@ModelAttribute SubscriptionRequestDTO subscriptionRequestDTO) {
		log.debug("REST request received - updateSubscription: id {} with: {}", id, subscriptionRequestDTO);

		subscriptionService.updateSubscription(authorization, id, subscriptionRequestDTO);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> removeSubscription(@ApiIgnore @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization,
			@PathVariable(name = "id", required = true) @ApiParam(value = "Subscription id to be deleted", required = true) Long id) {
		log.debug("REST request received . removeSubscription: id {}", id);

		subscriptionService.removeSubscription(authorization, id);

		return ResponseEntity.ok().build();
	}

}