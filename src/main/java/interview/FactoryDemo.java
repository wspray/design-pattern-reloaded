package interview;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FactoryDemo {

    interface Vehicle {
    }

    record Car() implements Vehicle {
    }

    record Bus() implements Vehicle {
    }

    @FunctionalInterface
    interface VehicleFactory {
        Vehicle create();

        default Supplier<Vehicle> createSupplier() {
            return this::create;
        }
    }

    public static List<Vehicle> createVehicles(VehicleFactory car, int num) {
        return Stream.generate(car.createSupplier()).limit(5).toList();
    }

    public static void main(String[] args) {
        List<Vehicle> vehicles = createVehicles(Car::new, 4);
        System.out.println(vehicles);
    }
}
