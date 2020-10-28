package itmo.oop.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    private Item item;

    @ManyToOne
    @Getter
    private Store store;

    @Getter
    @Setter
    private int amount;

    @Getter
    @Setter
    private float price;

    public Product(Item item, Store store, int amount, float price) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        if (price <= 0)
            throw new IllegalArgumentException("Price should be positive");
        this.item = item;
        this.store = store;
        this.amount = amount;
        this.price = price;
    }
}
