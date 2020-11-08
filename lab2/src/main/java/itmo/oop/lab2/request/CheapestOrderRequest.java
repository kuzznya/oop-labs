package itmo.oop.lab2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CheapestOrderRequest {

    @JsonProperty(required = true)
    private List<OrderRequest.OrderRequestPosition> positions;
}
