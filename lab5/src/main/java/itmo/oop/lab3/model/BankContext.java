package itmo.oop.lab3.model;

public interface BankContext {
    TransferSystem getTransferSystem();
    void withdraw(Bank.BankAccount account, double amount);
    void replenish(Bank.BankAccount account, double amount);
}
