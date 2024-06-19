package managers;

import entities.Client;
import entities.Invoice_details;
import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class InvoiceDetailsManager {
    public void addToInvoiceDetail(Integer amount, Double price, Client client, Product product){
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction= manager.getTransaction();
            transaction.begin();
            Invoice_details invoiceDetails = new Invoice_details(amount, price, client, product);
            manager.persist(invoiceDetails);
            transaction.commit();
            System.out.println("Detalle de factura creado exitosamente");
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Invoice_details> readByClient(Client client){
        EntityManager manager = null;
        List<Invoice_details> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("SELECT id FROM Invoice_details id WHERE id.client = :client", Invoice_details.class)
                    .setParameter("client", client)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

//remove form cart

/*    public List<Client> readAll(){
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

    public void updatedById(Integer id, String name, String lastName, Integer docNumber ){
        EntityManager manager = null;
        EntityTransaction transaction;
        try{
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Client client = manager.find(Client.class, id);
            if (client != null) {
                if(name !=null) client.setName(name);
                if(lastName !=null) client.setLastName(lastName);
                if(docNumber !=null) client.setDocNumber(docNumber);
                manager.merge(client);
                transaction.commit();
            }
            System.out.println("Cliente actualizado exitosamente");
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
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
    }*/
}
