package Admin;

import java.util.*;

public class AdminMenu {
    private Scanner scanner = new Scanner(System.in);
    private List<CarDetails>cars = new ArrayList<>();
//    AdminMenu adminMenu = new AdminMenu();

    /*
    Optional to add in ArrayList via function
    public void addCar(CarDetails car){
        cars.add(car);
    }
     */

    public void addCar(){
        System.out.println("\nFill up following details to add a car");
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        System.out.print("Brand: ");
        String carBrand = scanner.nextLine();
        System.out.print("Model: ");
        String carModel = scanner.nextLine();
        System.out.print("Base Price Per Day: ");
        double basePricePerDay = scanner.nextDouble();
        CarDetails car = new CarDetails(carId,carBrand,carModel,basePricePerDay);
        cars.add(car);
        System.out.println("\nCar was successfully added");
    }

    public void removeCar(){
        System.out.println("\nEnter the Id of the car you want to remove : ");
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();

        //Cannot use forEach loop because it will throw ConcurrentModificationException , so need to use iterator
        Iterator<CarDetails> iterator = cars.iterator(); // Get the iterator
        boolean carRemoved = false;

        while (iterator.hasNext()) {
            CarDetails car = iterator.next(); // Get the next car
            // Check if the car is available and matches the ID
            if (car.isAvailable() && Objects.equals(car.getCarId(), carId)) {
                iterator.remove(); // Use iterator's remove method to safely remove the car
                System.out.println("\nCar with ID " + carId + " removed successfully.");
                carRemoved = true;
                break;
            }
        }

        if (!carRemoved) {
            System.out.println("No available car found with ID: " + carId);
        }
    }

    public void viewAvailableCars(){
        System.out.println("\n============= Available Cars in inventory are ==============");
        for(CarDetails car : cars){
            if(car.isAvailable()){
                System.out.println(car.getCarId()+" - "+car.getCarBrand()+" "+car.getCarModel());
            }
        }
    }

    public void viewRentedCars(){
        System.out.println("\n============= Rented Cars are ==============");
        boolean flag = false;
        for(CarDetails car : cars){
            if(!car.isAvailable()){
                flag = true;
                System.out.println(car.getCarId()+" - "+car.getCarBrand()+" "+car.getCarModel());
            }
        }
        if(!flag){
            System.out.println("\nThere are no rented cars");
        }
    }

    public void updateCarDetails(){
        System.out.println("\nEnter the ID of the car you want to update : ");
        System.out.print("Car ID : ");
        String toUpdateCarId= scanner.nextLine();
        boolean carFound = false;
        for(CarDetails car : cars){
            if(Objects.equals(car.getCarId(), toUpdateCarId)){
                carFound = true;
                //Displaying current car details
                System.out.println("Current details of the car:");
                System.out.println("Car Brand: " + car.getCarBrand());
                System.out.println("Car Model: " + car.getCarModel());
                System.out.println("Car Rent per day: " + car.getBasePricePerDay());

                // Take new details as input
                System.out.println("\nEnter new details (leave blank to keep current value):");

                System.out.print("New Car Brand : ");
                String newCarBrand = scanner.nextLine();
                if (!newCarBrand.isEmpty()) {
                    car.setCarBrand(newCarBrand);
                }

                System.out.print("New Car Model : ");
                String newCarModel = scanner.nextLine();
                if (!newCarModel.isEmpty()) {
                    car.setCarModel(newCarModel);
                }

                System.out.println("New base price per day : ");
                String newRentPrice = scanner.nextLine();
                if (!newRentPrice.isEmpty()) {
                    try{
                        double newBasePrice = Double.parseDouble(newRentPrice);
                        car.setBasePricePerDay(newBasePrice);
                    }
                    catch (NumberFormatException e){
                        System.out.println("Invalid Rent value. Keeping current rent");
                    }

                }
                System.out.println("Car details updated successfully");
                break;
            }

        }
        if(!carFound){
            System.out.println("Car not found with ID: "+toUpdateCarId);
        }

    }

    public void showAdminMenu(){
        int choice;
        CarDetails car1 = new CarDetails("C001","Ford","Mustang",1500);
        CarDetails car2 = new CarDetails("C002","Mahindra","Thar",2000);
        CarDetails car3 = new CarDetails("C003","Tata","Nexon",1500);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
//        addCar(car1);
//        addCar(car2);
//        addCar(car3);
        do{
            System.out.println("\n====== Admin Menu ======");
            System.out.println("1. Add Car");
            System.out.println("2. Remove Car");
            System.out.println("3. Update Car Details");
            System.out.println("4. View Available Cars");
            System.out.println("5. View Rented Cars");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice){

                case 1:
                    addCar();
                    break;

                case 2:
                    removeCar();
                    break;

                case 3:
                    updateCarDetails();
                    break;

                case 4:
                    viewAvailableCars();
                    break;

                case 5:
                    viewRentedCars();
                    break;

                case 6:
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");

            }

        }while(choice!=6);

    }

}
