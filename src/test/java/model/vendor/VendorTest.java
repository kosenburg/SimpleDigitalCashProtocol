package model.vendor;

import model.bank.Bank;
import model.bank.Util;
import model.client.MoneyOrderBuilder;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class VendorTest {
    @Test
    void getIdentityStringChoices() {
        int amount = 5;
        String identity = "0123456789";
        CustomerInfo customerInfo = new CustomerInfo(identity, amount);
        Bank bank = new Bank(Util.generateKeyPair());
        MoneyOrderBuilder moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        ArrayList<Order> moneyOrders = moneyOrderBuilder.generateMoneyOrders();


        Vendor vendor = new Vendor();
        vendor.setOrder(moneyOrders.get(0));

        System.out.println(Arrays.toString(vendor.getIdentityStringChoices()));
        Assertions.assertEquals(vendor.getIdentityStringChoices().length, moneyOrders.get(0).getNumberOfCommitments());
    }

}