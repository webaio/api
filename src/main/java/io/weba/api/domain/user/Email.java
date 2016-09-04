package io.weba.api.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    private static String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    public final String email;

    public Email(String email) {
        if (this.isValidEmailAddress(email)) {
            this.email = email;
        } else {
            throw new RuntimeException("Email is not valid");
        }
    }

    private boolean isValidEmailAddress(String email) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
