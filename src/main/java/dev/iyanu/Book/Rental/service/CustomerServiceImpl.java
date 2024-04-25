package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.dto.CustomerDTO;
import dev.iyanu.Book.Rental.entity.Customers;
import dev.iyanu.Book.Rental.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Override
    public ResponseEntity<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        Customers customer = new Customers();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmailAddress(customerDTO.getEmailAddress());
        customerRepository.save(customer);
        customerDTO.setCustomerId(customer.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
    }

    @Override
    public ResponseEntity<CustomerDTO> updateCustomer(CustomerDTO customerDTO) {
        System.out.println(customerDTO.getCustomerId());
        System.out.println(customerDTO.getEmailAddress());
        Optional<Customers> customer = customerRepository.findByCustomerId(customerDTO.getCustomerId());
        if (customer.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomerDTO(HttpStatus.NOT_FOUND,"Customer ID not found"));
        }
        if(Objects.nonNull(customerDTO.getEmailAddress())){
            customer.get().setEmailAddress(customerDTO.getEmailAddress());
        }
        if(Objects.nonNull(customerDTO.getLastName())){
            customer.get().setLastName(customerDTO.getLastName());
        }
        if(Objects.nonNull(customerDTO.getFirstName())){
            customer.get().setFirstName(customerDTO.getFirstName());
        }
        customerRepository.save(customer.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomerDTO(HttpStatus.ACCEPTED,"Customer details has been updated"));
    }

    @Override
    public ResponseEntity<CustomerDTO> deleteCustomer(Integer customerId) {
        Optional<Customers> customer = customerRepository.findByCustomerId(customerId);
        if(customer.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomerDTO(HttpStatus.NOT_FOUND,"Customer ID not found"));
        }
        customerRepository.delete(customer.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomerDTO(HttpStatus.ACCEPTED,
                "Customer with ID:"+customer.get().getCustomerId()+" has been deleted"));
    }
}
