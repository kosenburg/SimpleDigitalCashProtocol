package model.client;

import model.datastructures.Order;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.RSABlindingEngine;
import org.bouncycastle.crypto.generators.RSABlindingFactorGenerator;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.PSSSigner;

import java.math.BigInteger;

public class BlindingFactory {

    private RSAKeyParameters pubKey;
    private static final byte[] MESSAGE = "aMessage".getBytes();
    private RSABlindingParameters rsaBlindingParameters;

    public BlindingFactory(RSAKeyParameters bankPubKey) {
        setPubKey(bankPubKey);
        createBlindingParameters();
    }

    private void createBlindingParameters() {
        RSABlindingFactorGenerator blindingFactorGenerator = new RSABlindingFactorGenerator();
        blindingFactorGenerator.init(pubKey);

        BigInteger blindingFactor = blindingFactorGenerator.generateBlindingFactor();

        rsaBlindingParameters = new RSABlindingParameters(pubKey, blindingFactor);
    }

    private void setPubKey(RSAKeyParameters pubKey) {
        this.pubKey = pubKey;
    }


    public byte[] getBlindedMessage() throws CryptoException {
        PSSSigner signer = new PSSSigner(new RSABlindingEngine(), new SHA1Digest(), 20);
        signer.init(true, rsaBlindingParameters);

        signer.update(MESSAGE, 0, MESSAGE.length);

        return signer.generateSignature();
    }


    public Order removeBlind(Order signedOrder) {
        RSABlindingEngine blindingEngine = new RSABlindingEngine();
        blindingEngine.init(false, rsaBlindingParameters);

        byte[] s = blindingEngine.processBlock(signedOrder.getSignature(), 0, signedOrder.getSignature().length);
        signedOrder.setSignature(s);
        signedOrder.setMessage(MESSAGE);
        return signedOrder;
    }
}
