package com.bmdb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.MovieGenre;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
	List<MovieGenre> findAllByMovieId(int movieId);
	List<MovieGenre> findAllByGenreId(int genreId);
		//List<MovieGenre> movieGenres = movieGenreRepo.findAllByGenreId(genreId);
	
}
