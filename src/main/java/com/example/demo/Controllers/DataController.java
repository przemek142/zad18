package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/data")
public class DataController {

    @GetMapping("/**")
    @ResponseBody
    public ModelAndView dataView(ModelMap model) {
//        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:/view", model);                              // !!!! STATIC !!!!
    }


    @GetMapping("/add")
    @ResponseBody
    public String addView() {

        return "add";
    }

    @GetMapping("/view")
    @ResponseBody
    public String viewView() {
        return "view";
    }

    @GetMapping("/remove")
    @ResponseBody
    public String removeView() {

        return "remove";
    }


}