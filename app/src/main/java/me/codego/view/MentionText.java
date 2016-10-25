package me.codego.view;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by mengxn on 16/10/8.
 */

public class MentionText {

    private String regex;
    private int color;

    private List<Range> rangeList = new ArrayList<>();

    private static final String DEFAULT_PATTERN = "@|#";
    private static final int DEFAULT_COLOR = 0xffff0000;

    private MentionText(String regex, int color) {
        this.regex = String.format(Locale.getDefault(), "[%s]\\w+", regex);
        this.color = color;
    }

    public static MentionText from() {
        return from(DEFAULT_PATTERN, DEFAULT_COLOR);
    }

    public static MentionText from(String regex) {
        return from(regex, DEFAULT_COLOR);
    }

    public static MentionText FROM(int color) {
        return from(DEFAULT_PATTERN, color);
    }

    public static MentionText from(String regex, int color) {
        return new MentionText(regex, color);
    }

    public void apply(final TextView textView) {
        if (textView == null) {
            return;
        }
        textView.addTextChangedListener(new TextWatcher() {

            private Range readyDeleteRange;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (textView instanceof EditText) {
                    if (count == 1 && after == 0 && s.charAt(start) == ' ') {
                        for (Range range : rangeList) {
                            if (range.end == start) {
                                readyDeleteRange = range;
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (readyDeleteRange != null) {
                    int start = readyDeleteRange.start;
                    int end = readyDeleteRange.end;
                    readyDeleteRange = null;
                    editable.delete(start, end);
                }
                ForegroundColorSpan[] oldSpans = editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
                for (ForegroundColorSpan oldSpan : oldSpans) {
                    editable.removeSpan(oldSpan);
                }
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(editable.toString());
                rangeList.clear();
                while (matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    rangeList.add(new Range(start, end));
                    editable.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        });
    }
}
