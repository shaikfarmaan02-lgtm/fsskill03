package com.inventory.demo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.inventory.entity.Product;
import com.inventory.loader.ProductDataLoader;
import com.inventory.util.HibernateUtil;

public class HQLDemo {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {

            // Run once to insert sample data
             ProductDataLoader.loadSampleProducts(session);

            sortProductsByPriceAscending(session);
            sortProductsByPriceDescending(session);
            countTotalProducts(session);
            filterProductsByPriceRange(session, 20.0, 100.0);

        } finally {
            session.close();
            factory.close();
        }
    }

    public static void sortProductsByPriceAscending(Session session) {
        String hql = "FROM Product p ORDER BY p.price ASC";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> list = query.list();

        System.out.println("\n=== Price ASC ===");
        list.forEach(System.out::println);
    }

    public static void sortProductsByPriceDescending(Session session) {
        String hql = "FROM Product p ORDER BY p.price DESC";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> list = query.list();

        System.out.println("\n=== Price DESC ===");
        list.forEach(System.out::println);
    }

    public static void countTotalProducts(Session session) {
        String hql = "SELECT COUNT(p) FROM Product p";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long count = query.uniqueResult();

        System.out.println("\nTotal Products: " + count);
    }

    public static void filterProductsByPriceRange(Session session,
                                                  double min,
                                                  double max) {

        String hql = "FROM Product p WHERE p.price BETWEEN :min AND :max";
        Query<Product> query = session.createQuery(hql, Product.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        List<Product> list = query.list();

        System.out.println("\nProducts Between " + min + " and " + max);
        list.forEach(System.out::println);
    }
}