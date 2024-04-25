package dev.iyanu.Book.Rental.repository;

import dev.iyanu.Book.Rental.entity.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, Integer> {
    Optional<CompanyAccount> findByAccountNumber(String accountNumber);
}
