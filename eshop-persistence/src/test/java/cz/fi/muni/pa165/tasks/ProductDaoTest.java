package cz.fi.muni.pa165.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@Transactional
public class ProductDaoTest extends AbstractTestNGSpringContextTests {
	
    @Autowired
    private ProductDao productDao;

	@Test
	public void removeProductTest() {
		Product p1 = new Product();
		p1.setName("TestProduct1");
		productDao.create(p1);

		Product p2 = new Product();
		p2.setName("TestProduct2");
		productDao.create(p2);
		
		productDao.remove(p2);
		
		Assert.assertEquals(productDao.findAll().size(), 1);
		Assert.assertEquals(productDao.findById(p2.getId()), null);
		Assert.assertEquals(productDao.findByName("TestProduct1").get(0), p1);
	}
}
