import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class accounts {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "subrat7894";

    private static final Scanner scanner = new Scanner(System.in);

    // 1. Create Account
    private static void openAccount() {
        System.out.print("Enter account number: ");
        String accNo = scanner.nextLine();

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email id: ");
        String email = scanner.next();

        System.out.print("Initial deposit: ");
        double balance = scanner.nextDouble();

        System.out.print("Set password (4 values): ");
        String password = scanner.next();

        String sql = "INSERT INTO accounts  VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accNo);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setDouble(4, balance);
            ps.setString(5, password);

            ps.executeUpdate();
            System.out.println("Account created successfully!");

        } catch (Exception e) {
            System.err.println("Error creating account: " + e.getMessage());
        }
    }

    // 2. Get all accounts
    private static void getAccount() {
        String sql = "SELECT * FROM accounts";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("\nACC NO ");
            while (rs.next()) {
                System.out.println(rs.getString("acc_no"));
            }
        } catch (Exception e) {
            System.err.println("Error viewing accounts: " + e.getMessage());
        }
    }

    // 3. Check if account exists
    private static void accountExists() {
        System.out.print("Enter account number to check: ");
        String accNo = scanner.nextLine();

        String sql = "SELECT account_number FROM accounts WHERE acc_no = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Account exists for account number: " + accNo);
                } else {
                    System.out.println("No account found with account number: " + accNo);
                }
            }

        } catch (Exception e) {
            System.err.println("Error checking account: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("1 -> Create account");
            System.out.println("2 -> Get accounts");
            System.out.println("3 -> Account exists?");
            System.out.println("enter choice");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> openAccount();
                case 2 -> getAccount();
                case 3 -> accountExists();
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
