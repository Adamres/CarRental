package carrental.service;

import java.util.List;
import carrental.model.Reservation;

public interface ReservationService {
    public List<Reservation> getAll();
    public void saveReservation(Reservation reservation);
}
