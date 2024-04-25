package dev.iyanu.Book.Rental.repository;

import dev.iyanu.Book.Rental.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, String> {
    Optional<Customers> findByCustomerId(Integer customerId);

}
