package carrental.controllerfunctest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {
    /* Ebben az osztályban az kerül tesztelésre, hogy az AdminController
    sikeresen visszaadja-e a benne definiált view útvonalakat, admin jogokkal */

    @Autowired
    private MockMvc mockMvc;
    /* Ha a mockMvc-t aláhuzza az IDE az nem baj, attól még ugyanúgy lefut a
    teszt, csak az IDE úgy érzékeli, hogy nem lehet autowireolni a MockMvc-t, de be van kötve. */

    @Test
    public void testAdmin() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .with(user("admin").password("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testAdminVehicles() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/vehicles")
                .with(user("admin").password("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testModifyCar() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/modifycar")
                        .with(user("admin").password("admin").roles("ADMIN"))
                        .param("carID","1"))  /* VALID ID! */
                        .andDo(print())
                        .andExpect(status()
                                .isOk());
          /* VALID adatbázisban szereplő autó id-t kell megadni paraméterként,tehát ha nem fut le
          a teszt, akkor ellenőrizd, hogy létezik-e a paramban átadott id a 'cars' nevű táblában */
    }

    @Test
    public void testAddCar() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/addcar")
                .with(user("admin").password("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testReservations() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservations")
                .with(user("admin").password("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }
}
