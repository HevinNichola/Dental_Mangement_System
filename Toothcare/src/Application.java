
import java.util.*;

public class Application {
    private Signup signup;

    private Login login;
    private Schedule schedule;

    FrontOfficeOperator frontOfficeOperator = new FrontOfficeOperator();
    
    public Application(){
        signup = Signup.getInstance();
        login = new Login(signup);
        schedule = new Schedule();

    }
    
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Welcome to the Dental System");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice){
                case 1:
                    login();
                    break;
                    
                case 2:
                    signup();
                    break;
                    
                case 3:
                    exit();
            }
            
            if(login.isLoggedIn()){
                mainMenu(scanner);
                
                System.out.println();
                System.out.print("Do you want to logout? (1 for Yes, 2 for No): ");
                int logoutChoice = scanner.nextInt();
                scanner.nextLine();
                
                if(logoutChoice == 1){
                    login.logout();
                    System.out.println("User logged out successfully.");
                } else {
                    System.out.println("Back to main menu");
                    System.out.println();
                    mainMenu(scanner);
                }
            }
            System.out.println();
        }
    }
    
    private void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to the Login page!");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if(login.authenticateUser(username, password)){
            System.out.println();
            System.out.println("Login successful!");
            System.out.println();
        } else{
            System.out.println("Incorrect username or password. Try again.");
        }
    }
    
    private void signup() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to the Signup page!");
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        
        signup.registerUser(newUsername, newPassword);
    }
    
    private void logout(){
        login.logout();
    }
    
    private void exit(){
        System.out.println("Exiting the Dental System. Goodbye!");
        System.exit(0);
    }
    
    private void mainMenu(Scanner scanner){
        while(true){
            System.out.println("Welcome to the Dental System");
            System.out.println("1. Appointment Category");
            System.out.println("2. Billing Category");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            
            int mainChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch(mainChoice){
                case 1:
                    frontOfficeOperator.appointmentCategory(scanner);
                    break;
                    
                case 2:
                    invoiceMenu(scanner);
                    break;
                    
                case 3:
                    logout();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void invoiceMenu(Scanner scanner){
        while (true) {
            System.out.println();
            System.out.println("Payment / Invoice Menu");
            System.out.println("1 - Generate Invoice");
            System.out.print("Choose an option (Type 0 to go to previous Menu): ");

            int billingChoice = scanner.nextInt();
            scanner.nextLine();

            if(billingChoice == 0){
                return;
            }

            switch(billingChoice) {
                case 1:
                    frontOfficeOperator.generateInvoice(scanner);
                    break;

                default:
                    System.out.println("Invalid Selection. Please enter a valid option.");
            }
        }
    }

    public static void main(String[] args){
        new Application().run();
    }
}
