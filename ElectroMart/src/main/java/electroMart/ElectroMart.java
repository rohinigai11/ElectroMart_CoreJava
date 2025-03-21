package electroMart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ElectroMart {

    
//    private static final String DB_NAME = "electromart";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
    private static final String URL = "jdbc:postgresql://localhost:5432/electromart"; 
    static Scanner scanner = new Scanner(System.in);

    
    /*public static void createDatabase(String dbName) {
        String sql = "CREATE DATABASE " + dbName;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    */

//    create the table
    public static void createTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                     "ProductId SERIAL PRIMARY KEY, " +
                     "ProductBrandName VARCHAR(100) NOT NULL, " +
                     "ProductName VARCHAR(100) NOT NULL, " +
                     "ProductPrice INT NOT NULL)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            
            System.out.println("Table '" + tableName + "' created successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

//    inserting data in to the table
    public static void insertIntoTbl(String tableName,String ProductBrandName, String ProductName, int ProductPrice) {
        String sql = "INSERT INTO " + tableName + " (ProductBrandName, ProductName, ProductPrice) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL , USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, ProductBrandName);
            pstmt.setString(2, ProductName);
            pstmt.setInt(3, ProductPrice);
            pstmt.executeUpdate();
            System.out.println("Data inserted into '" + tableName + "' successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

//    delete the row using the id
    public static void deleteRow(String tableName, int ProductId) {
        String sql = "DELETE FROM " + tableName + " WHERE ProductId = ?";
        try (Connection conn = DriverManager.getConnection(URL , USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ProductId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Row with ID " + ProductId + " deleted successfully.");
            } else {
                System.out.println("No row found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting row: " + e.getMessage());
        }
    }

//    update the name of product using the id
    public static void updateRow(String tableName, int ProductId, String newProductName) {
        String sql = "UPDATE " + tableName + " SET ProductName = ? WHERE ProductId = ?";
        try (Connection conn = DriverManager.getConnection(URL , USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newProductName);
            pstmt.setInt(2, ProductId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Row with ID " + ProductId + " updated successfully.");
            } else {
                System.out.println("No row found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
        }
    }

//    display all the records in the table
    public static void display(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DriverManager.getConnection(URL , USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Data in table '" + tableName + "':");
            while (rs.next()) {
                System.out.println(rs.getInt("ProductId") + " , " +
                				   rs.getString("ProductBrandName") + " , " +
                                   rs.getString("ProductName") + " , " +
                                   rs.getInt("ProductPrice") + " , ");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving table data: " + e.getMessage());
        }
    }

    
//    drop thetable by using the table name
    public static void dropTable(String tableName) {
        String query = "DROP TABLE IF EXISTS " + tableName;
        try (Connection conn = DriverManager.getConnection(URL , USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Table '" + tableName + "' has been dropped successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // to show the list of tables available in the current database
   public static void showListOfTbl() {
        String query = "SELECT tablename FROM pg_tables WHERE schemaname = 'public'"; 
        String query1 = "SELECT tablename FROM pg_tables";// Query to fetch table names
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             System.out.println("List of Tables in Database:");
             while (rs.next()) {
                System.out.println("- " + rs.getString("tablename"));  // Fetch table name column
             }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
//   main method
    public static void main(String[] args) {
        int choice;
        String tableName;
        
        while (true) {
            System.out.println("***** Electro Mart *****");
//            System.out.println("1. Create Database");
            System.out.println("1. Create Table");
            System.out.println("2. Insert Data");
            System.out.println("3. Delete Row");
            System.out.println("4. Update Row");
            System.out.println("5. Display Table");
            System.out.println("6. Drop table ");
            System.out.println("7. Show List of Tables");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
//            for selecting the choices 
//            and according to that performs the operation
            switch (choice) {
                /*case 1:
                    System.out.print("Enter database name: ");
                    String dbName = scanner.nextLine();
                    createDatabase(dbName);
                    break;*/
            
                case 1:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    createTable(tableName);
                    break;
                    
                case 2:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    System.out.print("Enter brand name: ");
                    String ProductBrandName = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String ProductName = scanner.nextLine();
                    System.out.print("Enter price: ");
                    int ProductPrice = scanner.nextInt();
                    scanner.nextLine(); 
                    insertIntoTbl(tableName,ProductBrandName, ProductName, ProductPrice);
                    break;
                    
                case 3:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    System.out.print("Enter row ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    deleteRow(tableName, idToDelete);
                    break;
                    
                case 4:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    System.out.print("Enter row ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter new name: ");
                    String newProductName = scanner.nextLine();
                    /*System.out.print("Enter new price: ");
                    int newProductPrice = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter new type: ");
                    String newProductType = scanner.nextLine();*/
                    updateRow(tableName, idToUpdate, newProductName);
                    break;
                    
                case 5:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    display(tableName);
                    break;
                    
                case 6:
                	System.out.println("Enter table name: ");
                	tableName= scanner.nextLine();
                	dropTable(tableName);
                	break;
                	
                case 7:
                	showListOfTbl();
                	break;
                	
                case 8:
                    System.out.println("Exit");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
