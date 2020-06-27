package com.hiberus.checkout.service.impl;

import static com.hiberus.checkout.helper.Constants.COUPON_DISCOUNT;
import static com.hiberus.checkout.helper.Constants.PAYMENT_METHOD_SUPPORTED;
import static com.hiberus.checkout.helper.Constants.UNIQUE_COUPON;
import static com.hiberus.checkout.helper.Constants.UNIQUE_PAYMENT_METHOD;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hiberus.checkout.service.BillServiceClient;
import com.hiberus.checkout.service.CheckoutService;
import com.hiberus.checkout.service.ClientServiceClient;
import com.hiberus.checkout.service.LogisticServiceClient;
import com.hiberus.commons.dto.BillResponseDTO;
import com.hiberus.commons.dto.CheckoutDTO;
import com.hiberus.commons.dto.CheckoutResponseDTO;
import com.hiberus.commons.dto.ClientDTO;
import com.hiberus.commons.dto.LogisticDTO;
import com.hiberus.commons.dto.LogisticResponseDTO;
import com.hiberus.commons.error.ErrorEnum;
import com.hiberus.commons.exception.CustomException;

import lombok.extern.java.Log;

@Log
@Service
public class CheckoutServiceImpl implements CheckoutService {

	@Autowired
	private ClientServiceClient clientService;

	@Autowired
	private BillServiceClient billService;

	@Autowired
	private LogisticServiceClient logisticService;

	@Override
	public CheckoutResponseDTO createOrder(CheckoutDTO checkoutDTO) {

		log.info("--- Starting Service ----");

		if (!checkoutDTO.getPaymentMethod().equals(PAYMENT_METHOD_SUPPORTED))
			throw new CustomException(ErrorEnum.INVALID_PAYMENT_METHOD, HttpStatus.BAD_REQUEST,
					ErrorEnum.INVALID_PAYMENT_METHOD.description);

		log.info(String.format("Getting information for client id : %s", checkoutDTO.getClientId()));
		ClientDTO clientDTO = clientService.index(checkoutDTO.getClientId()).getBody().getData();

		log.info("Processing bill information....");
		BillResponseDTO billResponseDTO = billService.store(checkoutDTO.getProducts()).getBody().getData();
		log.info(String.format("Bill created with id : %s", billResponseDTO.getBillId()));

		log.info("Processing logistic information...");
		LogisticResponseDTO logisticResponseDTO = logisticService
				.store(new LogisticDTO(clientDTO.getAddress(), billResponseDTO.getTotalProductsNumber())).getBody()
				.getData();
		log.info(String.format("Logistic created with id : %s", logisticResponseDTO.getDeliveryId()));

		String name = new StringBuilder(clientDTO.getFirstName()).append(" ").append(clientDTO.getLastName())
				.toString();

		boolean aCouponWasApplied = isAValidCoupon(checkoutDTO.getCoupon());

		log.info(String.format("The coupon was aplied : %s", aCouponWasApplied));

		Double totalPaid = aCouponWasApplied
				? (billResponseDTO.getTotalPaymentAmount() - billResponseDTO.getTotalPaymentAmount() * COUPON_DISCOUNT)
				: billResponseDTO.getTotalPaymentAmount();

		log.info(String.format("Total paid : %s", totalPaid));
		log.info("--- Finishing Service ----");
		return new CheckoutResponseDTO(name, clientDTO.getIdentification(), billResponseDTO.getBillId(), totalPaid,
				UNIQUE_PAYMENT_METHOD, billResponseDTO.getPaymentDeadline(), billResponseDTO.getTotalProductsNumber(),
				logisticResponseDTO.getDeliveryDate(), logisticResponseDTO.getAddress(),
				logisticResponseDTO.getDeliveryId(), aCouponWasApplied);
	}

	private boolean isAValidCoupon(String coupon) {
		Optional<String> checkCoupon = Optional.ofNullable(coupon);
		return checkCoupon.isPresent() && checkCoupon.get().equalsIgnoreCase(UNIQUE_COUPON);
	}

}
