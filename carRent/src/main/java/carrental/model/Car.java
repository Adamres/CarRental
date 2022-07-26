package carrental.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    private Long id;
    /* GeneratedValue(strategy = GenerationType.TABLE)
    * Ezt a funkciót kiszedtem és úgy döntöttem saját algoritmussal generálom az id-t, mivel
    * a generatedValue használatával hajlamosak összekavarodni a kulcsok,
    * ha más helyről is szúrunk be a táblába, pl a postgre query tooljából */

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "photoURL")
    private String photoURL;

    @Column(name = "active", columnDefinition = "text default 'active'")
    private String active;
}