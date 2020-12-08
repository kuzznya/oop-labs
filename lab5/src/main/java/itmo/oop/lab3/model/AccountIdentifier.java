package itmo.oop.lab3.model;

import lombok.Value;

import java.util.UUID;

@Value
public class AccountIdentifier {
    UUID bankId;
    UUID accountId;
}
