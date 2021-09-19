package me.uwu.temp.mail.wrapper;

import com.google.gson.Gson;
import lombok.Getter;
import me.uwu.temp.mail.wrapper.objs.Mail;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Getter
public class TemporaryMailBox {
    private final String apiKey;
    private final String mail;
    private final String md5;

    public TemporaryMailBox(String apiKey) throws Exception {
        this.apiKey = apiKey;
        this.mail = randomMail();
        this.md5 = toMD5(this.mail);
    }

    public TemporaryMailBox(String apiKey, String forcedMail) throws Exception {
        this.apiKey = apiKey;
        this.mail = forcedMail;
        this.md5 = toMD5(this.mail);
    }

    public boolean waitNewMail(){
        return waitNewMail(Integer.MAX_VALUE);
    }

    @SuppressWarnings("BusyWait")
    public boolean waitNewMail(int timeout){
        int currentMails = 0;
        long timeoutDelay = System.currentTimeMillis() + timeout;
        boolean received = false;

        try { currentMails = getEmails().length;
        } catch (Exception ignored){}

        while (!received && System.currentTimeMillis() < timeoutDelay) {
            try {
                if (getEmails().length > currentMails)
                    received = true;
            } catch (Exception e){
                e.printStackTrace();
            }

            try { Thread.sleep(500);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return received;
    }

    public Mail[] getEmails() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://privatix-temp-mail-v1.p.rapidapi.com/request/mail/id/" + md5 + "/format/json/")
                .method("GET", null)
                .addHeader("X-RapidAPI-Key", apiKey)
                .build();
        Mail[] emails = new Mail[0];
        try {
            emails = new Gson().fromJson(Objects.requireNonNull(client.newCall(request).execute().body()).string(), Mail[].class);
        } catch (Exception ignored) {}
        return emails;
    }

    public String randomMail() throws IOException {
        String[] extensions = availableMails();
        String extension = extensions[new Random().nextInt(extensions.length)];
        return UUID.randomUUID().toString().replace("-", "").toLowerCase() + extension;
    }

    public String toMD5(String email) throws NoSuchAlgorithmException {
       MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(email.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }

    public String[] availableMails() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://privatix-temp-mail-v1.p.rapidapi.com/request/domains/")
                .method("GET", null)
                .addHeader("X-RapidAPI-Key", apiKey)
                .build();
        return new Gson().fromJson(Objects.requireNonNull(client.newCall(request).execute().body()).string(), String[].class);
    }
}
