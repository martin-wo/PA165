package cz.fi.muni.pa165.currency;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

	private Currency sourceCurrency = Currency.getInstance("EUR");
	private Currency targetCurrency = Currency.getInstance("CZK");

	@Mock
	private ExchangeRateTable exchangeRateTableMock;

	private CurrencyConvertorImpl currencyConvertorMock;

	@Before
	public void init() {
		currencyConvertorMock = new CurrencyConvertorImpl(
				exchangeRateTableMock);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testConvert() throws ExternalServiceFailureException {
		when(exchangeRateTableMock.getExchangeRate(sourceCurrency,
				targetCurrency)).thenReturn(new BigDecimal("1"));

		assertEquals(new BigDecimal("0.00"), currencyConvertorMock.convert(
				sourceCurrency, targetCurrency, new BigDecimal("0.005")));
		assertEquals(new BigDecimal("0.01"), currencyConvertorMock.convert(
				sourceCurrency, targetCurrency, new BigDecimal("0.006")));
		assertEquals(new BigDecimal("0.01"), currencyConvertorMock.convert(
				sourceCurrency, targetCurrency, new BigDecimal("0.010")));
		assertEquals(new BigDecimal("0.02"), currencyConvertorMock.convert(
				sourceCurrency, targetCurrency, new BigDecimal("0.015")));
	}

	@Test
	public void testConvertWithNullSourceCurrency() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> currencyConvertorMock.convert(null,
						targetCurrency, new BigDecimal("1")));
	}

	@Test
	public void testConvertWithNullTargetCurrency() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> currencyConvertorMock.convert(sourceCurrency,
						null, new BigDecimal("1")));
	}

	@Test
	public void testConvertWithNullSourceAmount() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> currencyConvertorMock.convert(sourceCurrency,
						targetCurrency, null));
	}

	@Test
	public void testConvertWithUnknownCurrency() {
		try {
			when(exchangeRateTableMock.getExchangeRate(sourceCurrency,
					targetCurrency)).thenReturn(null);
		} catch (ExternalServiceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThatThrownBy(() -> currencyConvertorMock.convert(sourceCurrency,
				targetCurrency, new BigDecimal("1")))
						.isInstanceOf(UnknownExchangeRateException.class);
	}

	@Test
	public void testConvertWithExternalServiceFailure() {
		try {
			when(exchangeRateTableMock.getExchangeRate(sourceCurrency,
					targetCurrency))
							.thenThrow(ExternalServiceFailureException.class);
		} catch (ExternalServiceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThatThrownBy(() -> currencyConvertorMock.convert(sourceCurrency,
				targetCurrency, new BigDecimal("1")))
						.isInstanceOf(UnknownExchangeRateException.class);
	}

}
