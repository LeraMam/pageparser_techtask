package org.techtask.parse;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PageParser {
    private final String immutableUrlPart;

    public List<Element> getItemNameList() throws IOException {
        String nextURL = immutableUrlPart;
        List<Element> itemList = new ArrayList<>();
        while (nextURL != null) {
            Document doc = Jsoup.connect(nextURL).get();
            List<Element> items = doc.select("div.product").select("div.description").select("div.info-level4").select("a");
            itemList.addAll(items);
            nextURL = getNextPageUrl(nextURL);
        }
        return itemList;
    }

    public List<Element> getItemPriceList() throws IOException {
        String nextURL = immutableUrlPart;
        List<Element> priceList = new ArrayList<>();
        while (nextURL != null) {
            Document doc = Jsoup.connect(nextURL).get();
            List<Element> pages = doc.select("div.price");
            priceList.addAll(pages);
            nextURL = getNextPageUrl(nextURL);
        }
        return priceList;
    }

    public List<String> getItemHrefList() throws IOException {
        String nextURL = immutableUrlPart;
        List<String> hrefList = new ArrayList<>();
        while (nextURL != null) {
            Document doc = Jsoup.connect(nextURL).get();
            List<Element> items = doc.select("div.product").select("div.description").select("div.info-level4").select("a");
            for(Element item : items) {
                hrefList.add(item.attr("href"));
            }
            nextURL = getNextPageUrl(nextURL);
        }
        return hrefList;
    }

    /*private void parsePage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        List<Element> items = doc.select("div.product").select("div.description").select("div.info-level4").select("a");
        List<Element> prices = doc.select("div.price");
        int itemCount = items.size();
        for (int i = 0; i < itemCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(items.get(i).text()).append(" ").append(items.get(i).attr("href")).append("  ").append(prices.get(i).text());
            System.out.println(sb.toString());
        }
    }*/

    private String getNextPageUrl(String currentUrl) throws IOException {
        Document doc = Jsoup.connect(currentUrl).get();
        Element nextPageLink = doc.selectFirst("div.pagination > ul.pagination > li > a[rel=next]");
        if ((nextPageLink != null) && (nextPageLink.text().matches(".*\\d.*"))) {
            return immutableUrlPart + nextPageLink.attr("href");
        }
        return null;
    }
}