package managers;

import entities.Client;
import entities.Invoice_details;
import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class InvoiceDetailsManager {
    public void addToInvoiceDetail(Integer amount, Double price, Client client, Product product) throws Exception {
        if (amount == null || price == null || client == null || product == null)
            throw new Exception("Es necesario ingresar todos los campos para agregar un producto");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            Integer stock = product.getStock() - amount;
            if (stock < 0) throw new Exception("Stock insuficiente para agregar el producto.");
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Invoice_details invoiceDetails = new Invoice_details(amount, price, client, product);
            manager.persist(invoiceDetails);
            transaction.commit();
            ProductManager producto = new ProductManager();
            producto.updatedById(product.getId(), null, null, null, stock);
            System.out.println("Detalle de factura creado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public void removeToInvoiceDetail(Integer id) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id.");
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Invoice_details invoiceDetails = manager.find(Invoice_details.class, id);
            if (invoiceDetails != null) {
                manager.remove(invoiceDetails);
                transaction.commit();
            }
            System.out.println("Detalle de factura eliminado exitosamente");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Invoice_details> readByClient(Client client) throws Exception {
        if (client == null) throw new Exception("Es necesario ingresar el cliente.");
        EntityManager manager = null;
        List<Invoice_details> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("SELECT id FROM Invoice_details id WHERE id.client = :client", Invoice_details.class)
                    .setParameter("client", client)
                    .getResultList();
            if (lista.isEmpty()) throw new Exception("El cliente aún no tiene productos en su carrito.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return lista;
    }

    public Invoice_details readById(Integer id) throws Exception {
        if (id == null) throw new Exception("Es necesario ingresar el id.");
        EntityManager manager = null;
        Invoice_details invoiceDetails = null;
        try {
            manager = GenericManager.getEntityManager();
            invoiceDetails = manager.find(Invoice_details.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return invoiceDetails;
    }

    public List<Invoice_details> readAll() throws Exception {
        EntityManager manager = null;
        List<Invoice_details> lista = null;
        try {
            manager = GenericManager.getEntityManager();
            lista = manager.createQuery("From Invoice_details", Invoice_details.class).getResultList();
            if (lista.isEmpty()) throw new Exception("La lista de carritos se encuentra vacia.");
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
