package metier;

import dao.IDao;
import entities.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryDaoImpl implements IDao<Category> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(Category category) {
        sessionFactory.getCurrentSession().save(category);
        return true;
    }

    @Override
    public boolean delete(Category category) {
        sessionFactory.getCurrentSession().delete(category);
        return true;
    }

    @Override
    public boolean update(Category category) {
        sessionFactory.getCurrentSession().update(category);
        return true;
    }

    @Override
    public Category findById(int id) {
        return sessionFactory.getCurrentSession().get(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Category", Category.class).list();
    }
}
