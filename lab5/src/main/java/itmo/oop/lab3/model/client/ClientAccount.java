package itmo.oop.lab3.model.client;

import itmo.oop.lab3.model.Account;
import itmo.oop.lab3.model.AccountIdentifier;
import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.transaction.ReplenishmentTransaction;
import itmo.oop.lab3.model.transaction.TransferTransaction;
import itmo.oop.lab3.model.transaction.WithdrawTransaction;

import java.util.UUID;

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

    public final UUID requestWithdraw(double amount) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction(bankAccount, amount);
        bankAccount.execute(withdrawTransaction);
        return withdrawTransaction.getId();
    }

    public final UUID requestReplenishment(double amount) {
        ReplenishmentTransaction replenishmentTransaction = new ReplenishmentTransaction(bankAccount, amount);
        bankAccount.execute(replenishmentTransaction);
        return replenishmentTransaction.getId();
    }

    public final UUID requestTransfer(AccountIdentifier receiverId, double amount) {
        TransferTransaction transferTransaction = new TransferTransaction(bankAccount, receiverId, amount);
        bankAccount.execute(transferTransaction);
        return transferTransaction.getId();
    }
}
