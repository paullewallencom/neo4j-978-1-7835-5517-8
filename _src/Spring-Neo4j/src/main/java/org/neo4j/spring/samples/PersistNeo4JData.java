package org.neo4j.spring.samples;

import java.util.Iterator;

import org.neo4j.spring.samples.domain.Artist;
import org.neo4j.spring.samples.domain.Movie;
import org.neo4j.spring.samples.domain.Role;
import org.neo4j.spring.samples.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.aspects.core.NodeBacked;
import org.springframework.data.neo4j.aspects.core.RelationshipBacked;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

public class PersistNeo4JData {

	@Autowired
	private Neo4jTemplate neo4jTemplate;
	@Autowired
	MovieRepository movieRepo;

	@Transactional
	public void addData() {
		// Create Movie
		Movie rocky = new Movie();
		rocky.setTitle("Rocky");
		rocky.setYear(1976);
		rocky.setId(rocky.getTitle() + "-" + String.valueOf(rocky.getYear()));

		// Create Artists
		Artist artist = new Artist();
		artist.setName("Sylvester Stallone");
		artist.setWorkedAs(new String[] { "Actor", "Director" });
		artist.setYear_Of_Birth(1946);
		artist.setId(artist.getName() + "-" + artist.getYear_Of_Birth());

		// create Relationship
		Role role = new Role(artist, rocky, "Rocky Balboa");

		neo4jTemplate.save(artist);
		neo4jTemplate.save(rocky);
		neo4jTemplate.save(role);
		
		//Enable only if you are using Neo4j Aspects
		
		//((NodeBacked)artist).persist();
		//((NodeBacked)rocky).persist();
		//((RelationshipBacked)role).persist(); 

	}
	
	@Transactional
	public void searchData() {
		System.out.println("Role retreived from Database - 1");
		Result<Role> result = neo4jTemplate.findAll(org.neo4j.spring.samples.domain.Role.class);
		while (result.iterator().hasNext()) {
			Role role = result.iterator().next();
			System.out.println(role.toString());
		}

		System.out.println("Artists and Roles retreived from Database - End");

	}
	
	@Transactional
	public void getAllMovies() {
		Iterator<Movie> iterator = movieRepo.findAll().iterator();
		//System.out.println("Movie Repo = "+movieRepo.toString());
		System.out.println("\n 11Start Dumping Movies.............\n");
		while (iterator.hasNext()) {
			Movie movie = iterator.next();
			System.out.println(" Movies ---> ID = " + movie.getId() + ", Graph ID = " + movie.getId() + ", Title = " + movie.getTitle()	+ ", Year of Release = " + movie.getYear());
		}
		System.out.println("\n Completed Dumping Movies.............\n");

	}


	@Transactional
	public void searchRepositories() {

		Iterator<Movie> iterator = movieRepo.getAllMovies().iterator();
		while (iterator.hasNext()) {
			Movie movie = iterator.next();
			System.out.println(movie);
		}

	}

}
