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
    private int color;

    private static final String DEFAULT_PATTERN = "[@|#]\\w+";
    private static final int DEFAULT_COLOR = 0xffff0000;

    private MentionText(String regex, int color) {
        this.regex = regex;
        this.color = color;
    }

    public static MentionText from() {
        return from(DEFAULT_PATTERN, DEFAULT_COLOR);
    }

    public static MentionText from(String regex) {
        return from(regex, DEFAULT_COLOR);
    }

    public static MentionText from(int color) {
        return from(DEFAULT_PATTERN, color);
    }

    public static MentionText from(String regex, int color) {
        return new MentionText(regex, color);
    }

    public SpannableString apply(String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        SpannableString spannableString = new SpannableString(text);
        while (matcher.find()) {
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
}
