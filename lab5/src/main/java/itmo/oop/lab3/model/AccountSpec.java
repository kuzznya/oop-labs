package itmo.oop.lab3.model;

import itmo.oop.lab3.model.transaction.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSpec {
    @Getter
    private final List<BiPredicate<Account, Transaction>> constraints;
    @Getter
    private final Function<Account, Double> dailyCalculation;

    protected AccountSpec(AccountSpecBuilder builder) {
        constraints = builder.constraints;
        dailyCalculation = builder.dailyCalculation;
    }

    protected static AccountSpecBuilder builder() {
        return new AccountSpecBuilder();
    }

    protected static class AccountSpecBuilder {

        private final List<BiPredicate<Account, Transaction>> constraints = new ArrayList<>();
        private Function<Account, Double> dailyCalculation;

        public AccountSpecBuilder addConstraint(BiPredicate<Account, Transaction> constraint) {
            constraints.add(constraint);
            return this;
        }

        public AccountSpecBuilder dailyCalculation(Function<Account, Double> calculation) {
            dailyCalculation = calculation;
            return this;
        }
    }
}
