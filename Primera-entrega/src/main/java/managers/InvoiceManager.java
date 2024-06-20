package managers;

import entities.Client;
import entities.Invoice;
import entities.Invoice_details;
import jakarta.persistence.*;

import java.util.List;

public class InvoiceManager {
    public void create(Invoice_details invoiceDetails) throws Exception {
        if (invoiceDetails == null) throw new Exception("Es necesario ingresar el Detalle de compra.");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Invoice invoice = new Invoice();
            invoice.setClient(invoiceDetails.getClient());
            Double total = invoiceDetails.getPrice() * invoiceDetails.getAmount();
            invoice.setTotal(total);
            manager.persist(invoice);
            transaction.commit();
            System.out.println("Factura creada exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Invoice> readByClient(Client client) throws Exception {
        if (client == null) throw new Exception("Es necesario ingresar el cliente.");
        EntityManager manager = null;
        List<Invoice> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("SELECT id FROM Invoice id WHERE id.client = :client", Invoice.class)
                    .setParameter("client", client)
                    .getResultList();
            if (lista.isEmpty()) throw new Exception("El cliente aún no tiene facturas.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

    public Invoice readById(Integer id) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id de la factura.");
        EntityManager manager = null;
        Invoice invoice = null;
        try {
            manager = GenericManager.getEntityManager();
            invoice = manager.find(Invoice.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return invoice;
    }

    public List<Invoice> readAll() throws Exception {
        EntityManager manager = null;
        List<Invoice> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Invoice", Invoice.class).getResultList();
            if (lista.isEmpty()) throw new Exception("La lista de facturas se encuentra vacia.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }
}
