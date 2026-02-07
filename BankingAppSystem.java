import java.util.Scanner;

public class BankingAppSystem {
    
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "subrat7894";

    public static void main(String[] args) {
        // 1. Load Drivers & Setup Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("\n*** WELCOME TO BANKING SYSTEM ***");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        handleRegistration(scanner);
                        break;
                    case 2:
                        handleLogin(scanner);
                        break;
                    case 3:
                        System.out.println("Exiting System. Thank you!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            System.err.println("Critical System Error: " + e.getMessage());
        }
    }

    // Integrated logic to handle the flow between user and account classes
    private static void handleRegistration(Scanner sc) {
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (user.register(name, email, pass)) {
            System.out.println("Registration Successful! Now please open an account.");
            
        } else {
            System.out.println("Registration Failed. User might already exist.");
        }
    }

    private static void handleLogin(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (user.login(email, pass)) {
            System.out.println("Login Successful!");
            showAccountMenu(sc, email); // Pass user context to the manager
        } else {
            System.out.println("Invalid Credentials.");
        }
    }

    private static void showAccountMenu(Scanner sc, String email) {
        while (true) {
            System.out.println("\n--- ACCOUNT DASHBOARD (" + email + ") ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Credit Money");
            System.out.println("3. Debit Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Logout");
            System.out.print("Select Option: ");
            int opt = sc.nextInt();

            switch (opt) {
                case 1 -> System.out.println("Current Balance: ₹" + accountmanager.check_balance(email));
                case 2 -> {
                    System.out.print("Enter amount to credit: ");
                    accountmanager.credit_money(email, sc.nextDouble());
                }
                case 3 -> {
                    System.out.print("Enter amount to debit: ");
                    accountmanager.debit_money(email, sc.nextDouble());
                }
                case 4 -> {
                    System.out.print("Enter receiver email: ");
                    sc.nextLine(); // clear buffer
                    String toEmail = sc.nextLine();
                    System.out.print("Enter amount: ");
                    accountmanager.transfer_money(email, toEmail, sc.nextDouble());
                }
                case 5 -> { return; } // Goes back to main login menu
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
