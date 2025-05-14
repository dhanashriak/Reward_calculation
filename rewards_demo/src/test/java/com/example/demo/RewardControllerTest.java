package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCalculateRewardsAPI() throws Exception {
		String json = """
				[
				    {"customerId": "c1", "amount": 120, "transactionDate": "2024-03-01"},
				    {"customerId": "c1", "amount": 75, "transactionDate": "2024-03-15"},
				    {"customerId": "c2", "amount": 200, "transactionDate": "2024-04-01"}
				]
				""";

		mockMvc.perform(post("/api/rewards").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].customerId").value("c1"))
				.andExpect(jsonPath("$[1].customerId").value("c2"));
	}

	@Test
	public void testInvalidAmountAPI() throws Exception {
		String json = """
				[{"customerId": "c1", "amount": -100, "transactionDate": "2024-03-01"}]
				""";

		mockMvc.perform(post("/api/rewards").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest());
	}
}
