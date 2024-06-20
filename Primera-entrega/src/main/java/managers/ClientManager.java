package managers;

import entities.Client;
import jakarta.persistence.*;

import java.util.List;

public class ClientManager {
    public void create(String name, String lastName, Integer docNumber) throws Exception {
        if (name == null || lastName == null || docNumber == null)
            throw new Exception("Es necesario ingresar todos los campos para crear un nuevo cliente");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Client cliente = new Client(name, lastName, docNumber);
            manager.persist(cliente);
            transaction.commit();
            System.out.println("Cliente creado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Client> readAll() throws Exception {
        EntityManager manager = null;
        List<Client> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Client", Client.class).getResultList();
            if (lista.isEmpty()) throw new Exception("La lista de clientes se encuentra vacia.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

    public Client readById(Integer id) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id.");
        EntityManager manager = null;
        Client client = null;
        try {
            manager = GenericManager.getEntityManager();
            client = manager.find(Client.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return client;
    }

    public void updatedById(Integer id, String name, String lastName, Integer docNumber) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id para actualizar.");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Client client = manager.find(Client.class, id);
            if (client != null) {
                if (name != null) client.setName(name);
                if (lastName != null) client.setLastName(lastName);
                if (docNumber != null) client.setDocNumber(docNumber);
                manager.merge(client);
                transaction.commit();
            }
            System.out.println("Cliente actualizado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public void deleteById(Integer id) throws Exception {
        if (id == null) throw new Exception("Para eliminar un cliente es necesario ingresar el id.");
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
