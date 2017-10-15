package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	private Category category1, category2;
	private Product product1, product2, product3;
	
	@BeforeClass
	private void init() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		category1 = new Category();
		category1.setName("Electro");
		category2 = new Category();
		category2.setName("Kitchen");
		
		product1 = new Product();
		product1.setName("Flashlight");
		product2 = new Product();
		product2.setName("Kitchen robot");
		product3 = new Product();
		product3.setName("Plate");
		
		category1.addProduct(product1);
		category1.addProduct(product2);
		
		category2.addProduct(product2);
		category2.addProduct(product3);
		
		product1.addCategory(category1);
		product2.addCategory(category1);
		product2.addCategory(category2);
		product3.addCategory(category2);
		
		em.persist(product1);
		em.persist(product2);
		em.persist(product3);
		em.persist(category1);
		em.persist(category2);
		
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void category1HasExpectedProductsTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category persistedCategory1 = em.find(Category.class, category1.getId());
		em.getTransaction().commit();

		assertContainsProductWithName(persistedCategory1.getProducts(), product1.getName());
		assertContainsProductWithName(persistedCategory1.getProducts(), product2.getName());
		em.close();
	}
	
	@Test
	public void category2HasExpectedProductsTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category persistedCategory2 = em.find(Category.class, category2.getId());
		em.getTransaction().commit();
		
		assertContainsProductWithName(persistedCategory2.getProducts(), product2.getName());
		assertContainsProductWithName(persistedCategory2.getProducts(), product3.getName());
		em.close();
	}
	
	@Test
	public void product1HasExpectedCategoriesTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Product persistedProduct1 = em.find(Product.class, product1.getId());
		em.getTransaction().commit();
		
		assertContainsCategoryWithName(persistedProduct1.getCategories(), category1.getName());
		em.close();
	}

	@Test
	public void product2HasExpectedCategoriesTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Product persistedProduct2 = em.find(Product.class, product2.getId());
		em.getTransaction().commit();
		
		assertContainsCategoryWithName(persistedProduct2.getCategories(), category1.getName());
		assertContainsCategoryWithName(persistedProduct2.getCategories(), category2.getName());
		em.close();
	}
	
	@Test
	public void product3HasExpectedCategoriesTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Product persistedProduct3 = em.find(Product.class, product3.getId());
		em.getTransaction().commit();
		
		assertContainsCategoryWithName(persistedProduct3.getCategories(), category2.getName());
		em.close();
	}
	
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
