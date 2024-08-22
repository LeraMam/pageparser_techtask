package org.techtask.data;

import lombok.Getter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Getter
public class Item {
    private final String name;
    private final double price;
    private final String href;

    public Item(String name, String price, String href) {
        this.name = name;
        this.price = priceParseFormatting(price);
        this.href = href;
    }

    public double priceParseFormatting(String price) {
        try {
            String cleanedPrice = price.replaceAll("[^\\d.,-]", "");
            cleanedPrice = cleanedPrice.replace(',', '.');
            return NumberFormat.getInstance(Locale.ENGLISH).parse(cleanedPrice).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
