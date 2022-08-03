package readersandwriters;

import enums.Category;

import java.util.Objects;

public final class GiftData {
    private final String productName;
    private final Double price;
    private final Category category;
    private Double quantity;

    public GiftData(final String productName, final Double price,
                    final Category category, final Double quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(final Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiftData giftData = (GiftData) o;
        return productName.equals(giftData.productName)
                && price.equals(giftData.price)
                && category == giftData.category
                && quantity.equals(giftData.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, category, quantity);
    }
}
