package at.technikum.restserver;


import at.technikum.models.Tour;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TourResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void retrieve() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/resources/tours/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    public void retrieveAll() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/resources/tours")
                .accept(MediaType.APPLICATION_JSON);

        var response = mockMvc
                .perform(requestBuilder)
                .andReturn().getResponse();

        var jsonString = response.getContentAsString();
        var tours = om.readValue(
                jsonString,
                new TypeReference<List<Tour>>() {
                }
        );

        //variant 2: uses assert methods
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(response.getContentType()).containsIgnoringCase(MediaType.APPLICATION_JSON.toString());
        assertEquals(2, tours.size());
    }

}
