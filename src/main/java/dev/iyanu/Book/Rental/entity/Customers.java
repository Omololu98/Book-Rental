package dev.iyanu.Book.Rental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String emailAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rented_to_customer", referencedColumnName = "customerId")
    private List<RentedBooks> rentedBooksList;
}
