package org.techtask.parse;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.techtask.data.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class PageParser {
    private final String immutableUrlPart;

    public List<Item> getItemList() throws IOException {
        String nextURL = immutableUrlPart;
        List<Item> itemList = new ArrayList<>();
        boolean hasNextPage = true;
        while (hasNextPage) {
            itemList.addAll(parseItemList(nextURL));
            Optional<String> nextPageUrl = getNextPageUrl(nextURL);
            if (nextPageUrl.isPresent()) {
                nextURL = nextPageUrl.get();
            } else {
                hasNextPage = false;
            }
        }
        return itemList;
    }

    public List<Item> parseItemList(String nextURL) throws IOException {
        List<Item> itemList = new ArrayList<>();
        Document doc = Jsoup.connect(Objects.requireNonNull(nextURL)).get();
        List<Element> names = doc.select("div.product").select("div.description").select("div.info-level4").select("a");
        List<Element> prices = doc.select("div.price");
        List<Element> hrefs = doc.select("div.product").select("div.description").select("div.info-level4").select("a");
        for (int i = 0; i < names.size(); i++) {
            Item item = new Item(names.get(i).text(), prices.get(i).text(), hrefs.get(i).attr("href"));
            itemList.add(item);
        }
        return itemList;
    }

    private Optional<String> getNextPageUrl(String currentUrl) throws IOException {
        Document doc = Jsoup.connect(currentUrl).get();
        Element nextPageLink = doc.selectFirst("div.pagination > ul.pagination > li > a[rel=next]");
        if (nextPageLink != null) {
            return Optional.of(immutableUrlPart + nextPageLink.attr("href"));
        }
        return Optional.empty();
    }
}