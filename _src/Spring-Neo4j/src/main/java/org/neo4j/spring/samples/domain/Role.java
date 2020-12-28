package org.neo4j.spring.samples.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="ACTED_IN")
public class Role {

	@StartNode @Fetch 
	private Artist artist;
	@EndNode  @Fetch 
	private Movie movie;
	private String role_name;

	@GraphId
	private Long id;

	public Role(){
		artist = new Artist();
		movie= new Movie();
	}
	public Role(Artist artist, Movie movie, String role_name){
		setArtist(artist);
		setMovie(movie);
		setRole_name(role_name);
	}
	
	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Role_3 [artist=" + artist + ", movie=" + movie + ", roleName=" + role_name + ", id=" + id + "]";
	}
	
	
	

}
