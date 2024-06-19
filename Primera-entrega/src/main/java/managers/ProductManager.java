package managers;

import entities.Product;
import jakarta.persistence.*;

import java.util.List;

public class ProductManager {
    public void create(String description, String code, Double price, Integer stock){
        EntityManager manager = GenericManager.getEntityManager();
        manager.getTransaction().begin();
        Product producto = new Product(description, code, price, stock);
        manager.persist(producto);
        manager.getTransaction().commit();
        manager.close();
    }
    public List<Product> readAll(){
        EntityManager manager = GenericManager.getEntityManager();
        List<Product> lista = manager.createQuery("From Product", Product.class).getResultList();
        manager.close();
        return lista;
    }

    public Product readById(Integer id){
        EntityManager manager =  GenericManager.getEntityManager();
        Product prducto = manager.find(Product.class, id);
        manager.close();
        return prducto;
    }

/*    public void deleteById(Integer id){
        EntityManager manager = GenericManager.getEntityManager();
        manager.getTransaction().begin();
        Client cliente = manager.find(Client.class, id);
        if( cliente != null){
            manager.remove(cliente);
            manager.getTransaction().commit();
        }
        manager.close();
    }
 */

}
