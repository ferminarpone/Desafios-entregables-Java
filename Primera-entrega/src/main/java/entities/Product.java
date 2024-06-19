package entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String description;
    @Column
    private String code;
    @Column
    private Double price;
    @Column
    private Integer stock;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice_details> invoiceDetails = new ArrayList<>();

    public Product(){}

    public Product(String description, String code, Double price, Integer stock) {
        this.description = description;
        this.code = code;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<Invoice_details> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<Invoice_details> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(description, product.description) && Objects.equals(code, product.code) && Objects.equals(price, product.price) && Objects.equals(stock, product.stock) && Objects.equals(invoiceDetails, product.invoiceDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, code, price, stock, invoiceDetails);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", stock=" + stock +
              //  ", invoiceDetails=" + invoiceDetails +
                '}';
    }
}
