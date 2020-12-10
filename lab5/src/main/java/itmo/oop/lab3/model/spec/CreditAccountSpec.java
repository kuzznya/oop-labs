package itmo.oop.lab3.model.spec;

public class CreditAccountSpec extends AccountSpec {
    protected CreditAccountSpec(double creditLimit, double fixedFee) {
        super(AccountSpec.builder()
                .addFee((account, transaction) ->
                        account.getBalance() - transaction.getAmount() < 0 ? fixedFee : 0.0)
                .addConstraint((account, transaction) ->
                        account.getBalance() + transaction.getAmount() > -Math.abs(creditLimit))
        );
    }
}
