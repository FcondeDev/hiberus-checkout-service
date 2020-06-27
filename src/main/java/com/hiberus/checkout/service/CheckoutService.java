package com.hiberus.checkout.service;

import com.hiberus.commons.dto.CheckoutDTO;
import com.hiberus.commons.dto.CheckoutResponseDTO;

public interface CheckoutService {

	public CheckoutResponseDTO createOrder(CheckoutDTO checkoutDTO);
}
