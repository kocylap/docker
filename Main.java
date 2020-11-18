import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://Full_2020_86318:3306/world";

    static final String USER = "pkocyla";
    static final String PASS = "admin";

    static Scanner in = new Scanner(System.in);
    private static final String SELECT_ALL_ROWS = "SELECT Id, Name, Continent, Region FROM country";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement()) {
                Class.forName("com.mysql.jdbc.Driver");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("Connecting to database...");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS country (Id int, Name varchar(255), Continent varchar(255), Region varchar(255));");
                int menu;
                do
                {
                    System.out.println("1. Show all countries\n2. Insert country\n3. Edit country by ID\n4. Delete country by ID\n0. Press 0 to Exit");
                    menu = in.nextInt();
                    switch (menu)
                    {
                        case 1:
                            getAllRows(stmt);
                            break;
                        case 2:
                            addCountry(stmt);
                            break;
                        case 3:
                            updateCountry(stmt);
                            break;
                        case 4:
                            deleteCountry(stmt);
                            break;
                    }
                } while(menu != 0);
            } catch (InterruptedException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    private static void deleteCountry(Statement stmt) throws SQLException {
        System.out.println("Enter ID to delete country:");
        String id = in.nextLine();
        stmt.executeUpdate("DELETE FROM country WHERE Id= '" + id + "';");
    }

    private static void updateCountry(Statement stmt) throws SQLException {
        System.out.println("Enter ID to edit:");
        String id = in.nextLine();

        System.out.println("Name:");
        String name = in.nextLine();

        System.out.println("Continent");
        String continent = in.nextLine();

        System.out.println("Region:");
        String region = in.nextLine();

        String sql = "UPDATE country SET Name = '" + name + "', Continent = '" + continent + "', Region = '" + region + "' WHERE Id= '" + id + "';";
        stmt.executeUpdate(sql);
    }

    private static void addCountry(Statement stmt) throws SQLException {
        System.out.println("Id");
        String id = in.nextLine();

        System.out.println("Name:");
        String name = in.nextLine();

        System.out.println("Continent");
        String continent = in.nextLine();

        System.out.println("Region:");
        String region = in.nextLine();
        
        String sql = "INSERT INTO country (Id, Name, Continent, Region) VALUES ('" + id + "', '" + name + "', '" + continent + "', '" + region + "')";
        stmt.executeUpdate(sql);
    }

    private static void getAllRows(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_ALL_ROWS);
        printHeader();
        printResult(rs);
        rs.close();
    }

    private static void printHeader() {
        System.out.println("Id \t Name \t Continent \t Region");
    }

    private static void printResult(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getInt("Id") + " \t " + rs.getString("Name") + " \t " + rs.getString("Continent") + " \t " + rs.getString("Region"));
        }
    }
}
