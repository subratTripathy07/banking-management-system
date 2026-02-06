import java.sql.*;
import java.util.Scanner;

public class user {

    // JDBC configuration
    static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    static final String USER = "root";
    static final String PASSWORD = "subrat7894";

    // DB Connection
    static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    // user_exist()
    static boolean user_exist(String email) {
        String sql = "SELECT email FROM user WHERE email=?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // register()
    static boolean register(String fullName, String email, String password) {

        if (user_exist(email)) {
            return false;
        }

        String sql = "INSERT INTO user (full_name, email, password) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // login()
    static boolean login(String email, String password) {
        String sql = "SELECT email FROM users WHERE email=? AND password=?";

        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // MAIN METHOD
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Full Name: ");
            String fullName = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            if (register(fullName, email, password)) {
                System.out.println("Registration Successful ");
            } else {
                System.out.println("User already exists ");
            }

        } else if (choice == 2) {
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            if (login(email, password)) {
                System.out.println("Login Successful ");
            } else {
                System.out.println("Invalid email or password ");
            }
        } else {
            System.out.println("Invalid choice");
        }

        sc.close();
    }
}
