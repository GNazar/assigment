package sensysgatso.com.traffic.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import sensysgatso.com.traffic.service.EventService;

import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void createEvent_should_return200() throws Exception {
        String createEventReq = "{" +
                "    \"id\": \"d9bb7458-d5d9-4de7-87f7-7f39edd51d15\"," +
                "    \"eventDate\": \"2022-02-09T00:25:20.529\"," +
                "    \"eventType\": \"RED_LIGHT\"," +
                "    \"licensePlate\": \"ABC-123\"," +
                "    \"speed\": \"90.0\"," +
                "    \"limit\": \"50.0\"," +
                "    \"unity\": \"km/h\"," +
                "    \"processed\": \"false\"" +
                "}";
        String url = "/v1/event";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createEventReq))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(emptyString()));
    }

}
