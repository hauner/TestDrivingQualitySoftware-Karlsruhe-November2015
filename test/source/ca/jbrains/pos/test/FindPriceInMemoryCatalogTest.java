package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Catalog;
import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {

    @Override
    protected Catalog createCatalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(new HashMap() {{
            put("not " + barcode, Price.cents(237462));
            put(barcode, matchingPrice);
            put("definitely not " + barcode, Price.cents(236431));
        }});
    }

    @Override
    protected Catalog createCatalogWithout(final String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap() {{
            put("not " + barcodeToAvoid, Price.cents(100));
            put("definitely not " + barcodeToAvoid, Price.cents(200));
            put("certainly not " + barcodeToAvoid, Price.cents(300));
        }});
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
