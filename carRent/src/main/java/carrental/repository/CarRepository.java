package carrental.repository;

import java.util.List;
import carrental.model.Car;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "select * from cars where id = :id", nativeQuery = true)
    Car findCarByID(@Param("id") Long id);

    @Query(value = "select * from cars where name = :name", nativeQuery = true)
    Car findCarByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "update cars set active = :status where id = :id", nativeQuery = true)
    void changeActive(@Param("id") Long id, @Param("status") String status);
    /* Ha deaktiváljuk az autót, akkor törlődnek a hozzá tartozó foglalások */

    @Modifying
    @Transactional
    @Query(value = "update cars set name = :newName, price = :newPrice, photoURL = :newPhotoURL where id = :id", nativeQuery = true)
    void modifyCar(@Param("id") Long id, @Param("newName") String newName,
                   @Param("newPrice") int newPrice, @Param("newPhotoURL") String newPhotoURL);

    @Query(value="select max(id) from cars", nativeQuery = true)
    Long maxId();

    @Query(value = " select * from cars where active = 'active' and cars.id not in (select distinct car_id from reservations) union select * from cars " +
    " where active = 'active' and id in(select car_id from reservations where car_id not in(select b.car_id from (select * from reservations where " +
            " ((:from < start_date) and (start_date < :to)) or ((:from < end_date) and (end_date < :to)) or ((start_date < :from) and (:from < end_date)) or " +
            " ((start_date < :to) and (:to < end_date))) as b)) ", nativeQuery = true)
    List<Car> findAvailableCars(@Param("from") LocalDate from, @Param("to") LocalDate to);



    /* Lekérdezés magyarázata lejjebb */

    /*  !!FONTOS, HOGY A findAvailableCars QUERY MINDEN SORA ELOTT ES UTAN MARADJON OTT A SZOKOZ KULONBEN A LEKERDEZES NEM BIZTOS HOGY JOL ADODIK AT!! */
    /*

        select * from cars where active = 'active' and cars.id not in (select distinct car_id from reservations) union select * from cars "
            " where active = 'active' and id in(select car_id from reservations where car_id not in(select b.car_id from (select * from reservations where "
            " ((:from < start) and (start < :to)) or ((:from < end) and (end < :to))) as b))

         A hosszú autólistázó lekérdezés magyarázata:
             - A célunk, hogy lekérjük a user által megadott időintervallumban(from,to)szabad autókat.
               A lekérdezés két fő részből áll.

             -Az első rész, amely az union-ig tart, lekéri a car táblábólazokat az autókat, amelyek egy időpontra
              sincsennek lefoglalva(azaz az id-jük nincs a reservations táblában), azok biztosan kellenek.

             -A második rész logikája a következőképpen néz ki: Egy segédtáblába lekérjük azokat az autókat, amelyek foglalási idejük pont beleesnek
              abba az időintervallumban, amelyet a felhasználó megadott. Tehát a segédtáblába kerülnek a 'rossz' autó-dátum kombinációk.
              Ugye ha az adott autóból való sok foglalásból egy is belelóg a user által megadott időintervallumban, akkor már nem jeleníthető meg
              az adott autó.
              Majd lekérjük az összes olyan autót, amely nincsen benne ebben a segédtáblában.

             -Azért van szükség erre a segédtáblára, mivel valószínűleg egy autót többen is lefoglalnak különböző időpontokra és
              ezen időpontok közül, amelyek a segédtáblába kerülnek fog belelógni az összes a user által megadott időpontba,
              és ha csak 1 db is belelóg, akkor már nem jeleníthetjük meg azt az autót,
              tehát a segédtáblába bekerülő autó id-k biztosan nem lesznek jók nekünk

              Tehát nem azt kell leellenőrizni, hogy van-e EGY olyan foglalás x autóból, ami jó, hanem azt kell leellenőrizni, hogy
              ha pl az egyik x autóból egy foglalás már rossz időpontba van, akkor azt az autót nem listázhatjuk ki */

}
