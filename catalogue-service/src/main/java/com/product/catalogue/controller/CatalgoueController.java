package com.product.catalogue.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.catalogue.document.Availability;
import com.product.catalogue.document.Listings;
import com.product.catalogue.repository.AvailaibilityDomainRepository;
import com.product.catalogue.repository.DataAccessLayerRepoClass;
import com.product.catalogue.repository.DataAccessLayerRepoClassImpl;
import com.product.catalogue.repository.ListingDomainRepository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RequestMapping("catalogue-service")
@RestController
public class CatalgoueController {

	@Autowired
	ListingDomainRepository listRepo;

	@Autowired
	AvailaibilityDomainRepository availabilityRepo;

	@Autowired
	DataAccessLayerRepoClass templateRemo;

	ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger logger = LoggerFactory.getLogger(CatalgoueController.class);

	@GetMapping("/id")
	public String getProductJSONFromId(@RequestParam String id) throws JsonProcessingException, ProductNotFoundException {
		Optional<Listings> product = listRepo.findById(id);
		if (product.isPresent())
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product.get());
		else
			throw new ProductNotFoundException(id);
	}

	@PutMapping("/price")
	public HttpStatus updatePriceById(@RequestParam String id, @RequestParam int price)
			throws ProductNotFoundException {
		Optional<Listings> product = listRepo.findById(id);
		if (product.isPresent()) {
			Listings inventoryObj = product.get();
			inventoryObj.setPrice(price);
			listRepo.save(inventoryObj);
			return HttpStatus.OK;
		} else
			throw new ProductNotFoundException(id);
	}

	@GetMapping("/availability")
	public int getQuantityOfProductLeft(@RequestParam String productId) throws ProductNotFoundException {
		try {
			Availability avail = templateRemo.getAvailabilityInfo(productId);
			return avail.getQuantity();
		} catch (Exception e) {
			throw new ProductNotFoundException(productId);
		}
	}

	@PutMapping("/add")
	public HttpStatus addAvailability(@RequestParam String productId, @RequestParam int quantityToAdd)
			throws ProductNotFoundException {
		try {
			Availability avail = templateRemo.getAvailabilityInfo(productId);
			avail.setQuantity(avail.getQuantity() + quantityToAdd);
			availabilityRepo.save(avail);
			return HttpStatus.OK;
		} catch (Exception e) {
			throw new ProductNotFoundException(productId);
		}
	}

	@PutMapping("/decrease")
	public HttpStatus decreaseAvailability(@RequestParam String productId, @RequestParam int quantityToDecrease)
			throws ProductNotFoundException {
		try {
			logger.info("Recieved a request to decrease stock");
			Availability avail = templateRemo.getAvailabilityInfo(productId);
			if ((avail.getQuantity() - quantityToDecrease) < 0)
				return HttpStatus.BAD_REQUEST;
			avail.setQuantity(avail.getQuantity() - quantityToDecrease);
			availabilityRepo.save(avail);
			return HttpStatus.OK;
		} catch (Exception e) {
			throw new ProductNotFoundException(productId);
		}
	}

	@PostMapping("/new")
	public String addNewProduct(@RequestParam String nameOfProduct, @RequestParam int price,
			@RequestParam int numberOfUnits) throws JsonProcessingException {
		Listings product = new Listings();
		product.setName(nameOfProduct);
		product.setPrice(price);
		Listings savedEntity = listRepo.save(product);
		Availability avail = new Availability();
		avail.setListingId(savedEntity.getId());
		avail.setQuantity(numberOfUnits);
		availabilityRepo.save(avail);
		String listingJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedEntity);
		return listingJson;
	}

}
