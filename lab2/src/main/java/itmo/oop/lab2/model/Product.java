package itmo.oop.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @ManyToOne
    @Getter
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    private Store store;

    @Getter
    @Setter
    private int amount;

    @Getter
    @Setter
    private float price;

    public Product(Item item, Store store, int amount, float price) {
        this.item = item;
        this.store = store;
        this.amount = amount;
        this.price = price;
    }
}
