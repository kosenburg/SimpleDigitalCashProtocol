package model;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.RSABlindingEngine;
import org.bouncycastle.crypto.generators.RSABlindingFactorGenerator;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.PSSSigner;

import java.math.BigInteger;

public class BlindingFactory {


    private final RSABlindingParameters blindingParams;

    public BlindingFactory(RSAKeyParameters pub) {

        RSABlindingFactorGenerator blindingFactorGenerator
                = new RSABlindingFactorGenerator();
        blindingFactorGenerator.init(pub);

        BigInteger blindingFactor
                = blindingFactorGenerator.generateBlindingFactor();

        blindingParams = new RSABlindingParameters(pub, blindingFactor);
    }

    public byte[] getBlindedMessage(byte[] message) throws CryptoException {
        // "Blind" the coin and generate a coin request to be signed by the
        // bank.
        PSSSigner signer = new PSSSigner(new RSABlindingEngine(),
                new SHA1Digest(), 20);
        signer.init(true, blindingParams);

        signer.update(message, 0, message.length);

        byte[] sig = signer.generateSignature();

        return sig;
    }

}
