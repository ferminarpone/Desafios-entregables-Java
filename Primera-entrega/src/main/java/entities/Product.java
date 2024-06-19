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

    @ManyToMany
    @JoinTable(
            name = "Product_InvoiceDetail",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "invoiceDetail_id")
    )
    private List<Invoice_details> invoiceDetail = new ArrayList<>();

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

    public List<Invoice_details> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<Invoice_details> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(description, product.description) && Objects.equals(code, product.code) && Objects.equals(price, product.price) && Objects.equals(stock, product.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, code, price, stock);
    }
}
