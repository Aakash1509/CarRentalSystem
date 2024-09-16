package Admin;

import java.util.HashMap;

public class AdminDetails {
    private String username;
    private String password;
    private HashMap<String, String> adminCredentials = new HashMap<>();

    public AdminDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void registerAdmin(String username, String password) {
//        Admin newAdmin = new Admin(username,password);
        adminCredentials.put(username, password); // Store username and password in HashMap
        System.out.println("Admin " + username + " registered successfully!");
    }

    public boolean loginAdmin(String username, String password) {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
            System.out.println("Login successful! Welcome, "+username);
//            AdminMenu adminMenu = new AdminMenu();

            //After successful login , redirect to AdminMenu.java
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.showAdminMenu();
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public HashMap<String, String> getAdminCredentials() {
        return adminCredentials;
    }
}
