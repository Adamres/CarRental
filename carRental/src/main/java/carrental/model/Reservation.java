package carrental.model;

import lombok.Data;
import java.sql.Date;
import javax.persistence.*;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    private Long id;
    /* GeneratedValue(strategy = GenerationType.TABLE)
     * Ezt a funkciót kiszedtem és úgy döntöttem saját algoritmussal generálom az id-t, mivel
     * a generatedValue használatával hajlamosak összekavarodni a kulcsok,
     * ha más helyről is szúrunk be a táblába, pl a postgre query tooljából */

    @Column(name = "car_id")
    private Long car_id;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "total_price")
    private int total_price;

    @OneToOne
    @JoinColumn(name="car_id", referencedColumnName="id", insertable=false, updatable=false, nullable = false)
    private Car car;
    /* Kulcsok összekapcsolása, és az adott foglaláshoz melyik autó tartozik idegen kulcs alapján */
}
