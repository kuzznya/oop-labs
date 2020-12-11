package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(Bank.BankAccount account, double amount) {
        super(account, -Math.abs(amount));
    }

    @Override
    public void execute(BankContext context) {
        context.withdraw(super.account, -super.amount);
        executionContext = context;
    }

    @Override
    public void cancel() {
        executionContext.replenish(super.account, -super.amount);
    }
}
