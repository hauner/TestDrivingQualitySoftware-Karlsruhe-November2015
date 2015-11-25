package ca.jbrains.pos.test;

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
        final InMemoryCatalog catalog = new InMemoryCatalog(
                Collections.singletonMap("12345", matchingPrice));

        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    @Test
    public void productFoundAmongOthers() throws Exception {
        final Price matchingPrice = Price.cents(1250);

        final InMemoryCatalog catalog = new InMemoryCatalog(
                new HashMap() {{
                    put("23456", Price.cents(799));
                    put("12345", matchingPrice);
                    put("34567", Price.cents(905));
                }}
        );

        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    private static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
