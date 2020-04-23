package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Credit;
import com.bmdb.business.JsonResponse;
import com.bmdb.db.CreditRepository;

@RestController
@RequestMapping("/credits")
public class CreditController {
	@Autowired
	private CreditRepository creditRepo;

	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<Credit> credits = creditRepo.findAll();
		if (credits.size() > 0) {
			jr = JsonResponse.getInstance(credits);
		} else {
			jr = JsonResponse.getInstance("No credits found.");
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		// expected responses?
		// 1- a single credit
		// 2- bad id - no credit found
		// 3- Exception?? - hold off for now implement handling as needed
		JsonResponse jr = null;
		Optional<Credit> credit = creditRepo.findById(id);
		if (credit.isPresent()) {
			jr = JsonResponse.getInstance(credit.get());
		} else {
			jr = JsonResponse.getInstance("No credit found for id: " + id);
		}
		return jr;
	}

	// "create" method
	@PostMapping("/")
	public JsonResponse createCredit(@RequestBody Credit c) {
		JsonResponse jr = null;
		try {
			c = creditRepo.save(c);
			jr = JsonResponse.getInstance(c);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating credit: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update credit
	@PutMapping("/")
	public JsonResponse updateCredit(@RequestBody Credit c) {
		JsonResponse jr = null;
		try {
			c = creditRepo.save(c);
			jr = JsonResponse.getInstance(c);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating  credit: " + e.getMessage());
			e.printStackTrace();
		}

		return jr;

	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteCredit(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			creditRepo.deleteById(id);
			jr = JsonResponse.getInstance("Credit id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting credit: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

}
