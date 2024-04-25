package dev.iyanu.Book.Rental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccount {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private int accountId;
    @Column(unique = true)
    private String accountNumber;
    private String companyName;
    private int accountBalance;
    private String bankName;
}
