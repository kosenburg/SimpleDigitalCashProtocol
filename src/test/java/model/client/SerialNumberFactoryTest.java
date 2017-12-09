package model.client;


import model.client.SerialNumberFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SerialNumberFactoryTest {
    public void getSerialNumber() throws Exception {
        Pattern p = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        String uuid = SerialNumberFactory.getSerialNumber();
        System.out.println(SerialNumberFactory.getSerialNumber());

        Matcher m = p.matcher(uuid);
        if (m.find()) {
            System.out.println("match");
        } else {
            //TODO Junit 5 assert
        }


    }

}