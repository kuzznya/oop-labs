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

    public final void requestWithdraw(double amount) {
        bankAccount.execute(new WithdrawTransaction(bankAccount, amount));
    }

    public final void requestReplenishment(double amount) {
        bankAccount.execute(new ReplenishmentTransaction(bankAccount, amount));
    }

    public final void requestTransfer(AccountIdentifier receiverId, double amount) {
        bankAccount.execute(new TransferTransaction(bankAccount, receiverId, amount));
    }
}
