package system;

import java.io.BufferedReader;

import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.List;

//Created abstract class as ArrayList of cars will be shared by both admin and user and menu method can also be override
public abstract class CarRentalSystem
{
    //Made it static so both admin and customers share it
    public static final List<CarDetails> cars = new ArrayList<>();

    //Initializer block (cars.add() method should not be outside any method or block)
    static
    {
        CarDetails car1 = new CarDetails("C001", "Ford", "Mustang", 1500);

        CarDetails car2 = new CarDetails("C002", "Mahindra", "Thar", 2000);

        CarDetails car3 = new CarDetails("C003", "Tata", "Nexon", 1500);

        CarDetails car4 = new CarDetails("C004", "Hyundai", "Creta", 1800);

        CarDetails car5 = new CarDetails("C005", "Toyota", "Fortuner", 2200);

        CarDetails car6 = new CarDetails("C006", "Maruti", "Swift", 1300);

        CarDetails car7 = new CarDetails("C007", "Honda", "City", 1400);

        CarDetails car8 = new CarDetails("C008", "Kia", "Seltos", 1600);

        CarDetails car9 = new CarDetails("C009", "Jeep", "Compass", 1900);

        CarDetails car10 = new CarDetails("C010", "BMW", "X5", 3000);

        cars.add(car1);

        cars.add(car2);

        cars.add(car3);

        cars.add(car4);

        cars.add(car5);

        cars.add(car6);

        cars.add(car7);

        cars.add(car8);

        cars.add(car9);

        cars.add(car10);
    }

    public void viewAvailableCars(PrintWriter writeData)
    {
        writeData.println("\n============= Available Cars in inventory are ==============");

        boolean hasAvailableCars = false;

        synchronized (cars) //Synchronized so that when one thread is iterating other thread doesn't modify
        {
            for (CarDetails car : cars)
            {
//                try
//                {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e)
//                {
//                    throw new RuntimeException(e);
//                }
                if (car.isAvailable())
                {
                    hasAvailableCars = true;

                    writeData.println(car.getCarId() + " - " + car.getCarBrand() + " " + car.getCarModel() + " " + car.getBasePricePerDay() + " ($/day)");
                }
            }

            if(!hasAvailableCars)
            {
                writeData.println("No cars available at the moment");
            }
            writeData.flush();
        }
    }

    public abstract void showDashboard(PrintWriter writeData, BufferedReader readData) throws Exception;
}
