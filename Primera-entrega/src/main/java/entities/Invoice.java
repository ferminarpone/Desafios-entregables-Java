package entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime created_at;
    @Column
    private Double total;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Invoice(){
        this.created_at =  LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(created_at, invoice.created_at) && Objects.equals(total, invoice.total) && Objects.equals(client, invoice.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created_at, total, client);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", created_at=" + created_at +
                ", total=" + total +
                ", client=" + client +
                '}';
    }
}
