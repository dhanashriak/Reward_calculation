package com.example.service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.exception.InvalidTransactionException;
import com.example.model.RewardResponse;
import com.example.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RewardService {

	private static final Logger logger = LoggerFactory.getLogger(RewardService.class);

	public List<RewardResponse> calculateRewards(List<Transaction> transactions) {
		logger.info("Calculating rewards for {} transaction(s)", transactions != null ? transactions.size() : 0);

		if (transactions == null || transactions.isEmpty()) {
			logger.warn("Transaction list is null or empty.");
			throw new InvalidTransactionException("Transaction list cannot be null or empty.");
		}

		Map<String, Map<YearMonth, Integer>> customerMonthlyPoints = new HashMap<>();

		for (Transaction txn : transactions) {
			logger.debug("Processing transaction: {}", txn);

			if (txn.getAmount() < 0) {
				logger.error("Negative amount in transaction: {}", txn);
				throw new InvalidTransactionException("Transaction amount cannot be negative.");
			}

			if (txn.getCustomerId() == null || txn.getCustomerId().isEmpty()) {
				logger.error("Empty customer ID in transaction: {}", txn);
				throw new InvalidTransactionException("Customer Id cannot be empty.");
			}

			int points = calculatePoints(txn.getAmount());
			YearMonth month = YearMonth.from(txn.getTransactionDate());

			logger.debug("Calculated {} point(s) for customer {} in month {}", points, txn.getCustomerId(), month);

			customerMonthlyPoints.computeIfAbsent(txn.getCustomerId(), k -> new HashMap<>()).merge(month, points,
					Integer::sum);
		}

		List<RewardResponse> responses = customerMonthlyPoints.entrySet().stream().map(entry -> {
			String customerId = entry.getKey();
			Map<String, Integer> monthPoints = entry.getValue().entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
			int total = monthPoints.values().stream().mapToInt(Integer::intValue).sum();
			logger.info("Customer {} earned a total of {} point(s)", customerId, total);
			return new RewardResponse(customerId, monthPoints, total);
		}).collect(Collectors.toList());

		logger.info("Reward calculation complete for all customers.");
		return responses;
	}

	private int calculatePoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (int) ((amount - 100) * 2);
			points += 50;
		} else if (amount > 50) {
			points += (int) (amount - 50);
		}
		return points;
	}
}