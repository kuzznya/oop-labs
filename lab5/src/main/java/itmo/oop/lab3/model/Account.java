package itmo.oop.lab3.model;

import itmo.oop.lab3.model.client.Client;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Account {

    @Getter
    private final AccountIdentifier id;
    @Getter
    private final Client client;
    @Getter
    private final ZonedDateTime creationDate;

    public abstract double getBalance();
}
