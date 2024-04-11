package com.upload.uploaddocuments;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Service
public class AzureDBService {

    // JDBC URL for connecting to Azure PostgreSQL
    private static final String JDBC_URL = "jdbc:postgresql://searchablepostgresdb.postgres.database.azure.com:5432/postgres";
    private static final String USERNAME = "postgresadmin";
    private static final String PASSWORD = "Azurepwd$";

    public int createEntry(String location) {
        int autoIncrementedID = 0;
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection to Azure PostgreSQL
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare SQL statement for insertion
            String sql = "INSERT INTO document_details (location, source) VALUES (?, ?) RETURNING d_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set values for placeholders in the SQL statement
            preparedStatement.setString(1, location);
            preparedStatement.setString(2, "MAP");

            // Execute the SQL statement to insert the record and get the auto-incremented ID
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set to retrieve the auto-incremented ID
            if (resultSet.next()) {
                autoIncrementedID = resultSet.getInt("d_id");
                System.out.println("Auto-incremented ID: " + autoIncrementedID);
            } else {
                System.out.println("Failed to retrieve auto-incremented ID.");
            }

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autoIncrementedID;
    }


}