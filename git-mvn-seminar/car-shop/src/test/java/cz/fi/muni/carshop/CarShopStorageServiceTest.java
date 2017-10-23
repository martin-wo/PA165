package cz.fi.muni.carshop;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;
import cz.fi.muni.carshop.exceptions.RequestedCarNotFoundException;
import cz.fi.muni.carshop.services.CarShopStorageService;
import cz.fi.muni.carshop.services.CarShopStorageServiceImpl;

public class CarShopStorageServiceTest {

	private CarShopStorageService service;

	@Before
	public void setup() {
		service = new CarShopStorageServiceImpl();
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test()
	public void testPriceCantBeNegative() {
		// JUnit 4.11
		//thrown.expect(IllegalArgumentException.class);
		// JUnit 4.12
		thrown.reportMissingExceptionWithMessage("We expect exception on negative price").expect(IllegalArgumentException.class);

		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, -1));
	}

	@Test
	public void testGetCar() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));

		assertTrue(service.isCarAvailable(Color.BLACK, CarTypes.AUDI).isPresent());
	}

	@Test
	public void testCarShopStorage_containsTypeForExistingCar() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
		Map<CarTypes, List<Car>> cars = CarShopStorage.getInstancce().getCars();

		assertThat(cars, hasKey(CarTypes.AUDI));
	}

	// expected to fail with JUnit < 4.11
	@Test
	public void testGetCheaperCars_returnsCorrectResult() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 889000));
		service.addCarToStorage(new Car(Color.WHITE, CarTypes.AUDI, 2016, 859000));
		service.addCarToStorage(new Car(Color.BLUE, CarTypes.AUDI, 2016, 909000));

		assertThat(service.getCheaperCarsOfSameTypeAndYear(new Car(Color.BLACK, CarTypes.AUDI, 2016, 900000)),
				hasSize(3));

	}
	
	@Test
	public void testSellCar() {
		Car bmw2015 = new Car(Color.RED, CarTypes.BMW, 2015, 900000);
		Car bmw2016 = new Car(Color.BLACK, CarTypes.BMW, 2016, 950000);
		Car bmw2017 = new Car(Color.BLUE, CarTypes.BMW, 2017, 1000000);
		service.addCarToStorage(bmw2015);
		service.addCarToStorage(bmw2016);
		service.addCarToStorage(bmw2017);
		
		try {
			service.sellCar(bmw2015);
		} catch (RequestedCarNotFoundException e) {
			e.printStackTrace();
		}
		
		assertThat(CarShopStorage.getInstancce().getCars().get(CarTypes.BMW)).hasSize(2);
	}

	@Test
	public void testSellUnavailableCar() {
		Car bmw2016 = new Car(Color.RED, CarTypes.BMW, 2016, 900000);
		
		assertThatThrownBy(() -> service.sellCar(bmw2016)).isInstanceOf(RequestedCarNotFoundException.class);
	}
}
