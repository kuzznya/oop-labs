package itmo.oop.lab2.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AddProductRequest {
    private UUID itemId;
    private int amount;
    private float price;
}
