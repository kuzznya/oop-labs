package itmo.oop.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Getter
    private String name;

    @Getter
    private String address;

    @OneToMany
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

    public void addProduct(Item item, int amount, float price) {
        products.add(new Product(item, this, amount, price));
    }
}
