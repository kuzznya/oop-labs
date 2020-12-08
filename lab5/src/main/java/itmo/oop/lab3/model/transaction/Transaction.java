package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Transaction {

    @Getter
    protected final Bank.BankAccount account;
    @Getter
    protected final double amount;

    public abstract void execute(BankContext context);
    public abstract void cancel(BankContext context);
}
