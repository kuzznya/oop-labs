package itmo.oop.lab3.model.spec;

import itmo.oop.lab3.model.transaction.CancellationTransaction;
import itmo.oop.lab3.model.transaction.ReplenishmentTransaction;
import itmo.oop.lab3.util.DateTimeProvider;

import java.time.Duration;

public class DepositAccountSpec extends AccountSpec {
    public DepositAccountSpec(DateTimeProvider timeProvider, Duration depositTime, double depositPercentPerAnnum) {
        super(AccountSpec.builder()
                .addConstraint((account, transaction) ->
                        (transaction instanceof ReplenishmentTransaction ||
                                transaction instanceof CancellationTransaction) ||
                                account.getCreationDate().isAfter(timeProvider.currentDateTime().plus(depositTime)))
                .addConstraint((account, transaction) -> account.getBalance() + transaction.getAmount() >= 0)
                .dailyCalculation(account -> account.getBalance() * depositPercentPerAnnum / 100 / 365)
        );
    }
}
