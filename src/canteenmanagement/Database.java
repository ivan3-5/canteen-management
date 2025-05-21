package canteenmanagement;

import java.sql.*;
import javax.swing.JOptionPane;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/canteen_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    public boolean checkUsername(String user, String em) {
        String sqlCheckUser = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sqlCheckUser)) {
            stmt.setString(1, user);
            stmt.setString(2, em);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return true;
        }
    }
    
    public void insertUser(String fname, String lname, String user, String em, String pw) {
        String sqlInsertUser = "INSERT INTO users (firstname, lastname, username, email, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlInsertUser)) {
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, user);
            stmt.setString(4, em);
            stmt.setString(5, pw);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Signed Up Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }
    
    public User userLogin(String user, String pw) {
        String sqlCheckUser = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCheckUser)) {
            stmt.setString(1, user);
            stmt.setString(2, user);
            stmt.setString(3, pw);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Logged In Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return new User(
                    rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Selection failed: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close connection: " + e.getMessage());
        }
    }
}
