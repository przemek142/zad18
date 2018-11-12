package com.example.demo.Models;

import com.example.demo.Exceptions.WrongCategoryExeption;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductModel {
    private int UID, catUID;
    private String name, price, category;
    BigDecimal priceBig;

    public int getCatUID() {
        return catUID;
    }

    public BigDecimal getPriceBig() {
        return priceBig;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public ProductModel(String name, String price, String category, CategoryModel categoryModel) throws WrongCategoryExeption, NumberFormatException {
        boolean categoryExist = false;
        this.name = name;
        this.price = price;

        String currentCategory;
        for (Integer key : categoryModel.getCategory().keySet()) {
            currentCategory = categoryModel.getCategory().get(key);
            if (currentCategory != null && currentCategory.compareTo(category) == 0) {
                categoryExist = true;
                this.category = category;
                this.catUID = key;
                break;
            }

        }
        try {
            priceBig = new BigDecimal(price.replace(",", "."));
        }catch (NumberFormatException e){
            throw new NumberFormatException();
        }

        if (!categoryExist)
            throw new WrongCategoryExeption();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "UID=" + UID +
                ", catUID=" + catUID +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
