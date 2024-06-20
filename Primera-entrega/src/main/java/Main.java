import managers.ClientManager;
import managers.InvoiceDetailsManager;
import managers.InvoiceManager;
import managers.ProductManager;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        //Lanzar exepciones!!

        //Creación y lectura de clientes
        ClientManager cliente = new ClientManager();
        //cliente.create("Juan", "Ferreyra", 39843002);
        //cliente.create("Alvaro", "Luna", 40533002);
        //cliente.create("Maria", "Bilbao", 18533402);
        System.out.println("Todos los clientes: ");
        System.out.println(cliente.readAll());
        System.out.println("Cliente de id 2: ");
        System.out.println(cliente.readById(2));

        //Creación y lectura de productos
        ProductManager producto = new ProductManager();
        // producto.create("Iphone 15", "Apple1", 1000.0, 30);
        // producto.create("Macbook", "Apple2", 1500.0, 38);
        // producto.create("Apple Watch", "Apple3", 300.0, 40);
        // producto.create("Ipad Pro", "Apple4", 800.0, 20);
        System.out.println("Todos los productos: ");
        System.out.println(producto.readAll());
        System.out.println("Producto con id 3: ");
        System.out.println(producto.readById(3));

        //Actualización de producto
        System.out.println("Producto con id 2: ");
        System.out.println(producto.readById(2));
        //producto.updatedById(2,null , null, 1400.0, null);
        //System.out.println("Actualización de precio producto con id 2");
        //System.out.println(producto.readById(2));

        // Agregar productos al carrito (detalle de la factura)
        InvoiceDetailsManager invoiceDetail = new InvoiceDetailsManager();
        //System.out.println("Agregando producto a carritos");
        //invoiceDetail.addToInvoiceDetail(2, producto.readById(2).getPrice(), cliente.readById(1), producto.readById(2));
        //System.out.println("Actualización de stock producto con id 2");
        //System.out.println(producto.readById(2));
        //invoiceDetail.addToInvoiceDetail(2, producto.readById(1).getPrice(), cliente.readById(1), producto.readById(1));
        //invoiceDetail.addToInvoiceDetail(2, producto.readById(3).getPrice(), cliente.readById(2), producto.readById(3));

        //Detalle de los productos agregados al carrito por cliente.
        System.out.println("Detalles de compra del Cliente con id 1: ");
        System.out.println(invoiceDetail.readByClient(cliente.readById(1)));

        //Eliminar un carrito(Invoice_detail)
        System.out.println("Todos los detalles de compra: ");
        System.out.println(invoiceDetail.readAll());
        // invoiceDetail.removeToInvoiceDetail(7);
       // System.out.println("Detalles de compra sin el detalle de id 1: ");
       // System.out.println(invoiceDetail.readAll());


        //Crear factura
        System.out.println("Creando Factura");
        InvoiceManager invoice = new InvoiceManager();
        invoice.create(invoiceDetail.readById(6));
    }
}
