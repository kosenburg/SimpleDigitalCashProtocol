package model.bank;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Util {

    public static AsymmetricCipherKeyPair generateKeyPair() {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        generator.init(new RSAKeyGenerationParameters(
                new BigInteger("10001", 16), new SecureRandom(), 2048,
                80));
        return generator.generateKeyPair();
    }
}
