package itmo.oop.lab3.model.transaction;

import itmo.oop.lab3.model.BankContext;

public class CancellationTransaction extends Transaction {

    private final Transaction cancelling;

    public CancellationTransaction(Transaction cancelling) {
        super(cancelling.account, -cancelling.amount);
        this.cancelling = cancelling;
    }

    @Override
    public void execute(BankContext context) {
        cancelling.cancel();
        executionContext = context;
    }

    @Override
    public void cancel() {
        cancelling.execute(executionContext);
    }
}
