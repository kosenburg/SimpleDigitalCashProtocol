package model.vendor;

import model.datastructures.Order;

import java.util.Random;

public class Vendor {
    private Order signedOrder;
    private Random random;

    public Vendor(Order signedOrder) {
        this.signedOrder = signedOrder;
        setRandomGenerator();
    }

    //TODO remove constructor
    public Vendor() {

    }

    private void setRandomGenerator() {
        random = new Random();
    }

    public byte[] process() {
    return null;
    }

    public int[] getIdentityStringChoices() {
        return generateRandomArray();
    }

    private int[] generateRandomArray() {
        int[] identityChoices = new int[signedOrder.getCommitment().size()];
        for (int i = 0; i < signedOrder.getCommitment().size(); i++) {
            identityChoices[i] = random.nextInt(2);
        }
        return identityChoices;
    }
}
