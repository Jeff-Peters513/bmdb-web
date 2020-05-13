package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.business.JsonResponse;
import com.bmdb.db.ActorRepository;

@CrossOrigin
@RestController
@RequestMapping("/actors")
public class ActorController {
	@Autowired
	private ActorRepository actorRepo;

	// list all items
	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<Actor> actors = actorRepo.findAll();
		if (actors.size() > 0) {
			jr = JsonResponse.getInstance(actors);
		} else {
			jr = JsonResponse.getErrorInstance("No Actor found.");
		}
		return jr;
	}

	// get /list one item
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		// expected responses?
		// 1- a single actor
		// 2- bad id - no actor found
		// 3- Exception?? - hold off for now implement handling as needed
		JsonResponse jr = null;
		Optional<Actor> actor = actorRepo.findById(id);
		if (actor.isPresent()) {
			jr = JsonResponse.getInstance(actor.get());
		} else {
			jr = JsonResponse.getErrorInstance("No Actor found for id: " + id);
		}
		return jr;
	}

	// "create method
	@PostMapping("/")
	public JsonResponse createActor(@RequestBody Actor a) {
		JsonResponse jr = null;
		try {
			a = actorRepo.save(a);
			jr = JsonResponse.getInstance(a);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating Actor: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method
	@PutMapping("/")
	public JsonResponse updateActor(@RequestBody Actor a) {
		JsonResponse jr = null;
		try {
			a = actorRepo.save(a);
			jr = JsonResponse.getInstance(a);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating Actor: " + e.getMessage());
			e.printStackTrace();
		}

		return jr;

	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteActor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			actorRepo.deleteById(id);
			jr = JsonResponse.getInstance("Actor id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Actor: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// disclaimer - this method may not follow strict API Style Guide rules
	// String lastName
	@GetMapping("/by-actor-lastname")
	public JsonResponse listBylastName(@RequestParam String lastName) {
		JsonResponse jr = null;
		List<Actor> actors = actorRepo.findAllBylastName(lastName);
		if (actors.size() > 0) {
			jr = JsonResponse.getInstance(actors);
		} else {
			jr = JsonResponse.getInstance("No Actor(s) with entered Last Name: " + lastName + " ");
		}
		return jr;
	}

}
