package org.neo4j.spring.samples.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Artist {

	@GraphId
	private Long graphid;
	
	@Indexed(unique=true) 
	private String id;
	@Indexed (indexType = IndexType.FULLTEXT, indexName = "search") private String name;
	private String [] workedAs;
	private int year_Of_Birth;
	
	@RelatedTo(type="ACTED_IN", direction=Direction.OUTGOING)
	private Set<Movie> movies;
	
	@RelatedToVia(type="ACTED_IN",direction=Direction.OUTGOING)
	private Iterable<Role> roles;
	
	
	public Artist(){
		movies = new HashSet<Movie>();
		
	}

	public Long getGraphid() {
		return graphid;
	}

	public void setGraphid(Long graphid) {
		this.graphid = graphid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getWorkedAs() {
		return workedAs;
	}

	public void setWorkedAs(String[] workedAs) {
		this.workedAs = workedAs;
	}

	public int getYear_Of_Birth() {
		return year_Of_Birth;
	}

	public void setYear_Of_Birth(int yearOfBirth) {
		this.year_Of_Birth = yearOfBirth;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	public Iterable<Role> getRoles() {
		return roles;
	}

	public void setRoles(Iterable<Role> roles) {
		this.roles = roles;
	}
	
	public void addMovies(Movie movie){
		getMovies().add(movie);
	}


	@Override
	public String toString() {
		return "Artist_3 [graphid=" + graphid + ", id=" + id + ", name=" + name	+ ", workedAs=" + Arrays.toString(workedAs) + ", yearOfBirth=" + "yearOfBirth" + "roles=" + roles + "]";
	}

	

}
