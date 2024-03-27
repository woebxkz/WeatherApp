package util;

import java.sql.*;

public class MySQLConnection {

    private static final String URL = "jdbc:mysql://109.241.162.43:33306/sda_pogodynka";
    private static final String USER = "sda";
    private static final String PASSWORD = "sda2024MySQL";

    private static Connection connection;

    // Metoda do nawiązania połączenia z bazą danych
    public void connect() {
        try {
            // Ładowanie sterownika JDBC dla MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Nawiązanie połączenia
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Połączono z bazą danych MySQL.");
        } catch (ClassNotFoundException e) {
            System.out.println("Nie można znaleźć sterownika JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Błąd podczas nawiązywania połączenia z bazą danych.");
            e.printStackTrace();
        }
    }

    // Metoda do zwracania obiektu Connection
    public Connection getConnection() {
        return connection;
    }

    // Metoda do zamykania połączenia z bazą danych
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Zamknięto połączenie z bazą danych MySQL.");
            } catch (SQLException e) {
                System.out.println("Błąd podczas zamykania połączenia z bazą danych.");
                e.printStackTrace();
            }
        }
    }

    //executeUpdate
    public void executeUpdate() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM Locations");
            while (resultSet.next()) {
                System.out.println("ID " + resultSet.getInt("id"));
                System.out.println("Longtitude " + resultSet.getDouble("longtitude"));
                System.out.println("Latitude " + resultSet.getDouble("latitude"));
                System.out.println("City " + resultSet.getString("city"));
                System.out.println("Region " + resultSet.getString("region"));
                System.out.println("Country " + resultSet.getString("country"));
            }
        }
    }
        //create table
        public void createTableLocations() {
            try {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS Locations(id INT, " +
                        "longtitude DOUBLE, " +
                        "latitude DOUBLE, " +
                        "city VARCHAR(50), " +
                        "region VARCHAR(50), " +
                        "country VARCHAR(50));";
                statement.execute(sql);
                System.out.println("Utworzono tabelę Locations");
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void main(String... args){

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.connect();
        mySQLConnection.createTableLocations();
        mySQLConnection.disconnect();

    }


}