package com.order.orders.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.orders.document.Orders;
import com.order.orders.repository.OrdersDomainRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("order-service")
@RestController
public class OrdersController {

	@Autowired
	OrdersDomainRepository repo;

	ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
	


	@PutMapping("/buy")
	@Operation(summary = "Buy product end point", security = @SecurityRequirement(name = "basicAuth"))
	public String buyProduct(@RequestParam String productId, @RequestParam String userId, @RequestParam int quantity)
			throws JsonProcessingException {

		final String uri = "https://catalogue-service:8444/catalogue-service/availability";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("productId", productId);

		ResponseEntity<Integer> availableUnitsResponse = restTemplate.getForEntity(builder.toUriString(),
				Integer.class);

		int unitsVaialble = availableUnitsResponse.getBody();

		if (unitsVaialble < quantity) {
			logger.warn("Not enough available units of product " +productId);
			return "Not available units";
		}
		else {
			String buyUri = "https://catalogue-service:8444/catalogue-service/decrease";
			UriComponentsBuilder builderBuy = UriComponentsBuilder.fromHttpUrl(buyUri)
					.queryParam("productId", productId).queryParam("quantityToDecrease", quantity);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<HttpStatus> buyResponse = restTemplate.exchange(builderBuy.toUriString(),
					HttpMethod.PUT,
					entity,
					HttpStatus.class);
			if (buyResponse.getBody().equals(HttpStatus.OK)) {
				Orders order = new Orders();
				order.setProductId(productId);
				order.setUserId(userId);
				Orders savedEntity = repo.save(order);
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedEntity);
			} else {
				return "Cannot buy at this time";
			}
		}

	}
	

}
