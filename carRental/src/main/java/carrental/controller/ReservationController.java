package carrental.controller;

import java.sql.Date;
import java.time.LocalDate;
import carrental.model.Reservation;
import carrental.service.ReservationServiceImpl;
import org.springframework.ui.Model;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Controller;
import carrental.repository.ReservationRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Transactional
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationServiceImpl reservationService;

    @RequestMapping("/reserve")
    public String reserve(@RequestParam("carName") String carName, @RequestParam("price") int price, Model model,
                          @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                          @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                          @RequestParam("carID") Long carID) {

        long dateDifference = from.until(to, ChronoUnit.DAYS) + 1;
        long totalPrice = dateDifference * price;
        //Beszorozzuk a felhasznalo altal megadott ket datum kozotti napok szamat az auto napi araval

        model.addAttribute("carName", carName);
        model.addAttribute("carID", carID);
        model.addAttribute("price", price);
        model.addAttribute("dateDifference", dateDifference);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "reserve";
    }

    @RequestMapping("/reservationProcess")
    public String reservationProcess(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                     @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                     @RequestParam("name") String name, @RequestParam("email") String email,
                                     @RequestParam("address") String address, @RequestParam("phone") String phone,
                                     @RequestParam("totalPrice") int totalPrice, @RequestParam("carID") Long carID){

        Reservation reservation = new Reservation();
        Long maxId = reservationRepository.maxId();
        if(maxId == null){
            maxId = Long.valueOf(0);
        }
        reservation.setId(maxId + 1);
        reservation.setStart_date(Date.valueOf(from));
        reservation.setEnd_date(Date.valueOf(to));
        reservation.setName(name);
        reservation.setEmail(email);
        reservation.setAddress(address);
        reservation.setPhone(phone);
        reservation.setTotal_price(totalPrice);
        reservation.setCar_id(carID);

        reservationService.saveReservation(reservation);

        return "succes";
    }
}