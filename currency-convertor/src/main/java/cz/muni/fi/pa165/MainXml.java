package cz.muni.fi.pa165;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.muni.fi.pa165.currency.CurrencyConvertor;

public class MainXml {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
		BigDecimal result = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1));
		System.out.println(result);
	}

}
