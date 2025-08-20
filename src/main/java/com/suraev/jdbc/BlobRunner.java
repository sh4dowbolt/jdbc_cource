package com.suraev.jdbc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BlobRunner {

    public static void main(String[] args) throws SQLException, IOException {
        
       
        //saveImage();
        getImage();
        
    }


    private static void getImage() throws SQLException, IOException{

        var selectStatement = """
                SELECT image 
                FROM aircraft
                WHERE id = ?
                """;
        var airId = 2;        

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(selectStatement)) {
            
                preparedStatement.setInt(1, airId);

                var resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    var image = resultSet.getBytes("image");
                    Files.write(Path.of("src/main/resources/Airbus_new.jpg"), image, StandardOpenOption.CREATE);
                }

        } 
    }
    private static void saveImage() throws SQLException,IOException  {

        var updateStatement = "UPDATE aircraft SET image = ? WHERE id=2";

        try (var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(updateStatement)) {
            
            preparedStatement.setBytes(1, 
            Files.readAllBytes(Path.of("src/main/resources/Airbus.jpg")));
          
            preparedStatement.executeUpdate();
            
        } 
    }

    // private static void saveImage() throws SQLException,IOException  {

    //     var updateStatement = "UPDATE aircraft SET image = ? WHERE id=2";

    //     try (var connection = ConnectionManager.get();
    //         var preparedStatement = connection.prepareStatement(updateStatement)) {
    //         connection.setAutoCommit(false);
            
    //         var blob = connection.createBlob();

    //         blob.setBytes(1, Files.readAllBytes(Path.of("resources", "Airbus_A320.jpg")));

    //         preparedStatement.setBlob(1, blob);

    //         preparedStatement.executeUpdate();
            
    //         connection.commit();
    //     } 
    // }
}
