package itmo.oop.lab2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddItemRequest {
    @JsonProperty(required = true)
    private String name;
}
