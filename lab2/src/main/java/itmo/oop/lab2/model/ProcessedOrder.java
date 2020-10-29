package itmo.oop.lab2.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ProcessedOrder {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Store store;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    private List<OrderPosition> positions;

    public ProcessedOrder(Store store) {
        this.store = store;
        positions = new ArrayList<>();
    }

    public void addPosition(Product product) {
        if (!product.getStore().getId().equals(store.getId()))
            throw new IllegalArgumentException("Product from other store cannot be added to order");
        positions.add(new OrderPosition(this, product.getItem(), product.getAmount(), product.getPrice()));
    }

    public float getTotalSum() {
        return (float) positions
                .stream()
                .mapToDouble(position -> position.getAmount() * position.getPrice())
                .sum();
    }
}
