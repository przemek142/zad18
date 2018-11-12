package com.example.demo.Controllers;

import com.example.demo.Models.ProductModel;
import com.example.demo.Models.ProductsAndCategories;
import com.example.demo.Tools.ResponsModelTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/data")
public class DataController {

    @Autowired
    ProductsAndCategories productsAndCategories;
    @Autowired
    ResponsModelTools responsModelTools;

    @RequestMapping(
            value = {"/view/*"},
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView dataView(ModelMap model) {
//        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:/data/view", model);                              // !!!! STATIC !!!!
    }

    @GetMapping("/addCategory")
//    @ResponseBody
    public ModelAndView addCategory(@RequestParam(value = "category") String category, ModelMap model) {
        model.addAttribute("mess", responsModelTools.messageToHTML(productsAndCategories.addCategory(category)));
        model.addAttribute("title", "Add category");
        return new ModelAndView("redirect:/data/view", model);                              // !!!! STATIC !!!!
    }

    @GetMapping("/addProduct")
//    @ResponseBody
    public ModelAndView addProduct(@RequestParam(value = "category") String category,
                                   @RequestParam(value = "name") String name,
                                   @RequestParam(value = "price") String price,
                                   ModelMap model) {
        model.addAttribute("mess", responsModelTools.messageToHTML(productsAndCategories.addProduct(name, price, category)));
        model.addAttribute("title", "Add Product");
        return new ModelAndView("redirect:/data/view", model);                              // !!!! STATIC !!!!
    }

    @RequestMapping(value = "/view/{uid:[i][d][\\d][\\d][\\d][\\d]}", method = RequestMethod.GET)
    @ResponseBody
    public String view(
            @PathVariable String uid) {
        return "view uid=" + uid;
    }

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
//    @ResponseBody
    public String viewAll(@RequestParam(value = "mess", required = false) String mess,
                          @RequestParam(value = "cat", required = false) String cat,
                          Model model) {

        if (!model.containsAttribute("mess") && mess != null)
            model.addAttribute("mess", mess);
        else if (!model.containsAttribute("mess") && mess == null)
            model.addAttribute("mess", "");

        model.addAttribute("title", "View all");
        model.addAttribute("categoriesTable", responsModelTools.listofStringsToTable(productsAndCategories.getCategories(), productsAndCategories));
        model.addAttribute("productsTable", responsModelTools.listofProductsToTable(productsAndCategories.getProducts(cat)));
        model.addAttribute("listOfCatChoice", responsModelTools.ListofCategoriesChoice(productsAndCategories.getCategoryModel()));
        return "template.html";
    }

    @GetMapping("/categoryShow")
//    @ResponseBody
    public String showCategory(@RequestParam(value = "cat") String category, Model model) {
//        model.addAttribute("mess", responsModelTools.messageToHTML(productsAndCategories.addCategory(category)));
        model.addAttribute("cat", category);
        return "redirect:/data/view?cat=" + category;                              // !!!! STATIC !!!!
    }

    @GetMapping("/remove/*")
    @ResponseBody
    public String rootView() {
        return "id niepoprawny";
    }

    @RequestMapping(value = "/categoryRemove/{uid:[\\d*]}", method = RequestMethod.GET)
//    @ResponseBody
    public ModelAndView removeCategory(@PathVariable String uid, ModelMap model) {

        ArrayList<ProductModel> listOfProducts = new ArrayList<>();
        try {
            for (ProductModel product : productsAndCategories.getProducts()) {
                if (product.getCatUID() == Integer.parseInt(uid)) {
                   listOfProducts.add(product);
                }
            }
        }catch (Exception e){
            System.out.println("error " + e);
        }

        for (ProductModel singleUID : listOfProducts) {
            productsAndCategories.getProducts().remove(singleUID);
        }

            model.addAttribute("mess", responsModelTools.messageToHTML(productsAndCategories.removeCategory(uid)));

            return new ModelAndView("redirect:/data/view", model);                              // !!!! STATIC !!!!    }
        }

        @RequestMapping(value = "/productRemove/{uid:[\\d*]}", method = RequestMethod.GET)
//    @ResponseBody
        public ModelAndView removeProduct (@PathVariable String uid, ModelMap model){
            model.addAttribute("mess", responsModelTools.messageToHTML(productsAndCategories.removeProduct(uid)));

            return new ModelAndView("redirect:/data/view", model);                              // !!!! STATIC !!!!    }
        }

        @GetMapping("/example")
//    @ResponseBody
        public ModelAndView createExample (ModelMap model){

            productsAndCategories.addCategory("Art. spożywcze");
            productsAndCategories.addCategory("Art. gosp. domowego");
            productsAndCategories.addCategory("Inne");

            productsAndCategories.addProduct("mleko", "3.21", "Art. spożywcze");
            productsAndCategories.addProduct("chleb", "1.31", "Art. spożywcze");
            productsAndCategories.addProduct("pralka", "31.31", "Art. gosp. domowego");
            productsAndCategories.addProduct("czajnik", "31.31", "Art. gosp. domowego");
            productsAndCategories.addProduct("samochód", "312.11", "Inne");
            productsAndCategories.addProduct("rower", "352.51", "Inne");

            model.addAttribute("mess", "Examples created");
            return new ModelAndView("redirect:/data/view", model);                              // !!!! STATIC !!!!
        }
    }