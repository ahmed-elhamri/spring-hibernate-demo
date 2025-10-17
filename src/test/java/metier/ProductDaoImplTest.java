package metier;

import dao.IDao;
import entities.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = util.HibernateConfig.class)
public class ProductDaoImplTest {

    @Autowired
    @Qualifier("productDaoImpl")
    private IDao<Product> productDao;

    private Product product;

    @Before
    public void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);

        productDao.create(product);
    }

    @After
    public void tearDown() {
        Product foundProduct = productDao.findById(product.getId());
        if (foundProduct != null) {
            productDao.delete(foundProduct);
        }
    }

    @Test
    public void testCreate() {
        assertNotNull(product.getId());
    }

    @Test
    public void testFindById() {
        Product foundProduct = productDao.findById(product.getId());
        assertNotNull(foundProduct);
        assertEquals(product.getName(), foundProduct.getName());
    }

    @Test
    public void testUpdate() {
        product.setName("Updated Product");
        product.setPrice(149.99);
        boolean result = productDao.update(product);
        assertTrue(result);

        Product updatedProduct = productDao.findById(product.getId());
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(149.99, updatedProduct.getPrice(), 0.01);
    }

    @Test
    public void testDelete() {
        boolean result = productDao.delete(product);
        assertTrue(result);

        Product foundProduct = productDao.findById(product.getId());
        assertNull(foundProduct);
    }

    @Test
    public void testFindAll() {
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);

        boolean productFound = products.stream()
                .anyMatch(p -> p.getId() == product.getId());
        assertTrue(productFound);
    }

    @Test
    public void testCreateMultipleProducts() {
        Product product2 = new Product();
        product2.setName("Second Product");
        product2.setPrice(199.99);
        productDao.create(product2);

        List<Product> products = productDao.findAll();
        assertTrue(products.size() >= 2);

        productDao.delete(product2);
    }

    @Test
    public void testFindByIdNotFound() {
        Product foundProduct = productDao.findById(99999);
        assertNull(foundProduct);
    }
}