package itmo.oop.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Store {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Getter
    private String name;

    @Getter
    private String address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @Getter
    @JsonIgnore
    private List<Product> products;

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Optional<Product> getProduct(UUID itemId) {
        return products
                .stream()
                .filter(product -> product.getItem().getId().equals(itemId))
                .findAny();
    }

    public Optional<Product> orderProduct(UUID itemId, int amount) {
        Optional<Product> optionalProduct = getProduct(itemId)
                .filter(product -> product.getAmount() >= amount);
        if (optionalProduct.isEmpty())
            return Optional.empty();
        Product product = optionalProduct.get();
        product.setAmount(product.getAmount() - amount);

        return Optional.of(
                new Product(product.getItem(), this, amount, product.getPrice())
        );
    }

    public void addProduct(Item item, int amount, float price) {
        products.add(new Product(item, this, amount, price));
    }
}
