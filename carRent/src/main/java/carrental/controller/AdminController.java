package carrental.controller;

import java.util.List;
import java.util.ArrayList;
import carrental.model.Car;
import carrental.model.Reservation;
import carrental.service.ReservationServiceImpl;
import org.springframework.ui.Model;
import carrental.repository.CarRepository;
import org.springframework.stereotype.Controller;
import carrental.repository.ReservationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Controller
@Transactional
public class AdminController {

    @Autowired
    CarRepository carRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationServiceImpl reservationService;

    @RequestMapping("/admin")
    public String adminHome() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                updatedAuthorities));
        /* Itt hozáadjuk az ADMIN rolet a Granted Authorities-hez */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Granted Authorities: " + authentication.getAuthorities());

        return "admin";
    }

    @RequestMapping("/admin/vehicles")
    public String adminListCars(Model model) {
        model.addAttribute("allCars", carRepository.findAll());
        return "adminvehicles";
    }

    @RequestMapping("/changeActive")
    public String changeActive(@RequestParam("carID") Long id, @RequestParam("isActive") String status){
        if(status.equals("deactive")){
            status = "active";
        } else{
            reservationRepository.deletebyCarId(id);
            status = "deactive";
        }
        carRepository.changeActive(id,status);
        return "redirect:/admin/vehicles";
    }

    @RequestMapping("/deleteCar")
    public String deleteCar(@RequestParam("carID") Long id){
        //Előbb az autóhoz tartozó foglalásokat töröljük
        reservationRepository.deletebyCarId(id);
        carRepository.deleteById(id);
        return "redirect:/admin/vehicles";
    }

    @RequestMapping("/admin/modifycar")
    public String modifyCar(@RequestParam("carID") Long id, Model model){
        model.addAttribute("car", carRepository.findCarByID(id));
        return "modifycar";
    }

    @RequestMapping("/modificationProcess")
    public String modificationProcess(@RequestParam("carID") Long id, @RequestParam("name") String name,
                                      @RequestParam("price") int price, @RequestParam("photoURL") String photoURL){
        carRepository.modifyCar(id,name,price,photoURL);
        return "redirect:/admin/vehicles";
    }

    @RequestMapping("/admin/addcar")
    public String addCar(){
        return "addcar";
    }

    @RequestMapping("/addCarProcess")
    public String addCarProcess(@RequestParam("name") String name,
                         @RequestParam("price") int price, @RequestParam("photoURL") String photoURL){

        Car car = new Car();
        Long maxId = carRepository.maxId();
        if(maxId == null){
            maxId = Long.valueOf(0);
        }
        car.setId(maxId + 1);
        /*Ha különböző helyekről szúrúnk az adatbázisba, akkor lehet,
        hogy újra nulláról kezdené az id beszúrást a másik platform, ezért így generálok id-t */
        car.setName(name);
        car.setPrice(price);
        car.setActive("active");
        car.setPhotoURL(photoURL);
        carRepository.save(car);

        return "redirect:/admin/vehicles";
    }

    @RequestMapping("/admin/reservations")
    public String listReservations(Model model){
        model.addAttribute("allReservations", reservationService.getAll());
        return "adminreservations";
    }
}
