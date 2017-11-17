package PlaceHolderForBetterName;


import java.util.UUID;

public class SerialNumberFactory {

    public static String getSerialNumber() {
        return UUID.randomUUID().toString();
    }
}
