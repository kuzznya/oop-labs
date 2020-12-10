package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;

public class ReplenishmentTransaction extends Transaction {

    public ReplenishmentTransaction(Bank.BankAccount client, double amount) {
        super(client, Math.abs(amount));
    }

    @Override
    public void execute(BankContext context) {
        context.replenish(super.account, super.amount);
    }

    @Override
    public void cancel(BankContext context) {
        context.withdraw(super.account, super.amount);
    }
}
