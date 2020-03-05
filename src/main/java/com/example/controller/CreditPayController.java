package com.example.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Controller
public class CreditPayController {
	@RequestMapping(path = "/pay", method = { RequestMethod.POST })
    public ResponseEntity charge(
            @RequestParam("stripeToken") String stripeToken,
            @RequestParam("stripeTokenType") String stripeTokenType,
            @RequestParam("stripeEmail") String stripeEmail)
    {

        Stripe.apiKey = "sk_test_X0FVq2tbmFFPM3FamH92by8h00MR5yRVOk";

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 500);
        chargeMap.put("description", "コットンTシャツ");
        chargeMap.put("currency", "jpy");
        chargeMap.put("source", stripeToken);

        try {
            Charge charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        ResponseEntity response = ResponseEntity.ok().build();

        return response;
    }
}
