//import java.util.Scanner;
//
//import admin.Administrator;
//import customers.CustomerDetails;
//
//public class Main
//{
//    public static void main(String[] args)
//    {
//        Scanner scanner = new Scanner(System.in);
//        Administrator adminReg = null;
//        CustomerDetails customerReg = null;
//        System.out.println("====== Welcome to Car Rental System ======");
//        int choice = 0;
//        do
//        {
//            System.out.println();
//
//            System.out.println("1. Register Admin");
//
//            System.out.println("2. Register Customer");
//
//            System.out.println("3. Login Admin");
//
//            System.out.println("4. Login Customer");
//
//            System.out.println("5. Exit");
//
//            System.out.print("Enter your choice: ");
//
//            try
//            {
//                choice = scanner.nextInt();
//
//                scanner.nextLine();
//
//                switch (choice)
//                {
//                    case 1:
//                        // Admin Registration
//                        System.out.println("Enter Admin Details:");
//
//                        System.out.print("Username: ");
//
//                        String adminUsername = scanner.nextLine();
//
//                        System.out.print("Password: ");
//
//                        String adminPassword = scanner.nextLine();
//
////                    adminReg = new AdminDetails(adminUsername, adminPassword);
//                        if (!adminUsername.isEmpty() && !adminPassword.isEmpty())
//                        {
//                            adminReg = Administrator.getInstance();
//
//                            adminReg.registerAdmin(adminUsername, adminPassword);
//                        }
//                        else
//                        {
//                            System.out.println("Don't keep any field empty...Please register again");
//                        }
//                        break;
//
//                    case 2:
//                        // Guest Registration
//                        System.out.println("Enter Customer Details:");
//
//                        System.out.print("Username: ");
//
//                        String customerUsername = scanner.nextLine();
//
//                        System.out.print("Password: ");
//
//                        String customerPassword = scanner.nextLine();
//
//                        System.out.print("Driver license number: ");
//
//                        String drivingLicenseNumber = scanner.nextLine();
//
//                        if (!customerUsername.isEmpty() && !customerPassword.isEmpty() && !drivingLicenseNumber.isEmpty())
//                        {
//                            customerReg = new CustomerDetails(customerUsername, customerPassword, drivingLicenseNumber);
//
//                            customerReg.registerCustomer(customerUsername, customerPassword);
//                        }
//                        else
//                        {
//                            System.out.println("Don't keep any field empty...Please register again");
//                        }
//                        break;
//
//                    case 3:
//                        // Admin Login
//                        System.out.print("Admin Username: ");
//
//                        String adminLoginUsername = scanner.nextLine();
//
//                        System.out.print("Admin Password: ");
//
//                        String adminLoginPassword = scanner.nextLine();
////                        AdminLogin adminLogin = new AdminLogin(adminReg.getAdminCredentials());
//                        try
//                        {
//                            if (!adminLoginUsername.isEmpty() && !adminLoginPassword.isEmpty())
//                            {
//                                adminReg.loginAdmin(adminLoginUsername, adminLoginPassword);
//                            }
//                            else
//                            {
//                                System.out.println("Don't keep any field empty...Please login again");
//                            }
//                        }
//                        catch (Exception e)
//                        {
//                            System.out.println("No username found, please register yourself first");
//                        }
//                        //AdminMenu admin_menu = new AdminMenu(); //Creating object of AdminMenu class to access admin menu
//                        //admin_menu.showAdminMenu();
//                        break;
//
//                    case 4:
//                        // Guest Login
//                        System.out.print("Customer Username: ");
//
//                        String customerLoginUsername = scanner.nextLine();
//
//                        System.out.print("Customer Password: ");
//
//                        String customerLoginPassword = scanner.nextLine();
//                        //GuestLogin guestLogin = new GuestLogin(guestReg.getGuestCredentials());
//                        try
//                        {
//                            if (!customerLoginUsername.isEmpty() && !customerLoginPassword.isEmpty())
//                            {
//                                customerReg.loginCustomer(customerLoginUsername, customerLoginPassword);
//                            }
//                            else
//                            {
//                                System.out.println("Don't keep any field empty...Please login again");
//                            }
//                        }
//                        catch (Exception e)
//                        {
//                            System.out.println("No username found, please register yourself first");
//                        }
//
//                        //GuestMenu guest_menu = new GuestMenu();
//                        //guest_menu.showGuestMenu();
//                        break;
//
//                    case 5:
//                        System.out.println("Exiting the system.");
//
//                        break;
//
//                    default:
//                        System.out.println("Invalid choice. Please try again.");
//                }
//            }
//            catch (Exception e)
//            {
//                System.out.println("Invalid choice. Please enter a valid number for choice");
//
//                scanner.nextLine(); //To clear the buffer
//            }
//
//        } while (choice!=5); // Loop continues until the user chooses to exit (option 5).
//    }
//}