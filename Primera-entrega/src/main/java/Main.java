import managers.ProductManager;

public class Main {
    public static void main(String[] args) {
        ProductManager producto = new ProductManager();
        producto.create("Iphone 15", "Apple1", 1000.0, 30);
        producto.create("Macbook", "Apple2", 1500.0, 38);
        producto.create("Apple Watch", "Apple3", 300.0, 40);
        producto.create("Ipad Pro", "Apple4", 800.0, 20);
        producto.readAll();
    }
}
