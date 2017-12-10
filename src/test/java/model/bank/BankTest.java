package model.bank;

import model.client.MoneyOrderBuilder;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import model.datastructures.Pair;
import model.vendor.Vendor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    @Test
    void deductFromCustomerAccount() {
        Bank bank = new Bank(Util.generateKeyPair());
        int firstAmount = bank.getAccountBalance("Customer");
        bank.deductFromCustomerAccount(100);
        int secondAmount = bank.getAccountBalance("Customer");
        Assertions.assertTrue(secondAmount < firstAmount);

    }

    @Test
    void addToVendorAccount() {
        Bank bank = new Bank(Util.generateKeyPair());
        int firstAmount = bank.getAccountBalance("Vendor");
        bank.addToVendorAccount(100);
        int secondAmount = bank.getAccountBalance("Vendor");
        Assertions.assertTrue(firstAmount < secondAmount);
    }

    @Test
    void displayAccountValues() {
        Bank bank = new Bank(Util.generateKeyPair());
        bank.displayAccountValues();
    }

    @Test
    void verify() {
        int amount = 5;
        CustomerInfo customerInfo = new CustomerInfo("0123456789", amount);
        Bank bank = new Bank(Util.generateKeyPair());
        MoneyOrderBuilder moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        ArrayList<Order> moneyOrders = moneyOrderBuilder.generateMoneyOrders();
        Order signedOrder = bank.sign(moneyOrders);
        signedOrder = moneyOrderBuilder.removeBlind(signedOrder);

        Vendor vendor = new Vendor();
        vendor.setOrder(signedOrder);

        bank.displayAccountValues();
        ArrayList<Pair> pairs = moneyOrderBuilder.getRequestedIdentityStrings(vendor.getIdentityStringChoices());
        System.out.println("Verification results: " + bank.verify(signedOrder, pairs));
        bank.displayAccountValues();
    }

}