package itmo.oop.lab3.model.spec;

import itmo.oop.lab3.model.Account;
import itmo.oop.lab3.model.transaction.Transaction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AccountSpec {
    @Getter
    private final List<Predicate<Transaction>> constraints;
    @Getter
    private final List<Function<Transaction, Double>> fees;
    @Getter
    private final Function<Account, Double> dailyCalculation;

    protected AccountSpec(AccountSpecBuilder builder) {
        constraints = builder.constraints;
        constraints.add(transaction ->
                !(transaction instanceof WithdrawTransaction && transaction.getAccount().isSuspicious()));
        fees = builder.fees;
        dailyCalculation = builder.dailyCalculation;
    }

    protected static AccountSpecBuilder builder() {
        return new AccountSpecBuilder();
    }

    protected static class AccountSpecBuilder {

        private final List<Predicate<Transaction>> constraints = new ArrayList<>();
        private final List<Function<Transaction, Double>> fees = new ArrayList<>();
        private Function<Account, Double> dailyCalculation = account -> 0.0;

        public AccountSpecBuilder addConstraint(Predicate<Transaction> constraint) {
            constraints.add(constraint);
            return this;
        }

        public AccountSpecBuilder addFee(Function<Transaction, Double> fee) {
            fees.add(fee);
            return this;
        }

        public AccountSpecBuilder dailyCalculation(Function<Account, Double> calculation) {
            dailyCalculation = calculation;
            return this;
        }
    }
}
