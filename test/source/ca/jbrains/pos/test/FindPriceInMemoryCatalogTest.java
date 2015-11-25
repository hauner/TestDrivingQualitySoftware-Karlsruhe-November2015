package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Catalog;
import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(1250);
        final Catalog catalog = createCatalogWith("12345", matchingPrice);
        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    private Catalog createCatalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(new HashMap() {{
            put("not " + barcode, Price.cents(237462));
            put(barcode, matchingPrice);
            put("definitely not " + barcode, Price.cents(236431));
        }});
    }

    @Test
    public void productNotFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(new HashMap() {{
            put("not 12345", Price.cents(100));
            put("definitely not 12345", Price.cents(200));
            put("certainly not 12345", Price.cents(300));
        }});

        Assert.assertEquals(null, catalog.findPrice("12345"));
    }

    private static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
