package system;

import java.util.*;

public class CarRentalSystem
{
    //Made it static so both admin and customers share it
//    public static final List<CarDetails> cars = new ArrayList<>();

    public static final Map<String,CarDetails> cars = new LinkedHashMap<>();

    //Initializer block (cars.add() method should not be outside any method or block)
    static
    {
        cars.put("C001",new CarDetails("Ford", "Mustang", 1500));

        cars.put("C002",new CarDetails("Mahindra", "Thar", 2000));

        cars.put("C003",new CarDetails("Tata", "Nexon", 1500));

        cars.put("C004",new CarDetails("Hyundai", "Creta", 1800));

        cars.put("C005",new CarDetails("Toyota", "Fortuner", 2200));

        cars.put("C006",new CarDetails("Maruti", "Swift", 1300));

        cars.put("C007",new CarDetails("Honda", "City", 1400));

        cars.put("C008",new CarDetails("Kia", "Seltos", 1600));

        cars.put("C009",new CarDetails("Jeep", "Compass", 1900));

        cars.put("C010",new CarDetails("BMW", "X5", 3000));
    }

    public static String viewAvailableCars()
    {
        StringBuilder carList = new StringBuilder();

        try
        {
            carList.append("\n============= Available Cars in inventory are ==============\n");

            boolean hasAvailableCars = false;

            synchronized (cars) //Synchronized so that when one thread is iterating other thread doesn't modify
            {
                for (Map.Entry<String,CarDetails> entry : cars.entrySet())
                {
                    var carID = entry.getKey();

                    var car = entry.getValue();
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

                        carList.append(carID).append(" - ").append(car.getCarBrand()).append(" ").append(car.getCarModel())
                                .append(" ").append(car.getBasePricePerDay()).append(" ($/day)\n");
                    }
                }

                if(!hasAvailableCars)
                {
                    carList.append("No cars available at the moment");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception occurred : "+e.getMessage());
        }
        return carList.toString();
    }

    //Function to check if car exist or not by car Id

    public static boolean carExists(String carId)
    {
        return cars.containsKey(carId);
    }

    public static CarDetails getCarById(String carId)
    {
        return cars.get(carId);
    }

    public static CarDetails isCarAvailable(String carId)
    {
        CarDetails car = getCarById(carId);

        if(car != null && car.isAvailable())
        {
            return car;
        }
        return null;
    }
}
