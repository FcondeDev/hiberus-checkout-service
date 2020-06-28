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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;

	@PostMapping("checkouts")
	@ApiOperation(value = "Perform the checkout operation", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The checkout information for the client id"),
			@ApiResponse(code = 503, message = "There was a problem getting information from hiberus client service") })
	public ResponseEntity<JsonDTO<CheckoutResponseDTO>> store(@Valid @RequestBody CheckoutDTO checkoutDTO) {
		return new ResponseEntity<>(new JsonDTO<>(checkoutService.createOrder(checkoutDTO)), HttpStatus.OK);
	}

}
