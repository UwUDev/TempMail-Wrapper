package me.uwu.temp.mail.wrapper;

import java.util.Arrays;

public class Example {
    public static void main(String[] args) throws Exception {
        TemporaryMailBox mailBox = new TemporaryMailBox("", "sample@mailkept.com");
        System.out.println(mailBox.getMail());
        if (mailBox.waitNewMail(45000))
            System.out.println(Arrays.toString(mailBox.getEmails()));
        else System.out.println("Timed out... No emails received");
    }
}
