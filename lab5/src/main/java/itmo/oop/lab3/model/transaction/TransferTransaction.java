package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;

public class TransferTransaction extends Transaction {

    private final Bank.BankAccount receiver;

    public TransferTransaction(Bank.BankAccount sender, Bank.BankAccount receiver, double amount) {
        super(sender, -Math.abs(amount));
        this.receiver = receiver;
    }

    @Override
    public void execute(BankContext context) {
        context.withdraw(super.account, super.amount);
        context.replenish(receiver, super.amount);
    }

    @Override
    public void cancel(BankContext context) {
        context.withdraw(receiver, super.amount);
        context.replenish(super.account, super.amount);
    }
}
