import managers.ClientManager;
import managers.InvoiceDetailsManager;
import managers.ProductManager;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        //Creación y lectura de clientes
        ClientManager cliente = new ClientManager();
        //cliente.create("Juan", "Ferreyra", 39843002);
        //cliente.create("Alvaro", "Luna", 40533002);
        //cliente.create("Maria", "Bilbao", 18533402);
        System.out.println(cliente.readAll());
        System.out.println(cliente.readById(2));

        //Creación y lectura de productos
        ProductManager producto = new ProductManager();
        // producto.create("Iphone 15", "Apple1", 1000.0, 30);
        // producto.create("Macbook", "Apple2", 1500.0, 38);
        // producto.create("Apple Watch", "Apple3", 300.0, 40);
        // producto.create("Ipad Pro", "Apple4", 800.0, 20);
        System.out.println(producto.readAll());
        System.out.println(producto.readById(3));

        InvoiceDetailsManager invoiceDetail = new InvoiceDetailsManager();
        // Agregar productos al carrito (detalle de la factura)

        //invoiceDetail.addToInvoiceDetail(4, producto.readById(2).getPrice(), cliente.readById(1), producto.readById(2));
        //invoiceDetail.addToInvoiceDetail(2, producto.readById(1).getPrice(), cliente.readById(1), producto.readById(1));
        //invoiceDetail.addToInvoiceDetail(2, producto.readById(3).getPrice(), cliente.readById(2), producto.readById(3));

        //Detalle de los productos agregados al carrito por cliente.
        System.out.println(invoiceDetail.readByClient(cliente.readById(1)));
    }
}
