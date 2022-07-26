package carrental.controller;

import java.time.LocalDate;
import org.springframework.ui.Model;
import carrental.repository.CarRepository;
import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Transactional
public class HomeController {

    @Autowired
    private CarRepository carRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping(value = "/vehicles")
    public String vehicles(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                           @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to, Model model) {
                            //A @DateTimeFormat segítségével lehet hiba nélkül elkérni a dátum típusokat

        model.addAttribute("cars", carRepository.findAvailableCars(from,to));
        model.addAttribute("from",from);
        model.addAttribute("to", to);
        return "vehicles";
    }
}
