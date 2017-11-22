package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CommitterTest {
    @Test
    void commit() {
        BigInteger randomKey  = new BigInteger(128,new Random());
        Committer committer = new Committer(new String(randomKey.toByteArray()));

        String message = "MyMessage";
        byte[] hash = committer.commit(message);
        System.out.println();

        byte[] hash2 = committer.commit(message);

        Assertions.assertArrayEquals(hash, hash2);
    }

}