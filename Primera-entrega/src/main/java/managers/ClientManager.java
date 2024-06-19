package managers;

import entities.Client;
import jakarta.persistence.*;

import java.util.List;

public class ClientManager {
    public void create(String name, String lastName, Integer docNumber){
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction= manager.getTransaction();
            transaction.begin();
            Client cliente = new Client(name, lastName, docNumber);
            manager.persist(cliente);
            manager.getTransaction().commit();
            System.out.println("Cliente creado exitosamente");
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            GenericManager.closeEntityManagerFactory();
        }
    }
    public List<Client> readAll(){
        EntityManager manager = null;
        List<Client> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Client", Client.class).getResultList();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

    public Client readById(Integer id){
        EntityManager manager = null;
        Client client = null;
        try {
            manager = GenericManager.getEntityManager();
            client = manager.find(Client.class, id);
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return client;
    }

    public void deleteById(Integer id) {

        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Client client = manager.find(Client.class, id);
            if (client != null) {
                manager.remove(client);
                manager.getTransaction().commit();
            }
            System.out.println("Cliente eliminado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
}
