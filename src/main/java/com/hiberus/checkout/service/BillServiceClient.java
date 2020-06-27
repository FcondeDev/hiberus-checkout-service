package com.hiberus.checkout.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.hiberus.commons.service.BillServiceFeignClient;

@FeignClient(name = "hiberus-bill-service", url = "${url.bill.service}")
public interface BillServiceClient extends BillServiceFeignClient {

}
