package model.vendor;

import model.datastructures.Order;

public class Vendor {
    private Order signedOrder;

    public Vendor(Order signedOrder) {
        this.signedOrder = signedOrder;
    }

    public byte[] process() {
    }

}
