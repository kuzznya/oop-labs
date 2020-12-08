package itmo.oop.lab3.model.client;

import itmo.oop.lab3.model.Bank;

@FunctionalInterface
public interface ClientAccountFactory {
    ClientAccount createAccount(Bank.BankAccount bankAccount);
}
