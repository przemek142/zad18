package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RootController {

    @GetMapping("/**")
    @ResponseBody
    public ModelAndView rootView(ModelMap model) {
        return new ModelAndView( "redirect:/index", model);                                       // !!!! STATIC !!!!
    }

    @GetMapping("/data")
    @ResponseBody
    public ModelAndView dataView(ModelMap model) {
        return new ModelAndView( "redirect:/data/view", model);                                       // !!!! STATIC !!!!
    }

    @GetMapping("/index")
    @ResponseBody
    public String addView() {

        return "index";
    }
}
