/**
 * 
 */
package org.neo4j.spring.samples.repositories;

import org.neo4j.spring.samples.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sagupta
 *
 */
@Repository
public interface MovieRepository extends GraphRepository<Movie>{
	
	@Query("match (n) return n;")
	public Iterable<Movie> getAllMovies();

	
	
}
