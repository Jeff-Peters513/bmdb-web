package com.bmdb.business;

import javax.persistence.*;

@Entity
public class Credit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// this should really be an Actor, not actorID
	// private int actorId;
	@ManyToOne
	@JoinColumn(name = "ActorID")
	private Actor actor;
	// this should really be a Movie, not movieID
	// private int movieId;
	@ManyToOne
	@JoinColumn(name = "MovieID")
	private Movie movie;
	private String role;

	// empty constructor
	public Credit() {
		super();
	}

	// fully loaded constructor
	public Credit(int id, Actor actor, Movie movie, String role) {
		super();
		this.id = id;
		this.actor = actor;
		this.movie = movie;
		this.role = role;
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// IDE generated toString()
	@Override
	public String toString() {
		return "Credit [id=" + id + ", actor=" + actor + ", movie=" + movie + ", role=" + role + "]";
	}

}
