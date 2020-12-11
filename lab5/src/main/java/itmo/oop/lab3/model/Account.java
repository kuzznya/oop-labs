package itmo.oop.lab3.model;

import itmo.oop.lab3.model.client.Client;
import lombok.*;

import java.time.ZonedDateTime;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public abstract class Account {

    @Getter
    private final AccountIdentifier id;
    @Getter
    private final Client client;
    @Getter
    private final ZonedDateTime creationDate;

    public abstract double getBalance();

    public boolean isSuspicious() {
        return client.getAddress().isPresent() && client.getPassport().isPresent();
    }
}
