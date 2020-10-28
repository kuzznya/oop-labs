package itmo.oop.lab2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {

    @JsonProperty(required = true)
    private UUID storeId;

    @JsonProperty(required = true)
    private List<OrderRequestPosition> positions;

    @Data
    public static class OrderRequestPosition {
        private UUID itemId;
        private int amount;
    }
}
