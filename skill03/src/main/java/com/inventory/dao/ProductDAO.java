package com.inventory.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class ProductDAO {

    // CREATE
    public void saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(product);   // (Hibernate 6 uses persist instead of save)

        tx.commit();
        session.close();
    }

    // READ
    public Product getProduct(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = session.get(Product.class, id);
        session.close();
        return product;
    }

    // UPDATE
    public void updateProduct(int id, double price, int quantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);

        if (product != null) {
            product.setPrice(price);
            product.setQuantity(quantity);
        }

        tx.commit();
        session.close();
    }

    // DELETE
    public void deleteProduct(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);

        if (product != null) {
            session.remove(product);
        }

        tx.commit();
        session.close();
    }
}