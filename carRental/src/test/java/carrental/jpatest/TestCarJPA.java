package carrental.jpatest;

import org.junit.Test;
import carrental.model.Car;
import org.junit.runner.RunWith;
import carrental.repository.CarRepository;
import static org.junit.Assert.assertEquals;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCarJPA {
    /* Ebben az osztályban az autóval kapcsolatos adatbázis-műveleteket teszteljük */

    @Autowired
    CarRepository carRepository;
    Car testCar;
    Car retrievedCar;

    @Test
    public void addAndModifyAndDeleteCarTest() throws Exception {

        testCar = new Car();
        Long maxId = carRepository.maxId();
        if(maxId == null){
            maxId = Long.valueOf(0);
        }
        testCar.setId(maxId + 1);
        testCar.setName("MclarenTestNameUnique");
        testCar.setActive("active");
        testCar.setPrice(2000);
        testCar.setPhotoURL("https://cdn.pixabay.com/photo/2019/05/23/02/21/mclaren-4223024__480.jpg");

        carRepository.save(testCar);
        retrievedCar = carRepository.findCarByName(testCar.getName());

        assertEquals(testCar.getName(), retrievedCar.getName());
        assertEquals(testCar.getPrice(), retrievedCar.getPrice());
        /* Teszt, hogy tudunk-e hozzáadni az autót az adatbázishoz */

        testCar.setName("MclarenModifiedTestName");
        testCar.setPrice(1000);
        testCar.setPhotoURL("https://www.automotor.hu/wp-content/uploads/2021/03/mclaren1.jpg");
        carRepository.modifyCar(testCar.getId(), testCar.getName(), testCar.getPrice(), testCar.getPhotoURL());
        retrievedCar = carRepository.findCarByName(testCar.getName());
        /* Újra lekérjük a módosított autót */

        assertEquals(testCar.getName(), retrievedCar.getName());
        assertEquals(testCar.getPrice(), retrievedCar.getPrice());
        /* Teszt, hogy működik-e az autó módosítása funkció */

        carRepository.deleteById(retrievedCar.getId());
        assertEquals(null, carRepository.findCarByName(retrievedCar.getName()));
        /* Teszt, hogy működik-e az autó törlése az adatbázisból funkció */

    }
}

