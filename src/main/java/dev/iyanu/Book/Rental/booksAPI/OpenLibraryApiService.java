package dev.iyanu.Book.Rental.booksAPI;

import dev.iyanu.Book.Rental.dto.BooksDTo;
import dev.iyanu.Book.Rental.dto.OpenLibraryAPIResponse;
import dev.iyanu.Book.Rental.entity.Books;
import dev.iyanu.Book.Rental.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenLibraryApiService {

    private final RestTemplate restTemplate;
    private final BooksRepository booksRepository;
    private final MongoTemplate mongoTemplate;

    @Async
    public  void loadDataFromAPI(String title, String author){
        String OPEN_LIBRARY_API_ENDPOINT = "https://openlibrary.org/search.json";
        String url;
        if(Objects.isNull(title)){
//            System.out.println("A");
            url = OPEN_LIBRARY_API_ENDPOINT + "?author_name="+author+"&page=1&offset=0&limit15";
        }else if(Objects.isNull(author)){
//            System.out.println("B");
            url = OPEN_LIBRARY_API_ENDPOINT + "?title="+title+"&page=1&offset=0&limit15";
        }
        else{
            System.out.println("C");
            url = OPEN_LIBRARY_API_ENDPOINT + "?title="+title+"&author_name="+author+"&page=1&offset=0&limit15";
        }
        ParameterizedTypeReference<OpenLibraryAPIResponse> responseParameterizedTypeReference
                = new ParameterizedTypeReference<OpenLibraryAPIResponse>() {
        };
       ResponseEntity<OpenLibraryAPIResponse>  response = restTemplate.exchange(url, HttpMethod.GET,null,responseParameterizedTypeReference);
       List<Books> newBooksToSaveInDb = new ArrayList<>();
       List<Books> existingBooksInDb = booksRepository.findAll();
       Set<String> existingBooksInDbSet = existingBooksInDb.stream().map(Books::getTitle).collect(Collectors.toSet());
        if(response.getBody().getDocs().isEmpty()){
            System.out.println("Books not Found");
        }
      for(BooksDTo book : response.getBody().getDocs()){
            if(!existingBooksInDbSet.contains(book.getTitle())){
                Books bookToSave = new Books();
                bookToSave.setAuthorName(book.getAuthor());
                bookToSave.setTitle(book.getTitle());
                bookToSave.setPages(book.getPages());
                bookToSave.setAvailableLanguages(book.getAvailableLanguages());
                bookToSave.setRatings(book.getRatings());

                //Saving into the MongoDb using findAndModify(multithreading writes : READ MORE)

                //Method One
//                Query query = new Query(Criteria.where("title").is(bookToSave.getTitle()));
//                Update update = new Update().setOnInsert("title",bookToSave.getTitle()).setOnInsert("authorName",bookToSave.getAuthorName())
//                        .setOnInsert("availableLanguages",bookToSave.getAvailableLanguages()).setOnInsert("ratings",bookToSave.getRatings())
//                        .setOnInsert("pages",bookToSave.getPages());
//                FindAndModifyOptions options = new FindAndModifyOptions().upsert(true).returnNew(true);
//                Books myBook = mongoTemplate.findAndModify(query,update,options,Books.class);

                //Method Two
                Query query = new Query(Criteria.where("title").is(bookToSave.getTitle()));
                List<Books> booksList = mongoTemplate.find(query,Books.class);
                if(booksList.isEmpty()){
                    booksRepository.insert(bookToSave);
                }
            }
      }
        //booksRepository.insert(newBooksToSaveInDb);

    }


}
