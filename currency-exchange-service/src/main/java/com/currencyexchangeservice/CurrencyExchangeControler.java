package com.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeControler {

	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private Environment environment;

	@GetMapping("/from/{from}/to/{to}")
	public CurrencyExchange retriveExchange(@PathVariable String from, @PathVariable String to) {

		// CurrencyExchange currencyExchange = new CurrencyExchange(1001L, from, to,
		// BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Could not find " + from + " and " + to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}
