package itmo.oop.lab3.model;

import itmo.oop.lab3.model.spec.*;
import itmo.oop.lab3.util.DateTimeProvider;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class FinancialBank extends Bank {

    private final Map<AccountIdentifier, Double> accountMoneyMap = new HashMap<>();

    protected FinancialBank(TransferSystem transferSystem, DateTimeProvider dateTimeProvider) {
        super(transferSystem, dateTimeProvider);
    }

    @Override
    protected void createAccountData(BankAccount createdAccount) {
        accountMoneyMap.put(createdAccount.getId(), 0.0);
    }

    @Override
    protected double getBalance(BankAccount account) {
        return accountMoneyMap.get(account.getId());
    }

    @Override
    protected void withdraw(BankAccount account, double amount) {
        accountMoneyMap.computeIfPresent(account.getId(), (id, value) -> value - amount);
    }

    @Override
    protected void replenish(BankAccount account, double amount) {
        accountMoneyMap.computeIfPresent(account.getId(), (id, value) -> value + amount);
    }

    public abstract double creditLimit();
    public abstract double creditFee();
    public abstract double debitCashbackPerAnnum();
    public abstract Duration depositTime();
    public abstract double depositPercentPerAnnum();

    @Override
    protected AccountSpec getAccountSpec(AccountType type) {
        switch (type) {
            case CREDIT:
                return new CreditAccountSpec(creditLimit(), creditFee());
            case DEBIT:
                return new DebitAccountSpec(debitCashbackPerAnnum());
            case DEPOSIT:
                return new DepositAccountSpec(super.dateTimeProvider, depositTime(), depositPercentPerAnnum());
            default:
                throw new RuntimeException("Unsupported account spec");
        }
    }
}
