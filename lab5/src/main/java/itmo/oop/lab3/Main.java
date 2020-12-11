package itmo.oop.lab3;

import itmo.oop.lab3.model.Bank;
import itmo.oop.lab3.model.FinancialBank;
import itmo.oop.lab3.model.TransferSystem;
import itmo.oop.lab3.model.TransferSystemImpl;
import itmo.oop.lab3.model.client.Client;
import itmo.oop.lab3.model.client.ClientAccount;
import itmo.oop.lab3.model.spec.AccountType;
import itmo.oop.lab3.util.DateTimeProvider;
import itmo.oop.lab3.util.DefaultDateTimeProvider;
import itmo.oop.lab3.util.DemoDateTimeProvider;

import java.time.Duration;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        DemoDateTimeProvider dateTimeProvider = new DemoDateTimeProvider();

        try {

            Client client = Client.builder()
                    .name("Ilya")
                    .surname("Kuznetsov")
                    .build();

            TransferSystem transferSystem = new TransferSystemImpl();

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

            ClientAccount account = testBank.createAccount(client, AccountType.CREDIT);
            System.out.println(account.getBalance());
            assert account.getBalance() == 0;

            UUID replenishmentId = account.requestReplenishment(1_000);
            System.out.println(account.getBalance());
            assert account.getBalance() == 1_000;

            UUID withdrawId = null;
            try {
                withdrawId = account.requestWithdraw(1_000);
            } catch (Exception ignored) { }
            assert withdrawId == null;

            client.setAddress("My address");
            client.setPassport("12 34 567890");

            withdrawId = null;
            try {
                withdrawId = account.requestWithdraw(1_000);
            } catch (Exception ignored) { }
            assert withdrawId != null;

            testBank.cancelTransactionById(withdrawId);
            System.out.println(account.getBalance());
            assert account.getBalance() == 1_000;

            System.out.println(testBank.getExecutedTransactions());

            testBank.cancelTransactionById(testBank.getExecutedTransactions().get(testBank.getExecutedTransactions().size() - 1).getId());
            System.out.println(account.getBalance());
            assert account.getBalance() == 0;

            System.out.println(testBank.getExecutedTransactions());

            UUID transferId = null;
            try {
                transferId = account.requestTransfer(account.getId(), 1_000);
            } catch (Exception ignored) {}
            assert transferId == null;
            System.out.println(account.getBalance());

            Client client2 = Client.builder()
                    .name("test")
                    .surname("test")
                    .build();

            Bank testBank2 = new FinancialBank(transferSystem, dateTimeProvider) {
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
                    return Duration.ofDays(30);
                }

                @Override
                public double depositPercentPerAnnum() {
                    return 10;
                }
            };

            ClientAccount account2 = testBank2.createAccount(client2, AccountType.DEBIT);

            account.requestTransfer(account2.getId(), 1_000);
            System.out.println(account.getBalance() + "; " + account2.getBalance());

            dateTimeProvider.nextMonth();
            System.out.println(account2.getBalance());

            dateTimeProvider.nextMonth();
            System.out.println(account2.getBalance());

        } finally {
            dateTimeProvider.stop();
        }
    }
}
