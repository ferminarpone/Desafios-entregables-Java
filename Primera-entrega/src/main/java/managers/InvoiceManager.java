package managers;

import entities.Client;
import entities.Invoice;
import entities.Invoice_details;
import entities.Product;
import jakarta.persistence.*;

import java.util.List;

public class InvoiceManager {
    public void create(Invoice_details invoiceDetails){
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = GenericManager.getEntityManager();
            transaction= manager.getTransaction();
            transaction.begin();
            Invoice invoice = new Invoice();
            invoice.setClient(invoiceDetails.getClient());
            Double total = invoiceDetails.getPrice() * invoiceDetails.getAmount();
            invoice.setTotal(total);
            manager.persist(invoice);
            transaction.commit();
            System.out.println("Factura creada exitosamente");
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }


}
