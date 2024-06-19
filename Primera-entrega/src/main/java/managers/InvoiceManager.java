package managers;

import entities.Client;
import jakarta.persistence.*;

import java.util.List;

public class InvoiceManager {
    public void create(String name, String lastName, Integer dni){
        EntityManager manager = GenericManager.getEntityManager();
        manager.getTransaction().begin();
        Client cliente = new Client(name, lastName, dni);
        manager.persist(cliente);
        manager.getTransaction().commit();
        manager.close();
    }
    public List<Client> readAll(){
        EntityManager manager = GenericManager.getEntityManager();
        List<Client> lista = manager.createQuery("From Client", Client.class).getResultList();
        manager.close();
        return lista;
    }

    public Client readById(Integer id){
        EntityManager manager =  GenericManager.getEntityManager();
        Client cliente = manager.find(Client.class, id);
        manager.close();
        return cliente;
    }

    public void deleteById(Integer id){
        EntityManager manager = GenericManager.getEntityManager();
        manager.getTransaction().begin();
        Client cliente = manager.find(Client.class, id);
        if( cliente != null){
            manager.remove(cliente);
            manager.getTransaction().commit();
        }
        manager.close();
    }
}
