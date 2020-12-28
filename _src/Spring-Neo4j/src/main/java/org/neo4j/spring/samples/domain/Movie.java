package org.neo4j.spring.samples.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Movie {
	
	@GraphId 
	private Long graphid;
	
	@Indexed(unique=true) 
	private String id;
	private String title;
	private int year;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "Movie_3 [graphid=" + graphid + ", id=" + id + ", title=" + title + ", year=" + year + "]";
	}
	
}
