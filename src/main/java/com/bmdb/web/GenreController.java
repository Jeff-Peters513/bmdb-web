package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Genre;
import com.bmdb.business.JsonResponse;
import com.bmdb.db.GenreRepository;

@RestController
@RequestMapping("/genres")
public class GenreController {

	@Autowired
	private GenreRepository genreRepo;

	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<Genre> genre = genreRepo.findAll();
		if (genre.size() > 0) {
			jr = JsonResponse.getInstance(genre);
		} else {
			jr = JsonResponse.getInstance("No genre found.");
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
		Optional<Genre> genre = genreRepo.findById(id);
		if (genre.isPresent()) {
			jr = JsonResponse.getInstance(genre.get());
		} else {
			jr = JsonResponse.getInstance("No genre found for id: " + id);
		}
		return jr;
	}

	// "create" method
	@PostMapping("/")
	public JsonResponse createGenre(@RequestBody Genre g) {
		JsonResponse jr = null;
		try {
			g = genreRepo.save(g);
			jr = JsonResponse.getInstance(g);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating g: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update credit
	@PutMapping("/")
	public JsonResponse updateGenre(@RequestBody Genre g) {
		JsonResponse jr = null;
		try {
			g = genreRepo.save(g);
			jr = JsonResponse.getInstance(g);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating  genre: " + e.getMessage());
			e.printStackTrace();
		}

		return jr;

	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteGenre(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			genreRepo.deleteById(id);
			jr = JsonResponse.getInstance("Genre id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Genre: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

}

	
	
	
	

