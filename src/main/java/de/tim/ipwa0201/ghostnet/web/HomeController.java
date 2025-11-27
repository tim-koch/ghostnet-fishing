package de.tim.ipwa0201.ghostnet.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Ghost Net Fishing – Prototyp");
        model.addAttribute("message", "Grundgerüst läuft. Nächster Schritt: Domainmodell und CRUD.");
        return "home";
    }
}
