package model;

public class IdentityStringFactory {

    public static String getIdentityString(String[] information) {
        String idString = "";

        for (String info: information) {
            idString += info;
        }

        return idString;
    }
}
