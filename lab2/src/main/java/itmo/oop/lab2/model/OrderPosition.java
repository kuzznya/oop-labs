package itmo.oop.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderPosition {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @ManyToOne
    @Getter
    @JsonIgnore
    private ProcessedOrder order;

    @ManyToOne
    @Getter
    private Item item;

    @Getter
    private int amount;

    @Getter
    private float price;

    protected OrderPosition(ProcessedOrder order, Item item, int amount, float price) {
        this.order = order;
        this.item = item;
        this.amount = amount;
        this.price = price;
    }
}
