package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Invoice_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @Column
    private Integer amount;
    @ManyToMany(mappedBy = "invoiceDetail")
    private List<Product> products;
    @Column
    private Double price;

    public Invoice_details(){}

    public Invoice_details(Invoice invoice, Integer amount, Double price) {
        this.invoice = invoice;
        this.amount = amount;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice_details that = (Invoice_details) o;
        return Objects.equals(id, that.id) && Objects.equals(invoice, that.invoice) && Objects.equals(amount, that.amount) && Objects.equals(products, that.products) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoice, amount, products, price);
    }

    @Override
    public String toString() {
        return "Invoice_details{" +
                "id=" + id +
                ", invoice=" + invoice +
                ", amount=" + amount +
                ", products=" + products +
                ", price=" + price +
                '}';
    }
}
