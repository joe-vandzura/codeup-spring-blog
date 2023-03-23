package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HelloController {

    @GetMapping(path = "/hello/{firstName}/{lastName}")
    @ResponseBody
    public String hello(@PathVariable String firstName, @PathVariable String lastName) {
        return "Hello from " + firstName+ " " + lastName + "!";
    }

    @GetMapping(path = "/dice")
    @ResponseBody
    public String rollDice() {
        int diceRoll = (int) (Math.random() * 6) + 1;
        return "You got a " + diceRoll + "!";
    }
}
