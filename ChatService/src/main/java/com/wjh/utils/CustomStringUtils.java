package com.wjh.utils;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class CustomStringUtils {

    public String convertPrice(long actualPrice) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        return numberFormat.format(actualPrice);
    }
}
