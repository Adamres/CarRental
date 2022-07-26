package carrental.service;

import java.util.List;
import carrental.model.Reservation;
import org.springframework.stereotype.Service;
import carrental.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReservationServiceImpl implements ReservationService{
    /* Foglalásokat kiszolgáló service */

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> getAll(){
        return reservationRepository.findAll();
    }

    public void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }
}
