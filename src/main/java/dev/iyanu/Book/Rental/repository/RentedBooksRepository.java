package dev.iyanu.Book.Rental.repository;

import dev.iyanu.Book.Rental.entity.RentedBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentedBooksRepository extends JpaRepository<RentedBooks, Integer> {

    @Query("select r.title from RentedBooks r where r.title =:title")
    RentedBooks findBYOnlyTitle(@Param("title") String title);
    RentedBooks findByTitle(String title);
}
