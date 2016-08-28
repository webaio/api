package io.weba.api.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    public final String email;

    public Email(String email) {
        if (this.isValidEmailAddress(email)) {
            this.email = email;
        } else {
            throw new RuntimeException("Email is not valid");
        }
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
