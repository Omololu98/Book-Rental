package dev.iyanu.Book.Rental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {
    private String email;
    private float amountToBePaid;
    private String paymentEmail;
    @JsonProperty("authorization_url")
    private String paymentUrl;
    @JsonProperty("reference")
    private String referenceCode;

    @JsonProperty("amount")
    private String amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("currency")
    private String currency;
}
