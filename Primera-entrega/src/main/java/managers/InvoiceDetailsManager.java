package managers;

import entities.Client;
import entities.Invoice;
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
            ProductManager producto = new ProductManager();

            // if(product.getStock() - amount <= 0) LANZAR EXCEPTCION
            Integer stock = product.getStock() - amount;
            producto.updatedById(product.getId(), null, null,null, stock);
            System.out.println("Detalle de factura creado exitosamente");
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public void removeToInvoiceDetail(Integer id){
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction= manager.getTransaction();
            transaction.begin();
            Invoice_details invoiceDetails = manager.find(Invoice_details.class, id);
            if (invoiceDetails != null) {
                manager.remove(invoiceDetails);
                transaction.commit();
            }
            System.out.println("Detalle de factura eliminado exitosamente");
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

    public Invoice_details readById(Integer id){
        EntityManager manager = null;
        Invoice_details invoiceDetails = null;
        try {
            manager = GenericManager.getEntityManager();
            invoiceDetails = manager.find(Invoice_details.class, id);
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return invoiceDetails;
    }


    public List<Invoice_details> readAll(){
        EntityManager manager = null;
        List<Invoice_details> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Invoice_details", Invoice_details.class).getResultList();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }
/*
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
