package me.codego.view;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

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

    public void apply(TextView textView) {
        if (textView == null) {
            return;
        }
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(editable.toString());
                while (matcher.find()) {
                    editable.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        });
    }
}
