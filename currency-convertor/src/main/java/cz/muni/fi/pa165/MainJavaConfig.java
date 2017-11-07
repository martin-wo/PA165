package cz.muni.fi.pa165;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.muni.fi.pa165.currency.CurrencyConvertor;

public class MainJavaConfig {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringJavaConfig.class);
		CurrencyConvertor currencyConvertor = applicationContext
				.getBean("currencyConvertor", CurrencyConvertor.class);
		BigDecimal result = currencyConvertor.convert(
				Currency.getInstance("EUR"), Currency.getInstance("CZK"),
				new BigDecimal(1));
		System.out.println(result);
	}

}
