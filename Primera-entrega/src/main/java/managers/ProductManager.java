package managers;

import entities.Client;
import entities.Product;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class ProductManager {
    public void create(String description, String code, Double price, Integer stock){
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction= manager.getTransaction();
            transaction.begin();
            Product product = new Product(description, code, price, stock);
            manager.persist(product);
            manager.getTransaction().commit();
            System.out.println("Producto creado exitosamente");
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            GenericManager.closeEntityManagerFactory();
        }
    }
    public List<Product> readAll(){
        EntityManager manager = null;
        List<Product> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Product", Product.class).getResultList();
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

    public Product readById(Integer id){
        EntityManager manager = null;
        Product product = null;
        try {
            manager = GenericManager.getEntityManager();
            product = manager.find(Product.class, id);
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return product;
    }

    public void deleteById(Integer id) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Product product = manager.find(Product.class, id);
            if (product != null) {
                manager.remove(product);
                manager.getTransaction().commit();
            }
            System.out.println("Producto eliminado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
}
