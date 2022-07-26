package carrental.repository;
import carrental.model.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from reservations where car_id = :carID", nativeQuery = true)
    void deletebyCarId(@Param("carID") Long carID);
    //Ha törlünk egy autót, akkor törlődik az összes hozzá tartozó foglalás is

    @Transactional
    @Query(value = "select * from reservations where name = :personName", nativeQuery = true)
    Reservation findByName(@Param("personName") String name);
    // Query a teszthez

    @Query(value="select max(id) from reservations", nativeQuery = true)
    Long maxId();
}
