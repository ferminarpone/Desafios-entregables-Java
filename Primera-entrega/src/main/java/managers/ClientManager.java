package managers;

import identities.Client;
import jakarta.persistence.*;

import java.util.List;

public class ClientManager {
    public void create(String nombre, String apellido, Integer dni, Integer edad){
        EntityManager manager = GenericManager.getEntityManager();
        manager.getTransaction().begin();
        Client cliente = new Client(nombre, apellido, dni, edad);
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

}
