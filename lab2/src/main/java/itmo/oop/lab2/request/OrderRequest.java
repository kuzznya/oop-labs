package itmo.oop.lab2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
