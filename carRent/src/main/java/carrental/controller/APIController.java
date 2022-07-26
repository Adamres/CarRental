package carrental.controller;

import java.sql.Date;
import java.util.List;
import java.time.LocalDate;
import carrental.model.Car;
import carrental.model.Reservation;
import java.time.temporal.ChronoUnit;
import carrental.repository.CarRepository;
import org.springframework.web.bind.annotation.*;
import carrental.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class APIController {
    /*REST API*/

    @Autowired
    CarRepository carRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping(value = "/listcars/{from}&{to}")
    public String ListAvailableCars(@PathVariable(value = "from") String from,
                                    @PathVariable(value = "to") String to) {
        List<Car> availabeCars;
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        availabeCars = carRepository.findAvailableCars(fromDate, toDate);
        return availabeCars.toString();
        /* Bónuszfeladat REST API
         * Példahasználat: /listcars/2022-04-04&2022-04-05
         * Csak egy & jel kell a két dátum közé */
    }

    @RequestMapping(value = "/reserve/{car_id}&{from}&{to}&{name}&{address}&{email}&{phone}")
    public String ReserveCar(@PathVariable(value = "car_id") Long carID,
                             @PathVariable(value = "from") String from, @PathVariable(value = "to") String to,
                             @PathVariable(value = "name") String name, @PathVariable(value = "address") String address,
                             @PathVariable(value = "email") String email, @PathVariable(value = "phone") String phone){
        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);
        long dateDifference = LocalDate.parse(from).until(LocalDate.parse(to), ChronoUnit.DAYS) + 1;
        long totalPrice = dateDifference * (carRepository.findCarByID(carID).getPrice());

        Reservation reservation = new Reservation();
        Long maxId = reservationRepository.maxId();
        if(maxId == null){
            maxId = Long.valueOf(0);
        }
        reservation.setId(maxId + 1);
        reservation.setCar_id(carID);
        reservation.setStart_date(fromDate);
        reservation.setEnd_date(toDate);
        reservation.setName(name);
        reservation.setAddress(address);
        reservation.setEmail(email);
        reservation.setPhone(phone);
        reservation.setTotal_price((int) totalPrice);

        reservationRepository.save(reservation);
        return "Sikeres foglalás!";
        /* Példahasználat: /reserve/1&2022-04-04&2022-05-04&TesztVivien&Tesztutca&test@test.hu&06209999999
        *  A REST API-t használók kiváltságot kapnak arra, hogy lefoglaljanak egy autót arra az időpontra ahová már más lefoglalta.
        *  De ha úgy döntesz nem jár nekik a kiváltság, akkor:
        *  for(Car car : availableCars){
        *      if(reservation.getCar_id == car.getCarId)
        *          return "Az időpont sajnos foglalt!"
        * }                                                 */

        }
}
