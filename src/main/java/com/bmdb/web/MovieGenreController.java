package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.JsonResponse;
import com.bmdb.business.MovieGenre;
import com.bmdb.db.MovieGenreRepository;

@RestController
@RequestMapping("/movie-genres")
public class MovieGenreController {

	@Autowired
	private MovieGenreRepository movieGenreRepo;

	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<MovieGenre> movieGenre = movieGenreRepo.findAll();
		if (movieGenre.size() > 0) {
			jr = JsonResponse.getInstance(movieGenre);
		} else {
			jr = JsonResponse.getInstance("No Movie Genre found.");
		}
		return jr;
	}
	
	// disclaimer - this method may not follow strict API Style Guide rules
	@GetMapping("/by-movie-id")
	public JsonResponse listByMovieId(@RequestParam int movieId) {
		JsonResponse jr = null;
		List<MovieGenre> movieGenres = movieGenreRepo.findAllByMovieId(movieId);
		if (movieGenres.size() > 0) {
			jr = JsonResponse.getInstance(movieGenres);
		} else {
			jr = JsonResponse.getInstance("No Movie Genres found for movie id: "+movieId+" ");
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
		Optional<MovieGenre> movieGenre = movieGenreRepo.findById(id);
		if (movieGenre.isPresent()) {
			jr = JsonResponse.getInstance(movieGenre.get());
		} else {
			jr = JsonResponse.getInstance("No movie genre found for id: " + id);
		}
		return jr;
	}

	// "create" method
	@PostMapping("/")
	public JsonResponse createMovieGenre(@RequestBody MovieGenre mg) {
		JsonResponse jr = null;
		try {
			mg = movieGenreRepo.save(mg);
			jr = JsonResponse.getInstance(mg);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating Movie genre: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update credit
	@PutMapping("/")
	public JsonResponse updateMovieGenre(@RequestBody MovieGenre mg) {
		JsonResponse jr = null;
		try {
			mg = movieGenreRepo.save(mg);
			jr = JsonResponse.getInstance(mg);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating movie genre: " + e.getMessage());
			e.printStackTrace();
		}

		return jr;

	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteMovieGenre(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			movieGenreRepo.deleteById(id);
			jr = JsonResponse.getInstance("Movie Genre id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Movie Genre: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// disclaimer - this method may not follow strict API Style Guide rules
		@GetMapping("/by-genre-id")
		public JsonResponse listByGenreId(@RequestParam int genreId) {
			JsonResponse jr = null;
			List<MovieGenre> movieGenres = movieGenreRepo.findAllByGenreId(genreId);
			if (movieGenres.size() > 0) {
				jr = JsonResponse.getInstance(movieGenres);
			} else {
				jr = JsonResponse.getInstance("No Movie(s) found for genre id: "+genreId+" ");
			}
			return jr;
		}
		
	
	
	
	
	
	
}
