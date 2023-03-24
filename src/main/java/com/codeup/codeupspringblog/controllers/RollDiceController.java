package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/roll-dice")
public class RollDiceController {

    @GetMapping
    public String rollDiceIndex() {
        return "rolldice/index";
    }

    @GetMapping(path = "/{guess}")
    public String checkGuess(Model model, @PathVariable int guess) {
        int diceRoll = (int) Math.floor(Math.random() * (6) + 1);
        model.addAttribute("diceroll", diceRoll);
        model.addAttribute("guess", guess);
        return "rolldice/guess";
    }

}
