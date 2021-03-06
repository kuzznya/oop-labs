package itmo.oop.lab3.model;

import itmo.oop.lab3.model.transaction.ReplenishmentTransaction;

public interface TransferSystem {
    void registerBank(Bank bank);
    ReplenishmentTransaction createReplenishmentTransaction(AccountIdentifier receiverId, double amount);
}
