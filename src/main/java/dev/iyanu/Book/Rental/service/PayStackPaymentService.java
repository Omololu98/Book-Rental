package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.dto.APIResponse;
import dev.iyanu.Book.Rental.dto.PayStackAPIResponse;
import dev.iyanu.Book.Rental.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PayStackPaymentService {
    private final String PAYSTACK_BASE_URL = "https://api.paystack.co";
    private final RestTemplate restTemplate;

   public ResponseEntity<APIResponse> initialiseRentPayment(PaymentDTO paymentDTO){
       String url = PAYSTACK_BASE_URL + "/transaction/initialize";

       MultiValueMap<String, Object> bodyParameters = new LinkedMultiValueMap<>();
       bodyParameters.add("email", paymentDTO.getPaymentEmail());
       bodyParameters.add("amount",paymentDTO.getAmountToBePaid()*100);
       bodyParameters.add("callback_url","http://localhost:8080/api/v1/book-rental/successful_transaction");

       HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(bodyParameters,setUrlHeaders());

       ParameterizedTypeReference<PayStackAPIResponse> parameterizedTypeReference = new ParameterizedTypeReference<PayStackAPIResponse>() {
       };

       ResponseEntity<PayStackAPIResponse> response =restTemplate.exchange(url, HttpMethod.POST,entity,parameterizedTypeReference);

       return ResponseEntity.status(HttpStatus.ACCEPTED)
               .body(new APIResponse(HttpStatus.ACCEPTED, response.getBody(),"Click link to pay through payStack"));

   }

   public ResponseEntity<APIResponse> verifyPayment(String referenceCode){
       String url = PAYSTACK_BASE_URL + "/transaction/verify/{reference}";
       HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(null,setUrlHeaders());
       ParameterizedTypeReference<PayStackAPIResponse> parameterizedTypeReference = new ParameterizedTypeReference<PayStackAPIResponse>() {
       };
       ResponseEntity<PayStackAPIResponse> response = restTemplate.exchange(url,HttpMethod.GET,entity,parameterizedTypeReference,referenceCode);

       if(Objects.requireNonNull(response.getBody()).getData().getStatus().equalsIgnoreCase("success"))
       {
           return ResponseEntity.status(HttpStatus.OK)
                   .body(new APIResponse(HttpStatus.OK,response.getBody()," Customer has made Payment"));
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(new APIResponse(HttpStatus.NOT_FOUND,response.getBody(),"Payment has not been made"));
   }

   private HttpHeaders setUrlHeaders(){
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       String SECRET_KEY = "Bearer sk_test_e6afb728ab3c19068ec6de36f9a7d109fb3b642f";
       headers.set("Authorization", SECRET_KEY);
       return headers;
   }

   private MultiValueMap<String, Object> bodyParameters(PaymentDTO paymentDTO){
       MultiValueMap<String, Object> bodyParameters = new LinkedMultiValueMap<>();
       bodyParameters.add("email", paymentDTO.getPaymentEmail());
       bodyParameters.add("amount",paymentDTO.getAmountToBePaid()*100);
       return bodyParameters;
   }

}
