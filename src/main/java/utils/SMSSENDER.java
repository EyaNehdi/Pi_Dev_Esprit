package utils;

import utils.MyDatabase;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.sql.Connection;

public class SMSSENDER{

    Connection cnx;

    public SMSSENDER() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "AC1a191a54118dbb5657d80a0fbcd60c16 ";
    public static final String AUTH_TOKEN = "9f67194df81ef93187a33e17fc059458";

    public static void main(String[] args) {

    }

    public static void sendSMS(String clientPhoneNumber, String s) {

        String accountSid = "AC1a191a54118dbb5657d80a0fbcd60c16";
        String authToken = "9f67194df81ef93187a33e17fc059458";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+14429002104"),
                    s
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}