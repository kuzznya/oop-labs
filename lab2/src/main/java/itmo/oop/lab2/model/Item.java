package itmo.oop.lab2.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Getter
    @Column(unique = true)
    private String name;

    public Item(String name) {
        this.name = name;
    }
}
