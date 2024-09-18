package system;

import java.util.ArrayList;
import java.util.List;

//Created abstract class as ArrayList of cars will be shared by both admin and user and menu method can also be override
public abstract class CarRentalSystem {
    protected List<CarDetails>cars = new ArrayList<>();

    //Instance Initializer block (cars.add() method should not be outside any method or block)
    {
        CarDetails car1 = new CarDetails("C001", "Ford", "Mustang", 1500);
        CarDetails car2 = new CarDetails("C002", "Mahindra", "Thar", 2000);
        CarDetails car3 = new CarDetails("C003", "Tata", "Nexon", 1500);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
    }
    public void viewAvailableCars(){
        System.out.println("\n============= Available Cars in inventory are ==============");
        for(CarDetails car : cars){
            if(car.isAvailable()){
                System.out.println(car.getCarId()+" - "+car.getCarBrand()+" "+car.getCarModel()+ " "+car.getBasePricePerDay()+" (in $)");
            }
        }
    }
    public abstract void showMenu();
}
