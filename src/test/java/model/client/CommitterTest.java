package model.client;

import model.client.Committer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

class CommitterTest {
    @Test
    void commit() {
        BigInteger randomKey  = new BigInteger(128,new Random());
        Committer committer = new Committer(new String(randomKey.toByteArray()));

        String message = "MyMessage";
        byte[] hash = committer.commit(message.getBytes());
        System.out.println();

        byte[] hash2 = committer.commit(message.getBytes());

        Assertions.assertArrayEquals(hash, hash2);
    }

}