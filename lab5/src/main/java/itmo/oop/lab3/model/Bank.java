package itmo.oop.lab3.model;

import itmo.oop.lab3.model.client.Client;
import itmo.oop.lab3.model.client.ClientAccount;
import itmo.oop.lab3.model.spec.AccountSpec;
import itmo.oop.lab3.model.spec.AccountType;
import itmo.oop.lab3.model.transaction.CancellationTransaction;
import itmo.oop.lab3.model.transaction.Transaction;
import itmo.oop.lab3.util.DateTimeProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class Bank {

    @Getter
    private final UUID id = UUID.randomUUID();
    private final TransferSystem transferSystem;
    protected final DateTimeProvider dateTimeProvider;

    protected final List<BankAccount> accounts = new ArrayList<>();

    @Getter
    protected final List<Transaction> executedTransactions = new ArrayList<>();

    protected Bank(TransferSystem transferSystem, DateTimeProvider dateTimeProvider) {
        this.transferSystem = transferSystem;
        this.dateTimeProvider = dateTimeProvider;

        transferSystem.registerBank(this);
    }

    protected abstract void createAccountData(BankAccount createdAccount);
    protected abstract double getBalance(BankAccount account);
    protected abstract void withdraw(BankAccount account, double amount);
    protected abstract void replenish(BankAccount account, double amount);
    protected abstract AccountSpec getAccountSpec(AccountType type);

    private BankContext createContext() {
        return new BankContext() {
            @Override
            public TransferSystem getTransferSystem() {
                return transferSystem;
            }

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
        boolean check = transaction.getAccount()
                .getSpec()
                .getConstraints()
                .stream()
                .map(constraint -> constraint.test(transaction))
                .reduce(true, (b1, b2) -> b1 && b2);

        if (!check)
            throw new RuntimeException("Cannot execute transaction " + transaction.getId() + ": check failed");
        transaction.execute(createContext());
        executedTransactions.add(transaction);
    }

    private void cancel(Transaction transaction) {
        execute(new CancellationTransaction(transaction));
    }

    public void cancelTransactionById(UUID id) {
        executedTransactions.stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .ifPresentOrElse(this::execute,
                        () -> {throw new IllegalArgumentException("Transaction with id " + id + " not found");});
    }

    public ClientAccount createAccount(Client client, AccountType type) {
        BankAccount bankAccount = new BankAccount(client, getAccountSpec(type));
        accounts.add(bankAccount);
        createAccountData(bankAccount);
        return new ClientAccount(bankAccount);
    }

    public Optional<BankAccount> findAccountById(AccountIdentifier id) {
        return accounts.stream()
                .filter(acc -> acc.getId().equals(id))
                .findAny();
    }

    public boolean accountExists(AccountIdentifier id) {
        return findAccountById(id).isPresent();
    }


    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public final class BankAccount extends Account {
        @Getter
        private final AccountSpec spec;

        private BankAccount(Client client, AccountSpec spec) {
            super(new AccountIdentifier(Bank.this.getId(), UUID.randomUUID()),
                    client,
                    dateTimeProvider.currentDateTime());
            this.spec = spec;
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
