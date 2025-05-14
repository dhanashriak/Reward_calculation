package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.RewardResponse;
import com.example.model.Transaction;
import com.example.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/rewards")

public class RewardController {

	private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

	private final RewardService rewardService;

	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@PostMapping
	public ResponseEntity<List<RewardResponse>> calculateRewards(@RequestBody List<Transaction> transactions) {
		logger.info("Received request to calculate rewards for {} transaction(s)",
				transactions != null ? transactions.size() : 0);

		if (transactions != null && !transactions.isEmpty()) {
			List<String> customerIds = transactions.stream().map(Transaction::getCustomerId).distinct()
					.collect(Collectors.toList());
			logger.debug("Customer IDs in request: {}", customerIds);
		}

		List<RewardResponse> response = rewardService.calculateRewards(transactions);

		logger.info("Successfully calculated rewards for {} customer(s)", response.size());
		return ResponseEntity.ok(response);
	}
}
