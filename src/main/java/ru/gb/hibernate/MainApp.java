package ru.gb.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MainApp {
    private static SessionFactory factory;
    private static Product p;

    public static void init() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public static void main(String[] args) {

        try {
            init();
            prepareData();
            ProductDao productDao = new ProductDao(factory);
            try (Session session = factory.getCurrentSession()) {
               System.out.println(productDao.findAllProducts());
               System.out.println(productDao.findById(1L));
               productDao.delete(productDao.findById(2L));
               System.out.println(productDao.findAllProducts());
               p = productDao.findById(3L);
               p.setPrice(4000);
               productDao.saveOrUpdate(p);
               System.out.println(productDao.findAllProducts());
            }
        } finally {
            shutdown();
        }
    }

    public static void prepareData() {
        Session session = null;
        try {
            String sql = Files.lines(Paths.get("full.sql")).collect(Collectors.joining(" "));
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void shutdown() {
        factory.close();
    }
}
