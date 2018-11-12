package com.example.demo.Models;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ProductsAndCategories {
    private ResponseModel responseModel;
    private ArrayList<ProductModel> products = new ArrayList<>();

    private CategoryModel categories = new CategoryModel();

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public ResponseModel addCategory(String cat) {
        categories.addCategory(cat);
        ArrayList<Object> msg = new ArrayList<>();
        msg.add("Category Added");
        HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
        payload.put(0, msg);
        responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
        return responseModel;
    }

    public ResponseModel getCategories() {
        ArrayList<Object> msg = new ArrayList<>();
        for (Integer key : categories.getCategory().keySet()) {
            HashMap<Integer, String> singleObject = new HashMap<>();
            singleObject.put(key, categories.getCategory().get(key));
            msg.add(singleObject);
        }
        HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
        payload.put(0, msg);
        responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.LISTOFSTRINGS, ResponseModel.Types.LISTOFSTRINGS, payload);
        return responseModel;
    }

    public ArrayList<Integer> getCategoriesUIDs() {
        ArrayList<Integer> output = new ArrayList<>();
        for (Integer key : categories.getCategory().keySet()) {
            output.add(key);
        }
        return output;
    }

    public ResponseModel removeCategory(String input) {
        boolean removedOK = true;

        try {
            categories.removeCategory(input);
        } catch (Exception e) {
            removedOK = false;

        }

        if (removedOK) {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Category Removed");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
            return responseModel;
        } else {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Error removing category");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.ERROR, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
            return responseModel;
        }

    }

    public ResponseModel addProduct(String name, String price, String category) {
        int lastUID = 0;
        for (ProductModel product : products) {
            if (lastUID < product.getUID())
                lastUID = product.getUID();
        }
        try {
            ProductModel newProduct = new ProductModel(name, price, category, categories);
            newProduct.setUID(lastUID + 1);
            products.add(newProduct);
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Added product");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);

        } catch (Exception e) {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Category doesn't exist or wrong number format");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.ERROR, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
        }
        return responseModel;
    }

    public ResponseModel getProducts(String category) {
        ArrayList<Object> msg = new ArrayList<>();
        for (ProductModel product : products) {
            if (category != null && category.compareTo("") != 0) {
                int catInt = Integer.parseInt(category);
                if (product.getCatUID() == catInt)
                    msg.add(product);
            } else
                msg.add(product);
        }
        HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
        payload.put(0, msg);
        responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.LISTOFPRODUCTS, ResponseModel.Types.LISTOFPRODUCTS, payload);
        return responseModel;
    }

    public ResponseModel getProduct(int UID) {
        boolean found = false;
        for (ProductModel product : products) {
            if (product.getUID() == UID) {
                ArrayList<Object> msg = new ArrayList<>();
                msg.add(product);
                HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
                payload.put(0, msg);
                responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.PRODUCT, ResponseModel.Types.PRODUCT, payload);
                found = true;
                break;
            }
        }

        if (!found) {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Product not found");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
        }

        return responseModel;
    }

    public ResponseModel removeProduct(String uid) {
        boolean removedOK = true;
        int productModelToRemoveUID;

        try {
            productModelToRemoveUID = Integer.parseInt(uid);
            for (ProductModel product : products) {
                if (productModelToRemoveUID == product.getUID()) {
                    products.remove(product);
                }
            }

        } catch (Exception e) {
            removedOK = false;

        }

        if (removedOK) {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Product Removed");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.SUCCESS, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
            return responseModel;
        } else {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("Error removing product");
            HashMap<Integer, ArrayList<Object>> payload = new HashMap<>();
            payload.put(0, msg);
            responseModel = new ResponseModel(ResponseModel.Types.ERROR, ResponseModel.Types.MESSAGE, ResponseModel.Types.MESSAGE, payload);
            return responseModel;
        }
    }

    public CategoryModel getCategoryModel() {
        return categories;
    }
}
