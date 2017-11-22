package model;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Committer {
    private Mac mac;
    private SecretKeySpec signingKey;

    public Committer(String key) {
        setUpEncryptor(key);
    }

    private void setUpEncryptor(String key) {
        try {
            mac = Mac.getInstance("HmacSHA1");
            signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("Error while setting up encryptor due to: " + e.getMessage());
        }
    }

    public byte[] commit(String commitString) {
        return mac.doFinal(commitString.getBytes());
    }

}
