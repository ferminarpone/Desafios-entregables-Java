package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Invoice_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InoviceDetailsRepository extends JpaRepository<Invoice_details, Long> {
}
