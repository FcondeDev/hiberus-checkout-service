package com.hiberus.checkout.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.hiberus.commons.service.LogisticServiceFeignClient;

@FeignClient(name = "hiberus-logistic-service", url = "${url.logistic.service}")
public interface LogisticServiceClient extends LogisticServiceFeignClient {

}
