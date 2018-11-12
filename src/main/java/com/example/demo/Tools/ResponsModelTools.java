package com.example.demo.Tools;

import com.example.demo.Models.CategoryModel;
import com.example.demo.Models.ProductModel;
import com.example.demo.Models.ProductsAndCategories;
import com.example.demo.Models.ResponseModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class ResponsModelTools {

    public String messageToHTML(ResponseModel responseModel) {
        if (responseModel.getHeader().get(ResponseModel.Struct.INPUT) == ResponseModel.Types.MESSAGE) {
            return String.format("%s",
                    (String) responseModel.getPayload().get(0).get(0));
        } else
            return "";
    }

    public String listofStringsToTable(ResponseModel responseModel, ProductsAndCategories productsAndCategories) {
        HashMap<Integer, BigDecimal> priceSum = new HashMap<>();
        BigDecimal sum = new BigDecimal("0");

        for (ProductModel product : productsAndCategories.getProducts()) {
            sum=sum.add(product.getPriceBig());
        }

        for (Integer categoriesUID : productsAndCategories.getCategoriesUIDs()) {
            priceSum.put(categoriesUID,BigDecimal.valueOf(0));
            for (ProductModel product : productsAndCategories.getProducts()) {
                if (product.getCatUID() == categoriesUID)
                priceSum.put(categoriesUID,priceSum.get(categoriesUID).add(product.getPriceBig()));
            }
        }


        String table = "<thead><tr><th scope=\"col\">UID</th><th scope=\"col\">Category</th><th scope=\"col\">SUM</th><th scope=\"col\"></th></tr></thead><tbody>";
        table+= "<tr><th scope=\"row\"></th><td>Wszystkie</td></th><td>" + sum +"</td><td><a class=\"fas fa-eye\" href=\"/data/view\"> </a></td></tr>";
        int i = 1;
        if (responseModel.getHeader().get(ResponseModel.Struct.INPUT) == ResponseModel.Types.LISTOFSTRINGS) {
            try {
                for (Integer key : responseModel.getPayload().keySet()) {
                    for (Object o : responseModel.getPayload().get(key)) {
                        HashMap<Integer, String> map = (HashMap<Integer, String>) o;
                        for (Integer integer : map.keySet()) {
                            table += String.format("<tr><th scope=\"row\">%d</th><td>%s</td><td>%s</td><td><a class=\"fas fa-eraser\" href=\"/data/categoryRemove/%d\">&nbsp;&nbsp;</a><a class=\"fas fa-eye\" href=\"/data/categoryShow?cat=%d\"> </a></td></tr>",
                                    integer, map.get(integer),priceSum.get(integer), integer,integer);
                        }
                    }
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
            }
            table += "</tbody>";
            return table;
        } else
            return "";
    }

    public String listofProductsToTable(ResponseModel responseModel) {

        String table = "<thead><tr><th scope=\"col\">UID</th><th scope=\"col\">Name</th><th scope=\"col\">Category</th><th scope=\"col\">Price</th><th scope=\"col\"></th></tr></thead><tbody>";
        int i = 1;
        if (responseModel.getHeader().get(ResponseModel.Struct.INPUT) == ResponseModel.Types.LISTOFPRODUCTS) {
            try {
                for (Integer key : responseModel.getPayload().keySet()) {
                    for (Object o : responseModel.getPayload().get(key)) {
                        ProductModel map = (ProductModel) o;
                            table += String.format("<tr><th scope=\"row\">%d</th><td>%s</td><td>%s</td><td>%s</td><td><a class=\"fas fa-eraser\" href=\"/data/productRemove/%d\"</td></tr>",
                                    map.getUID(),map.getName(),map.getCategory(),map.getPrice() ,map.getUID());
                    }
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
            }
            table += "</tbody>";
            return table;
        } else
            return "";
    }

    public String ListofCategoriesChoice(CategoryModel categoryModel){
        String output="";

        for (String s : categoryModel.getCategoryList()) {
            output+=String.format("<option>%s</option>", s);
        }
        return output;
    }
}
