package itmo.oop.lab3.model;

import itmo.oop.lab3.model.transaction.ReplenishmentTransaction;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class TransferSystemImpl implements TransferSystem {

    private final Map<UUID, Bank> banks = new LinkedHashMap<>();

    @Override
    public void registerBank(Bank bank) {
        banks.put(bank.getId(), bank);
    }

    @Override
    public ReplenishmentTransaction createReplenishmentTransaction(AccountIdentifier receiverId, double amount) {
        Bank.BankAccount receiver = banks
                .get(receiverId.getBankId())
                .findAccountById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Account with id " + receiverId + " not found"));
        return new ReplenishmentTransaction(receiver, amount);
    }
}
