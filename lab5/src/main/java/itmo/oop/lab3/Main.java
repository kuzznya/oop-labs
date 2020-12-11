package itmo.oop.lab3;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.FinancialBank;
import itmo.oop.lab3.model.InterbankTransferSystem;
import itmo.oop.lab3.model.InterbankTransferSystemImpl;
import itmo.oop.lab3.model.client.Client;
import itmo.oop.lab3.model.client.ClientAccount;
import itmo.oop.lab3.model.spec.AccountType;
import itmo.oop.lab3.util.DateTimeProvider;
import itmo.oop.lab3.util.DefaultDateTimeProvider;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        Client client = Client.builder()
                .name("Ilya")
                .surname("Kuznetsov")
                .build();

        InterbankTransferSystem transferSystem = new InterbankTransferSystemImpl();
        DateTimeProvider dateTimeProvider = new DefaultDateTimeProvider();

        Bank testBank = new FinancialBank(transferSystem, dateTimeProvider) {
            @Override
            public double creditLimit() {
                return 100_000;
            }

            @Override
            public double creditFee() {
                return 10;
            }

            @Override
            public double debitCashbackPerAnnum() {
                return 10;
            }

            @Override
            public Duration depositTime() {
                return Duration.ofDays(90);
            }

            @Override
            public double depositPercentPerAnnum() {
                return 10;
            }
        };

        ClientAccount account = testBank.createAccount(client, AccountType.DEBIT);
        account.requestReplenishment(1_000);
        account.requestWithdraw(1_000);
        account.requestTransfer(account.getId(), 1_000);

        dateTimeProvider.stop();
    }
}
