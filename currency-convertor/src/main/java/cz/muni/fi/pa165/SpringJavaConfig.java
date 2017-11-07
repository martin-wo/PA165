package cz.muni.fi.pa165;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;

@Configuration
@EnableAspectJAutoProxy
public class SpringJavaConfig {

	@Inject
	private ExchangeRateTable exchangeRateTable;
	
	@Bean
	public ExchangeRateTable exchangeRateTable() {
		return new ExchangeRateTableImpl();
	}
	
	@Bean
	public CurrencyConvertor currencyConvertor() {
		return new CurrencyConvertorImpl(exchangeRateTable);
	}
}
