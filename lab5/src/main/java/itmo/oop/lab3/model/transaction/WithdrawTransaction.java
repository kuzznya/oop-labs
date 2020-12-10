package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(Bank.BankAccount account, double amount) {
        super(account, -Math.abs(amount));
    }

    @Override
    public void execute(BankContext context) {
        context.withdraw(super.account, super.amount);
    }

    @Override
    public void cancel(BankContext context) {
        context.replenish(super.account, super.amount);
    }
}
