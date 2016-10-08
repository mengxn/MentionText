package me.codego.view;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mengxn on 16/10/8.
 */

public class MentionText {

    private String regex;
    private static final String DEFAULT_PATTERN="[@|#]\\w+";

    private MentionText(String regex) {
        this.regex = regex;
    }

    public static MentionText from() {
        return new MentionText(DEFAULT_PATTERN);
    }

    public static MentionText from(String regex) {
        return new MentionText(regex);
    }

    public SpannableString apply(String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        SpannableString spannableString = new SpannableString(text);
        while (matcher.find()) {
            spannableString.setSpan(new ForegroundColorSpan(0xffff0000), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }
}
