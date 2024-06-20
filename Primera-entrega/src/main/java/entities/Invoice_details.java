package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Invoice_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer amount;
    @Column
    private Double price;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Invoice_details() {
    }

    public Invoice_details(Integer amount, Double price, Client client, Product product) {
        this.amount = amount;
        this.price = price;
        this.client = client;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice_details that = (Invoice_details) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(price, that.price) && Objects.equals(client, that.client) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, price, client, product);
    }

    @Override
    public String toString() {
        return "Invoice_details{" +
                "id=" + id +
                ", amount=" + amount +
                ", price=" + price +
                ", client=" + client +
                ", product=" + product +
                '}';
    }
}
