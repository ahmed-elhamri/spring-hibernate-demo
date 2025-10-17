package presentation;

import dao.IDao;
import entities.Category;
import entities.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.HibernateConfig;

import java.util.List;

public class Presentation2 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        IDao<Category> categoryDao = context.getBean("categoryDaoImpl", IDao.class);
        IDao<Product> productDao = context.getBean("productDaoImpl", IDao.class);

        Category cat1 = new Category();
        cat1.setName("Électronique");
        categoryDao.create(cat1);

        Product p1 = new Product();
        p1.setName("Smartphone");
        p1.setPrice(500.0);
        p1.setCategory(cat1);
        productDao.create(p1);

        Product p2 = new Product();
        p2.setName("Laptop");
        p2.setPrice(1200.0);
        p2.setCategory(cat1);
        productDao.create(p2);

        List<Category> categories = categoryDao.findAll();
        System.out.println("Catégories :");
        for (Category c : categories) {
            System.out.println("- " + c.getName());
        }

        List<Product> products = productDao.findAll();
        System.out.println("Produits :");
        for (Product p : products) {
            System.out.println("- " + p.getName() + " (" + p.getCategory().getName() + ")");
        }

        Product product = productDao.findById(1);
        System.out.println("Product :" + product.getName());

        product.setName("Smartphone Updated");
        productDao.update(product);
        Product product2 = productDao.findById(3);
        productDao.delete(product2);
    }
}
