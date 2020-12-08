package itmo.oop.lab3.model.spec;

public class DebitAccountSpec extends AccountSpec {
    public DebitAccountSpec(double percentPerAnnum) {
        super(AccountSpec.builder()
                .addConstraint((account, transaction) -> account.getBalance() - transaction.getAmount() >= 0)
                .dailyCalculation(account -> account.getBalance() * percentPerAnnum / 100 / 365)
        );
    }
}
