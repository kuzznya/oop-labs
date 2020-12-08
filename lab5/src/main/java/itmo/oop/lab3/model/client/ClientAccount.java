package itmo.oop.lab3.model.client;

import itmo.oop.lab3.model.Account;
import itmo.oop.lab3.model.AccountIdentifier;
import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.transaction.ReplenishmentTransaction;
import itmo.oop.lab3.model.transaction.TransferTransaction;
import itmo.oop.lab3.model.transaction.WithdrawTransaction;

public class ClientAccount extends Account {

    private final Bank.BankAccount bankAccount;

    public ClientAccount(Bank.BankAccount bankAccount) {
        super(bankAccount.getId(), bankAccount.getClient(), bankAccount.getCreationDate());
        this.bankAccount = bankAccount;
    }

    @Override
    public final AccountIdentifier getId() {
        return bankAccount.getId();
    }

    @Override
    public final Client getClient() {
        return bankAccount.getClient();
    }

    @Override
    public final double getBalance() {
        return bankAccount.getBalance();
    }

    protected final void requestWithdraw(double amount) {
        bankAccount.execute(new WithdrawTransaction(bankAccount, amount));
    }

    protected final void requestReplenishment(double amount) {
        bankAccount.execute(new ReplenishmentTransaction(bankAccount, amount));
    }

    protected final void requestTransfer(Bank.BankAccount receiver, double amount) {
        // TODO: 09.12.2020 use AccountIdentifier instead of BankAccount
        bankAccount.execute(new TransferTransaction(bankAccount, receiver, amount));
    }
}
