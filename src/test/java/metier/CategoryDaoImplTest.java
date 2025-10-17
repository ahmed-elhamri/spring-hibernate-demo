package metier;

import dao.IDao;
import entities.Category;
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
public class CategoryDaoImplTest {

    @Autowired
    @Qualifier("categoryDaoImpl")
    private IDao<Category> categoryDao;

    private Category category;

    @Before
    public void setUp() {
        category = new Category();
        category.setName("Test Category");

        categoryDao.create(category);
    }

    @After
    public void tearDown() {
        Category foundCategory = categoryDao.findById(category.getId());
        if (foundCategory != null) {
            categoryDao.delete(foundCategory);
        }
    }

    @Test
    public void testCreate() {
        assertNotNull(category.getId());
    }

    @Test
    public void testFindById() {
        Category foundCategory = categoryDao.findById(category.getId());
        assertNotNull(foundCategory);
        assertEquals(category.getName(), foundCategory.getName());
    }

    @Test
    public void testUpdate() {
        category.setName("Updated Category");
        boolean result = categoryDao.update(category);
        assertTrue(result);

        Category updatedCategory = categoryDao.findById(category.getId());
        assertEquals("Updated Category", updatedCategory.getName());
    }

    @Test
    public void testDelete() {
        boolean result = categoryDao.delete(category);
        assertTrue(result);

        Category foundCategory = categoryDao.findById(category.getId());
        assertNull(foundCategory);
    }

    @Test
    public void testFindAll() {
        List<Category> categories = categoryDao.findAll();
        assertNotNull(categories);
        assertTrue( categories.size() > 0);

        boolean categoryFound = categories.stream()
                .anyMatch(c -> c.getId() == category.getId());
        assertTrue(categoryFound);
    }

    @Test
    public void testCreateMultipleCategories() {
        Category category2 = new Category();
        category2.setName("Second Category");
        categoryDao.create(category2);

        List<Category> categories = categoryDao.findAll();
        assertTrue(categories.size() >= 2);

        categoryDao.delete(category2);
    }

    @Test
    public void testFindByIdNotFound() {
        Category foundCategory = categoryDao.findById(99999);
        assertNull(foundCategory);
    }
}