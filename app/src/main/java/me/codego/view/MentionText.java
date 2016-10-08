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

    public static SpannableString apply(String text) {
        Pattern pattern = Pattern.compile("@[\\w]+");
        Matcher matcher = pattern.matcher(text);
        SpannableString spannableString = new SpannableString(text);
        while (matcher.find()) {
            spannableString.setSpan(new ForegroundColorSpan(0xffff0000), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }
}
