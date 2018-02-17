package com.yermilov.sciencefunding;


import com.j256.ormlite.support.ConnectionSource;
import com.yermilov.sciencefunding.dao.DAOFactory;
import com.yermilov.sciencefunding.transaction.DatabaseConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class SciencefundingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SciencefundingApplication.class, args);
	}

}
