package org.neo4j.spring.samples;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

	String[] configs = { "classpath:Application-Config.xml" };

	public static void main(String[] args) {
		MainClass mainClass = new MainClass();
		mainClass.setup();

	}

	public void setup() {
		// Read the Spring configuration file and bring up the Spring framework,
		// so that all required beans can be initialized.
		ApplicationContext context = new ClassPathXmlApplicationContext(configs);
		System.out.println("Spring Context Created");
		// Get the Object of PersistNeo4JData from the Spring Context.
		PersistNeo4JData data = (PersistNeo4JData) context.getBean("persistNeo4JData");
		// Invoke addData() method to persist our neo4j nodes and relationships.
		data.addData();
		//Invoke Search 
		data.searchData();
		//Uses findAll() of Repositories
		data.getAllMovies();
		//Uses getAllMovies of interface <code>MovieRepository</code>
		data.searchRepositories();

	}

}
