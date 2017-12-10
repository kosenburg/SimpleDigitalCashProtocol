package model.vendor;

import model.datastructures.Order;

import java.util.Random;

public class Vendor {
    private Order signedOrder;
    private Random random;

    public Vendor() {
        setRandomGenerator();
    }

    public void setOrder(Order signedOrder) {
        this.signedOrder = signedOrder;
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
        int[] identityChoices = new int[signedOrder.getNumberOfCommitments()];
        for (int i = 0; i < signedOrder.getNumberOfCommitments(); i++) {
            identityChoices[i] = random.nextInt(2);
        }

        return identityChoices;
    }
}
