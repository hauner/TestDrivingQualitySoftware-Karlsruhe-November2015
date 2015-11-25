package ca.jbrains.pos.test;

import ca.jbrains.pos.test.SellOneItemControllerTest.Catalog;
import ca.jbrains.pos.test.SellOneItemControllerTest.Price;
import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(1250);
        final Catalog catalog = createCatalogWith("12345", matchingPrice);
        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    protected abstract Catalog createCatalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        final String barcodeToAvoid = "::anything I want::";
        Assert.assertEquals(null, createCatalogWithout(barcodeToAvoid).findPrice(barcodeToAvoid));
    }

    protected abstract Catalog createCatalogWithout(String barcodeToAvoid);
}
