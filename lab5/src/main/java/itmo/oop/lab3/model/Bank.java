package itmo.oop.lab3.model;

import itmo.oop.lab3.model.client.Client;
import itmo.oop.lab3.model.client.ClientAccount;
import itmo.oop.lab3.model.transaction.CancellationTransaction;
import itmo.oop.lab3.model.transaction.Transaction;
import itmo.oop.lab3.util.DateTimeProvider;
import lombok.Getter;

import java.util.UUID;

public abstract class Bank {

    @Getter
    private final UUID id;

    private final DateTimeProvider dateTimeProvider;

    protected Bank(UUID id, DateTimeProvider dateTimeProvider) {
        this.id = id;
        this.dateTimeProvider = dateTimeProvider;

    }

    protected abstract double getBalance(BankAccount account);
    protected abstract void withdraw(BankAccount account, double amount);
    protected abstract void replenish(BankAccount account, double amount);

    private BankContext createContext() {
        return new BankContext() {
            @Override
            public void withdraw(BankAccount account, double amount) {
                Bank.this.withdraw(account, amount);
            }

            @Override
            public void replenish(BankAccount account, double amount) {
                Bank.this.replenish(account, amount);
            }
        };
    }

    private void execute(Transaction transaction) {
        transaction.execute(createContext());
        // TODO: 09.12.2020 save transaction into log
    }

    private void cancel(Transaction transaction) {
        execute(new CancellationTransaction(transaction));
    }

    public ClientAccount createAccount(Client client) {
        BankAccount bankAccount = new BankAccount(client);
        // TODO: 09.12.2020 remember bank account
        return new ClientAccount(bankAccount);
    }


    public final class BankAccount extends Account {

        private BankAccount(Client client) {
            super(new AccountIdentifier(Bank.this.getId(), UUID.randomUUID()),
                    client,
                    dateTimeProvider.currentDateTime());
        }

        @Override
        public double getBalance() {
            return Bank.this.getBalance(this);
        }

        public void execute(Transaction transaction) {
            Bank.this.execute(transaction);
        }

        public void cancel(Transaction transaction) {
            Bank.this.cancel(transaction);
        }
    }
}
