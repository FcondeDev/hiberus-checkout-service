package com.hiberus.checkout.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiberus.checkout.service.CheckoutService;
import com.hiberus.commons.dto.CheckoutDTO;
import com.hiberus.commons.dto.CheckoutResponseDTO;
import com.hiberus.commons.dto.JsonDTO;

@RestController
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;

	@PostMapping("checkouts")
	public ResponseEntity<JsonDTO<CheckoutResponseDTO>> store(@Valid @RequestBody CheckoutDTO checkoutDTO) {
		return new ResponseEntity<>(new JsonDTO<>(checkoutService.createOrder(checkoutDTO)), HttpStatus.OK);
	}

}
