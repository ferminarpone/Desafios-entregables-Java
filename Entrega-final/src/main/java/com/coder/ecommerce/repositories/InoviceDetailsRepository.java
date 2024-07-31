package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InoviceDetailsRepository extends JpaRepository<Invoice_details, Long> {
    List<Invoice_details> findByClientAndDelivered(Client client, Boolean delivered);
}
