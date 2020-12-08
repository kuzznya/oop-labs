package itmo.oop.lab3.model.spec;

import itmo.oop.lab3.model.Account;
import itmo.oop.lab3.model.transaction.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AccountSpec {
    @Getter
    private final List<BiPredicate<Account, Transaction>> constraints;
    @Getter
    private final List<BiFunction<Account, Transaction, Double>> fees;
    @Getter
    private final Function<Account, Double> dailyCalculation;

    protected AccountSpec(AccountSpecBuilder builder) {
        constraints = builder.constraints;
        fees = builder.fees;
        dailyCalculation = builder.dailyCalculation;
    }

    protected static AccountSpecBuilder builder() {
        return new AccountSpecBuilder();
    }

    protected static class AccountSpecBuilder {

        private final List<BiPredicate<Account, Transaction>> constraints = new ArrayList<>();
        private final List<BiFunction<Account, Transaction, Double>> fees = new ArrayList<>();
        private Function<Account, Double> dailyCalculation = account -> 0.0;

        public AccountSpecBuilder addConstraint(BiPredicate<Account, Transaction> constraint) {
            constraints.add(constraint);
            return this;
        }

        public AccountSpecBuilder addFee(BiFunction<Account, Transaction, Double> fee) {
            fees.add(fee);
            return this;
        }

        public AccountSpecBuilder dailyCalculation(Function<Account, Double> calculation) {
            dailyCalculation = calculation;
            return this;
        }
    }
}
