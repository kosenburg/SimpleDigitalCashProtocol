package model.client;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.bank.Bank;
import model.bank.Util;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import model.datastructures.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MoneyOrderBuilderTest {

    @Test
    void removeBlind() {
        int amount = 5;
        CustomerInfo customerInfo = new CustomerInfo("0123456789", amount);
        Bank bank = new Bank(Util.generateKeyPair());
        MoneyOrderBuilder moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        ArrayList<Order> moneyOrders = moneyOrderBuilder.generateMoneyOrders();

        byte[] blindedMessage = moneyOrders.get(0).getMessage();

        Order signedOrder = bank.sign(moneyOrders);

        signedOrder = moneyOrderBuilder.removeBlind(signedOrder);
        byte[] deblindedMessage = signedOrder.getMessage();

        Assertions.assertFalse(Arrays.equals(blindedMessage, deblindedMessage));
    }

    @Test
    void generateMoneyOrders() {
        int amount = 5;
        CustomerInfo customerInfo = new CustomerInfo("0123456789", amount);
        Bank bank = new Bank(Util.generateKeyPair());
        MoneyOrderBuilder moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        ArrayList<Order> moneyOrders = moneyOrderBuilder.generateMoneyOrders();

        Assertions.assertEquals(amount, moneyOrders.size());
    }

    @Test
    void getRequestedIdentityStrings() {
        int amount = 5;
        String identity = "0123456789";
        CustomerInfo customerInfo = new CustomerInfo(identity, amount);
        Bank bank = new Bank(Util.generateKeyPair());
        MoneyOrderBuilder moneyOrderBuilder = new MoneyOrderBuilder(customerInfo, bank.getPublicKey());
        ArrayList<Order> moneyOrders = moneyOrderBuilder.generateMoneyOrders();


        ArrayList<Pair> pairs = moneyOrderBuilder.getRequestedIdentityStrings(generateAllLeftString(moneyOrders));

        StringBuilder identityRebuilt = new StringBuilder();
        for (Pair pair: pairs) {
            identityRebuilt.append(new String(pair.getLeft()));
        }

        Assertions.assertEquals(identityRebuilt.toString(), "01347");
    }

    int[] generateAllLeftString(ArrayList<Order> moneyOrders) {
        int[] array = new int[moneyOrders.get(0).getNumberOfCommitments()];
        for (int i = 0; i < moneyOrders.get(0).getNumberOfCommitments(); i++) {
            array[i] = 1;
        }
        return array;
    }


    int[] generateRandomString(ArrayList<Order> moneyOrders) {
        Random random = new Random();
        int[] identityChoices = new int[moneyOrders.get(0).getNumberOfCommitments()];
        for (int i = 0; i < moneyOrders.get(0).getNumberOfCommitments(); i++) {
            identityChoices[i] = random.nextInt(2);
        }

        return identityChoices;
    }

}