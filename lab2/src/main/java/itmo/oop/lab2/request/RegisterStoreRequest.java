package itmo.oop.lab2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterStoreRequest {
    @JsonProperty(required = true)
    private String address;
}
