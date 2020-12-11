package itmo.oop.lab3.model.spec;

public class CreditAccountSpec extends AccountSpec {
    public CreditAccountSpec(double creditLimit, double fixedFee) {
        super(AccountSpec.builder()
                .addFee(transaction ->
                        transaction.getAccount().getBalance() - transaction.getAmount() < 0 ? fixedFee : 0.0)
                .addConstraint(transaction ->
                        transaction.getAccount().getBalance() + transaction.getAmount() > -Math.abs(creditLimit))
        );
    }
}
