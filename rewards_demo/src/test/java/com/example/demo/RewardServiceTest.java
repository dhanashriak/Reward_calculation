package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.exception.InvalidTransactionException;
import com.example.model.RewardResponse;
import com.example.model.Transaction;
import com.example.service.RewardService;

@SpringBootTest
public class RewardServiceTest {

	@Autowired
	RewardService service;

	@Test
	public void testRewardCalculation() {
		List<Transaction> txns = List.of(new Transaction("cust1", 120, LocalDate.of(2024, 3, 1)),
				new Transaction("cust1", 80, LocalDate.of(2024, 3, 15)),
				new Transaction("cust2", 55, LocalDate.of(2024, 4, 10)),
				new Transaction("cust2", 200, LocalDate.of(2024, 5, 10)));

		List<RewardResponse> result = service.calculateRewards(txns);
		assertEquals(2, result.size());

		RewardResponse cust1 = result.stream().filter(r -> r.getCustomerId().equals("cust1")).findFirst().orElse(null);
		assertEquals(90 + 30, cust1.getTotalPoints());
	}

	@Test
	public void testEmptyTransactionList() {
		assertThrows(InvalidTransactionException.class, () -> {
			service.calculateRewards(new ArrayList<>());
		});
	}

	@Test
	public void testNegativeAmount() {
		List<Transaction> txns = List.of(new Transaction("cust", -50, LocalDate.now()));
		assertThrows(InvalidTransactionException.class, () -> {
			service.calculateRewards(txns);
		});
	}
}
