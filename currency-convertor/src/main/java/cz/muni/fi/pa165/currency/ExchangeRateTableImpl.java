package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

	@Override
	public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency)
			throws ExternalServiceFailureException {
		return new BigDecimal(27);
	}

}
