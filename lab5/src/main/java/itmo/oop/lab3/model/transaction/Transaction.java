package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Transaction {
    @Getter
    protected final UUID id = UUID.randomUUID();
    @Getter
    protected final Bank.BankAccount account;
    @Getter
    protected final double amount;

    protected BankContext executionContext;

    public abstract void execute(BankContext context);
    public abstract void cancel();
}
