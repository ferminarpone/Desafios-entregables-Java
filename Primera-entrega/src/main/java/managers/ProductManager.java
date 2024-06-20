package managers;

import entities.Product;
import jakarta.persistence.*;

import java.util.List;

public class ProductManager {
    public void create(String description, String code, Double price, Integer stock) throws Exception {
        if (description == null || code == null || price == null || stock == null)
            throw new Exception("Es necesario ingresar todos los campos para crear un nuevo producto");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Product product = new Product(description, code, price, stock);
            manager.persist(product);
            manager.getTransaction().commit();
            System.out.println("Producto creado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Product> readAll() throws Exception {
        EntityManager manager = null;
        List<Product> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Product", Product.class).getResultList();
            if (lista.isEmpty()) throw new Exception("La lista de productos se encuentra vacia.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

    public Product readById(Integer id) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id.");
        EntityManager manager = null;
        Product product = null;
        try {
            manager = GenericManager.getEntityManager();
            product = manager.find(Product.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return product;
    }

    public void updatedById(Integer id, String description, String code, Double price, Integer stock) throws Exception {
        if (id == null) throw new Exception("Para actualizar el producto es necesario ingresar el id.");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Product product = manager.find(Product.class, id);
            if (product != null) {
                if (description != null) product.setDescription(description);
                if (code != null) product.setCode(code);
                if (price != null) product.setPrice(price);
                if (stock != null) product.setStock(stock);
                manager.merge(product);
                transaction.commit();
            }
            System.out.println("Producto actualizado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public void deleteById(Integer id) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id.");
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
