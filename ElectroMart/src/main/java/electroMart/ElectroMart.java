package electroMart;

import java.sql.*;
import java.util.Scanner;

public class ElectroMart {

    
    private static final String DB_NAME = "mydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
    private static final String URL = "jdbc:postgresql://localhost:5432/"; 
    static Scanner scanner = new Scanner(System.in);

    
    public static void createDatabase(String dbName) {
        String sql = "CREATE DATABASE " + dbName;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    
    public static void createTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                     "ProductId SERIAL PRIMARY KEY, " +
                     "ProductName VARCHAR(100) NOT NULL, " +
                     "ProductPrice INT NOT NULL, " +
                     "ProductType VARCHAR(100) NOT NULL)";
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table '" + tableName + "' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    
    public static void insertData(String tableName, String ProductName, int ProductPrice, String ProductType) {
        String sql = "INSERT INTO " + tableName + " (ProductName, ProductPrice, ProductType) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ProductName);
            pstmt.setInt(2, ProductPrice);
            pstmt.setString(3, ProductType);
            pstmt.executeUpdate();
            System.out.println("Data inserted into '" + tableName + "' successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    
    public static void deleteRow(String tableName, int ProductId) {
        String sql = "DELETE FROM " + tableName + " WHERE ProductId = ?";
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
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

    
    public static void updateData(String tableName, int ProductId, String newProductName, int newProductPrice, String newProductType) {
        String sql = "UPDATE " + tableName + " SET ProductName = ?, ProductPrice = ?, ProductType = ? WHERE ProductId = ?";
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newProductName);
            pstmt.setInt(2, newProductPrice);
            pstmt.setString(3, newProductType);
            pstmt.setInt(4, ProductId);
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

    /**
     * 🏆 Show all data from a specific table
     */
    public static void showTable(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Data in table '" + tableName + "':");
            while (rs.next()) {
                System.out.println(rs.getInt("ProductId") + " | " +
                                   rs.getString("ProductName") + " | " +
                                   rs.getInt("ProductPrice") + " | " +
                                   rs.getString("ProductType"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving table data: " + e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        int choice;
        String tableName;
        
        while (true) {
            System.out.println("\n===== Electro Mart =====");
            System.out.println("1. Create Database");
            System.out.println("2. Create Table");
            System.out.println("3. Insert Data");
            System.out.println("4. Delete Row");
            System.out.println("5. Update Row");
            System.out.println("6. Show Table Data");
            
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();  
            
            switch (choice) {
                case 1:
                    System.out.print("Enter database name: ");
                    String dbName = scanner.nextLine();
                    createDatabase(dbName);
                    break;
                case 2:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    createTable(tableName);
                    break;
                case 3:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String ProductName = scanner.nextLine();
                    System.out.print("Enter price: ");
                    int ProductPrice = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter type: ");
                    String ProductType = scanner.nextLine();
                    insertData(tableName, ProductName, ProductPrice, ProductType);
                    break;
                case 4:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    System.out.print("Enter row ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    deleteRow(tableName, idToDelete);
                    break;
                case 5:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    System.out.print("Enter row ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter new name: ");
                    String newProductName = scanner.nextLine();
                    System.out.print("Enter new price: ");
                    int newProductPrice = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter new type: ");
                    String newProductType = scanner.nextLine();
                    updateData(tableName, idToUpdate, newProductName, newProductPrice, newProductType);
                    break;
                case 6:
                    System.out.print("Enter table name: ");
                    tableName = scanner.nextLine();
                    showTable(tableName);
                    break;
                
                case 7:
                    System.out.println("Exit");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

