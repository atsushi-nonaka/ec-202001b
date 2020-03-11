package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.domain.CreditCard;
import com.example.form.CreditCardForm;

@Service
@Transactional
public class CreditCardService {
	
	@Autowired
    private RestTemplate restTemplate;

	private static final String URL = "http://153.126.176.141:8080/sample-credit-card-web-api/credit-card/payment";
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	public CreditCard getCreditCard( CreditCardForm creditCardForm) {
        return restTemplate.postForObject(URL,creditCardForm, CreditCard.class);
    }

}
