package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.AccountIdentifier;
import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.BankContext;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransferTransaction extends Transaction {

    private final WithdrawTransaction withdrawTransaction;
    private ReplenishmentTransaction replenishmentTransaction;
    private final AccountIdentifier receiverId;

    public TransferTransaction(Bank.BankAccount sender, AccountIdentifier receiverId, double amount) {
        super(sender, -Math.abs(amount));
        withdrawTransaction = new WithdrawTransaction(sender, -amount);
        this.receiverId = receiverId;
    }

    @Override
    public void execute(BankContext context) {
        withdrawTransaction.execute(context);
        replenishmentTransaction = context.getTransferSystem()
                .createReplenishmentTransaction(receiverId, super.amount);
        replenishmentTransaction.account.execute(replenishmentTransaction);
        executionContext = context;
    }

    @Override
    public void cancel() {
        withdrawTransaction.cancel();
        replenishmentTransaction.cancel();
    }
}
