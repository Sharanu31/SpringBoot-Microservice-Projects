package com.currency.conversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	@Autowired
	CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion-resttemplate/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		// RestTemplate Implementation starts

		Map<String, String> urivariable = new HashMap<>();
		urivariable.put("from", from);
		urivariable.put("to", to);

		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, urivariable);

		CurrencyConversion conversion = responseEntity.getBody();

		// RestTemplate Implementation ends
		return new CurrencyConversion(conversion.getId(), from, to, quantity, conversion.getConversionMultiple(),
				quantity.multiply(conversion.getConversionMultiple()), conversion.getEnvironment());

	}

	@GetMapping("/currency-conversion-feing/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeing(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		// Feing Implementation starts

		CurrencyConversion conversion = proxy.retriveExchange(from, to);

		// Feing Implementation ends
		return new CurrencyConversion(conversion.getId(), from, to, quantity, conversion.getConversionMultiple(),
				quantity.multiply(conversion.getConversionMultiple()), conversion.getEnvironment());

	}
}
