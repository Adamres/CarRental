package carrental.controllerfunctest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {
    /* Ebben az osztályban az kerül tesztelésre, hogy a HomeController
    sikeresen visszaadja-e a benne definiált view útvonalakat */

    @Autowired
    private MockMvc mockMvc;
    /* Ha a mockMvc-t aláhuzza az IDE az nem baj, attól még ugyanúgy lefut a
    teszt, csak az IDE úgy érzékeli, hogy nem lehet autowireolni a MockMvc-t, de be van kötve. */

    @Test
    public void testIndexPage() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testVehiclesPage() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/vehicles")
                        .param("from","2022-04-04")
                        .param("to","2022-05-06"))
                        .andDo(print())
                        .andExpect(status().isOk());
    }
}