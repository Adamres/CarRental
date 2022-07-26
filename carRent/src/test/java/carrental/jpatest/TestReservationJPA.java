package carrental.jpatest;

import java.sql.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import carrental.model.Reservation;
import static org.junit.Assert.assertEquals;
import carrental.repository.ReservationRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReservationJPA {

    @Autowired
    ReservationRepository reservationRepository;
    Reservation testReservation;
    Reservation retrievedReservation;

    @Test
    public void addAndDeleteReservationTest() throws Exception {

        testReservation = new Reservation();
        Long maxId = reservationRepository.maxId();
        if(maxId == null){
            maxId = Long.valueOf(0);
        }
        testReservation.setId(maxId + 1);
        testReservation.setCar_id((long) 1);                        /*Valid Car ID kell*/
        testReservation.setStart_date(Date.valueOf("2024-04-06"));
        testReservation.setEnd_date(Date.valueOf("2024-05-06"));
        testReservation.setName("Teszt Vivien");
        testReservation.setAddress("Szeged, Teszt utca 12");
        testReservation.setEmail("tesztemail@teszt.com");
        testReservation.setPhone("209999999");
        testReservation.setTotal_price(40000);

        reservationRepository.save(testReservation);
        retrievedReservation = reservationRepository.findByName(testReservation.getName());
        assertEquals(testReservation.getName(), retrievedReservation.getName());
        assertEquals(testReservation.getTotal_price(), retrievedReservation.getTotal_price());

        reservationRepository.deletebyCarId(testReservation.getCar_id());
        assertEquals(null, reservationRepository.findByName(testReservation.getName()));
    }
}

