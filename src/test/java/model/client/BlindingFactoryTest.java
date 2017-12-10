package model.client;

import model.bank.Bank;
import model.bank.Util;
import model.datastructures.CustomerInfo;
import model.datastructures.Order;
import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BlindingFactoryTest {
    @Test
    void getBlindedMessage() throws CryptoException {
        Bank bank = new Bank(Util.generateKeyPair());
        BlindingFactory blindingFactory = new BlindingFactory(bank.getPublicKey());
        System.out.println(Arrays.toString(blindingFactory.getBlindedMessage()));
    }
}