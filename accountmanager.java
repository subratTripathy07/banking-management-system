import java.sql.*;
import java.util.Scanner;

public class accountmanager {

    static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    static final String USER = "root";
    static final String PASSWORD = "subrat7894";

    static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //  ACCOUNT MANAGER

    // check_balance()
    static double check_balance(String email) {
        String sql = "SELECT balance FROM accounts WHERE email=?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getDouble("balance");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // credit_money()
    static void credit_money(String email, double amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE email=?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Amount credited successfully ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // debit_money()
    static void debit_money(String email, double amount) {
        double balance = check_balance(email);

        if (balance < amount) {
            System.out.println("Insufficient balance ");
            return;
        }

        String sql = "UPDATE accounts SET balance = balance - ? WHERE email=?";
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Amount debited successfully ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // transfer_money()
    static void transfer_money(String fromEmail, String toEmail, double amount) {

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            double senderBalance = check_balance(fromEmail);
            if (senderBalance < amount) {
                System.out.println("Insufficient balance ");
                return;
            }

            PreparedStatement debit = con.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE email=?");
            PreparedStatement credit = con.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE email=?");

            debit.setDouble(1, amount);
            debit.setString(2, fromEmail);

            credit.setDouble(1, amount);
            credit.setString(2, toEmail);

            debit.executeUpdate();
            credit.executeUpdate();

            con.commit();
            System.out.println("Transfer successful ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  MAIN (Testing) 
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Check Balance");
        System.out.println("2. Credit Money");
        System.out.println("3. Debit Money");
        System.out.println("4. Transfer Money");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Your Email: ");
        String email = sc.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Balance: ₹" + check_balance(email));
                break;

            case 2:
                System.out.print("Amount: ");
                credit_money(email, sc.nextDouble());
                break;

            case 3:
                System.out.print("Amount: ");
                debit_money(email, sc.nextDouble());
                break;

            case 4:
                System.out.print("Receiver Email: ");
                String toEmail = sc.nextLine();
                System.out.print("Amount: ");
                double amt = sc.nextDouble();
                transfer_money(email, toEmail, amt);
                break;

            default:
                System.out.println("Invalid choice");
        }

        sc.close();
    }
}
