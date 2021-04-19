package ru.gb.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class ProductDao {
   private SessionFactory sessionFactory;

   public ProductDao(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory;
   }
    public Product findById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

public List<Product> findAllProducts(){
    try (Session session = sessionFactory.getCurrentSession()) {
        session.beginTransaction();
        List<Product> products = (List<Product>)session.createQuery("Select p from Product p", Product.class).getResultList();
        session.getTransaction().commit();
        return products;
    }
}
    public Product saveOrUpdate(Product product) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
            return product;
        }
    }

    public void delete(Product product) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
    }
}
