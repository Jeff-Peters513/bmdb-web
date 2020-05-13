package com.bmdb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.Credit;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
	List<Credit> findAllByMovieId(int movieId);
	List<Credit> findAllByActorId(int actorId);
}
