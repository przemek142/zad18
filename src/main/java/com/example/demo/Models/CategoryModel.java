package com.example.demo.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CategoryModel {
    private HashMap<Integer, String> category = new HashMap<>();

    public void addCategory(String cat) {
        boolean exist = false;
        int UID = 0;

        for (Integer key : category.keySet()) {
            if (UID < key)
                UID = key;

        }
        for (Integer i : category.keySet()) {
            if (category.get(i).compareTo(cat) == 0)
            exist = true;
        }
        if (!exist)
            category.put(UID + 1, cat);
    }

    public void removeCategory(String key) throws Exception {
        try {
            int keyInt = Integer.parseInt(key);
            category.remove(keyInt);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public HashMap<Integer, String> getCategory() {
        return category;
    }

    public ArrayList<String> getCategoryList() {
        ArrayList<String> output = new ArrayList<>();
        for (Integer integer : category.keySet()) {
            output.add(category.get(integer));
        }
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryModel that = (CategoryModel) o;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "category=" + category +
                '}';
    }
}
