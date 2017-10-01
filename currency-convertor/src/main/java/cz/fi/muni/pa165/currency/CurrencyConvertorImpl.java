package cz.fi.muni.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import org.slf4j.*;

/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

	private final ExchangeRateTable exchangeRateTable;
	private final Logger logger = LoggerFactory
			.getLogger(CurrencyConvertorImpl.class);

	public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
		this.exchangeRateTable = exchangeRateTable;
	}

	@Override
	public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency,
			BigDecimal sourceAmount) {
		logger.trace("Called convert with arguments: ", sourceCurrency,
				targetCurrency, sourceAmount);
		if (sourceCurrency == null) {
			throw new IllegalArgumentException("Source currency is null.");
		}
		if (targetCurrency == null) {
			throw new IllegalArgumentException("Target currency is null.");
		}
		if (sourceAmount == null) {
			throw new IllegalArgumentException("Source amount is null.");
		}
		try {
			BigDecimal exchangeRate = exchangeRateTable
					.getExchangeRate(sourceCurrency, targetCurrency);
			if (exchangeRate == null) {
				logger.warn("Unknown exchange rate.", sourceCurrency,
						targetCurrency);
				throw new UnknownExchangeRateException(
						"Unknown exchange rate.");
			}
			return sourceAmount.multiply(exchangeRate).setScale(2,
					RoundingMode.HALF_EVEN);
		} catch (ExternalServiceFailureException e) {
			logger.error("Exchange rate lookup failed", sourceCurrency,
					targetCurrency);
			throw new UnknownExchangeRateException(
					"Exchange rate lookup failed.");
		}
	}

}
