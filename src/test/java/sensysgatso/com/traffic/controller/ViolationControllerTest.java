package sensysgatso.com.traffic.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import sensysgatso.com.traffic.dto.SummaryDto;
import sensysgatso.com.traffic.dto.ViolationDto;
import sensysgatso.com.traffic.service.ViolationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.emptyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sensysgatso.com.traffic.ViolationTestDataFactory.createViolationDto;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ViolationController.class)
class ViolationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViolationService violationService;

    @Test
    void getViolations_should_return200() throws Exception {
        String url = "/v1/violation";
        String result = "[{" +
                "\"id\":null," +
                "\"eventId\":\"f5bac61f-ff5b-495a-bca7-d000767aa20e\"," +
                "\"paid\":false," +
                "\"fine\":10" +
                "}]";
        UUID id = UUID.fromString("f5bac61f-ff5b-495a-bca7-d000767aa20e");
        ViolationDto violation = createViolationDto(id);
        when(violationService.getViolations(any())).thenReturn(List.of(violation));

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(result));

        verify(violationService).getViolations(any());
    }

    @Test
    void pay_should_return204() throws Exception {
        String id = "beb812fe-b3c3-46d8-a7c6-6a67e0a6a3fd";
        String url = String.format("/v1/violation/%s/pay", id);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(emptyString()));

        verify(violationService).markAsPaid(UUID.fromString(id));
    }

    @Test
    void getSummary_should_return200() throws Exception {
        String url = "/v1/violation/summary";
        String result = "{" +
                    "\"paid\":10," +
                    "\"notPaid\":1" +
                "}";
        SummaryDto summary = new SummaryDto(BigDecimal.TEN, BigDecimal.ONE);
        when(violationService.getSummary()).thenReturn(summary);

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(result));

        verify(violationService).getSummary();
    }

}
