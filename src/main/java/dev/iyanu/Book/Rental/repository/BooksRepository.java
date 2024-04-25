package dev.iyanu.Book.Rental.repository;

import dev.iyanu.Book.Rental.entity.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends MongoRepository<Books,String> {

 List<Books> findBooksByTitle(String title);

 List<Books> findBooksByTitleContainingIgnoreCase(String title);

// @Query("{'authorName': ?0}")
 List<Books> findBooksByAuthorName(String authorName);

 List<Books> findBooksByAuthorNameContainingIgnoreCase(String authorName);

 @Query("{'authorName': ?0, 'title': ?1}")
 List<Books> findBooksByAuthorNameAndTitle(String authorName, String title);

 List<Books> findBooksByAuthorNameContainingIgnoreCaseOrTitleContainingIgnoreCase(String authorName, String title);
}
