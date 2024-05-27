import classes.*;
import exceptions.InvalidDataException;
import services.*;
import repositories.*;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

import classes.*;
import exceptions.InvalidDataException;
import repositories.*;
import services.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static AthleteService athleteService = AthleteService.getInstance(new AthleteRepository());
    private static CustomerService customerService = CustomerService.getInstance(new CustomerRepository());
    private static FitnessChallengeService fitnessChallengeService = FitnessChallengeService.getInstance(new FitnessChallengeRepository());
    private static GymService gymService = GymService.getInstance(new GymRepository());
    private static GymMembershipService gymMembershipService = GymMembershipService.getInstance(new GymMembershipRepository());
    private static OrderService orderService = OrderService.getInstance(new OrderRepository());
    private static LocationService locationService = LocationService.getInstance(new LocationRepository());
    private static EventService eventService = EventService.getInstance(new EventRepository());
    private static ProductService productService = ProductService.getInstance(new ProductRepository());
    private static CustomerMembershipService cmService = CustomerMembershipService.getInstance(new CustomerMembershipRepository());
    private static OrderProductService opService = OrderProductService.getInstance(new OrderProductRepository());
    private static ChallengeCustomerService ccService = ChallengeCustomerService.getInstance(new ChallengeCustomerRepository());
    private static PersonService personService = PersonService.getInstance(new PersonRepository());
    public static void main(String[] args) {

        while (true) {
            System.out.println("1. CRUD");
            System.out.println("2. Methods");
            System.out.println("0. Exit");

            int classChoice = Integer.parseInt(scanner.nextLine());

            if (classChoice == 0) break;

            if (classChoice == 1) {
                CRUD();
            } else {
                otherMethods();
            }
        }
    }

    public static void CRUD() {
        System.out.println("1. Athlete");
        System.out.println("2. Customer");
        System.out.println("3. FitnessChallenge");
        System.out.println("4. Gym");
        System.out.println("5. GymMembership");
        System.out.println("6. Order");
        System.out.println("7. Location");
        System.out.println("8. Event");
        System.out.println("9. Product");

        int classChoice = Integer.parseInt(scanner.nextLine());
        if (classChoice == 0) {
            return;
        }


        System.out.println("Select an operation:");
        int operationChoice = 1;

        System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        operationChoice = Integer.parseInt(scanner.nextLine());

        try {
            switch (classChoice) {
                case 1:
                    handleAthleteOperations(operationChoice);
                    break;
                case 2:
                    handleCustomerOperations(operationChoice);
                    break;
                case 3:
                    handleFitnessChallengeOperations(operationChoice);
                    break;
                case 4:
                    handleGymOperations(operationChoice);
                    break;
                case 5:
                    handleGymMembershipOperations(operationChoice);
                    break;
                case 6:
                    handleOrderOperations(operationChoice);
                    break;
                case 7:
                    handleLocationOperations(operationChoice);
                    break;
                case 8:
                    handleEventOperations(operationChoice);
                    break;
                case 9:
                    handleProductOperations(operationChoice);
                    break;

                default:
                    System.out.println("Invalid class choice.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static void otherMethods() {
        System.out.println("1. Buy products (OrderProduct, CustomerMembership)");
        System.out.println("2. Complete challenge (ChallengeCustomer)");
        System.out.println("3. Find gyms with a membership price inside an interval");
        System.out.println("4. Change membership prices of gym");
        System.out.println("5. Get most frequent locations for GymLion events");
        System.out.println("6. Delete expensive athletes");
        System.out.println("7. Get customer with most orders");
        System.out.println("8. Reward customer with most orders");
        System.out.println("9. Delete all events that already took place");
        System.out.println("10. Get all events that took place in a period of time in a city selected");
        System.out.println("11. Find all users that completed a challenge");
        System.out.println("12. Upgrade points of a challenge that was completed less than a number n of times");
        System.out.println("13. Show customers with balance over threshold");
        System.out.println("14. Increase salary of popular athletes");
        System.out.println("15. Show people sorted by age");
        System.out.println("16. Get number of memberships of each gym");
        System.out.println("17. Get challenges sorted by points");

        int classChoice = Integer.parseInt(scanner.nextLine());
        if (classChoice == 0) {
            return;
        }

        try {
            switch (classChoice) {
                case 1:
                    handleOrder();
                    break;
                case 2:
                    completeChallenge();
                    break;
                case 3:
                    findGymBasedOnMembershipPrices();
                    break;
                case 4:
                    changeMembershipPrices();
                    break;
                case 5:
                    getMostFrequentEventLocations();
                    break;
                case 6:
                    deleteExpensiveAthletes();
                    break;
                case 7:
                    getCustomerWithMostOrders();
                    break;
                case 8:
                    rewardCustomerWithMostOrder();
                    break;
                case 9:
                    deleteOldEvents();
                    break;
                case 10:
                    getAllEventsFromAPeriodAndACity();
                    break;
                case 11:
                    findUsersThatCompletedChallenge();
                    break;
                case 12:
                    upgradeChallengeCompletedLessThanNTimes();
                    break;
                case 13:
                    showCustomersWithBalanceOverThreshold();
                    break;
                case 14:
                    increaseSalaryOfPopularAthletes();
                    break;
                case 15:
                    showPeopleSortedByAge();
                    break;
                case 16:
                    getNumberOfMembershipsOfGyms();
                    break;
                case 17:
                    getChallengesSortedByPoints();
                    break;

                default:
                    System.out.println("Invalid class choice.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void handleAthleteOperations(int operation) throws InvalidDataException, SQLException {
        switch (operation) {
            case 1:
                System.out.println("Name:");
                String name = scanner.nextLine();
                System.out.println("Email:");
                String email = scanner.nextLine();
                System.out.println("Phone:");
                String phone = scanner.nextLine();
                System.out.println("Address:");
                String address = scanner.nextLine();
                System.out.println("Age:");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Salary:");
                double salary = Double.parseDouble(scanner.nextLine());
                System.out.println("Enter bonus per 10,000 likes:");
                int bonus = Integer.parseInt(scanner.nextLine());
                System.out.println("Followers:");
                int followers = Integer.parseInt(scanner.nextLine());
                athleteService.registerNewEntity(name, email, phone, address, age, salary, bonus, followers);
                break;
            case 2:
                System.out.println("Enter athlete ID:");
                int id = Integer.parseInt(scanner.nextLine());
                Athlete athlete = athleteService.get(id);
                System.out.println(athlete);
                break;
            case 3:
                System.out.println("Id of athlete you want to modify:");
                int updateId = Integer.parseInt(scanner.nextLine());
                athleteService.update(updateId);
                break;
            case 4:
                System.out.println("Enter athlete ID to delete:");
                int deleteId = Integer.parseInt(scanner.nextLine());
                athleteService.delete(deleteId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleCustomerOperations(int operation) throws InvalidDataException, SQLException {
        switch (operation) {
            case 1:
                System.out.println("Name:");
                String name = scanner.nextLine();
                System.out.println("Email:");
                String email = scanner.nextLine();
                System.out.println("Phone:");
                String phone = scanner.nextLine();
                System.out.println("Address:");
                String address = scanner.nextLine();
                System.out.println("Enter age:");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter balance:");
                Integer balance = Integer.parseInt(scanner.nextLine());
                customerService.registerNewEntity(name, email, phone, address, age, balance);
                break;
            case 2:
                System.out.println("Enter customer ID:");
                int id = Integer.parseInt(scanner.nextLine());
                Customer customer = customerService.get(id);
                System.out.println(customer);
                break;
            case 3:
                System.out.println("Enter customer ID to update:");
                int updateId = Integer.parseInt(scanner.nextLine());
                customerService.update(updateId);
                break;
            case 4:
                System.out.println("Enter customer ID to delete:");
                int deleteId = Integer.parseInt(scanner.nextLine());
                customerService.delete(deleteId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleFitnessChallengeOperations(int operation) throws InvalidDataException, SQLException {
        switch (operation) {
            case 1:
                System.out.println("Enter name:");
                String name = scanner.nextLine();
                System.out.println("Enter description:");
                String description = scanner.nextLine();
                System.out.println("Enter points:");
                int points = Integer.parseInt(scanner.nextLine());
                fitnessChallengeService.registerNewEntity(name, description, points);
                break;
            case 2:
                System.out.println("Enter challenge ID:");
                int id = Integer.parseInt(scanner.nextLine());
                FitnessChallenge challenge = fitnessChallengeService.get(id);
                System.out.println(challenge);
                break;
            case 3:
                System.out.println("Enter challenge ID to update:");
                int updateId = Integer.parseInt(scanner.nextLine());
                fitnessChallengeService.update(updateId);
                break;
            case 4:
                System.out.println("Enter challenge ID to delete:");
                int deleteId = Integer.parseInt(scanner.nextLine());
                fitnessChallengeService.delete(deleteId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleGymOperations(int operation) throws InvalidDataException, SQLException {
        switch (operation) {
            case 1:
                System.out.println("Enter name:");
                String name = scanner.nextLine();
                System.out.println("Enter description:");
                String description = scanner.nextLine();
                System.out.println("Enter capacity:");
                int capacity = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter location ID:");
                int locationId = Integer.parseInt(scanner.nextLine());
                gymService.registerNewEntity(name, description, capacity, locationId);
                break;
            case 2:
                System.out.println("Enter gym ID:");
                int id = Integer.parseInt(scanner.nextLine());
                Gym gym = gymService.get(id);
                System.out.println(gym);
                break;
            case 3:
                System.out.println("Enter gym ID to update:");
                int updateId = Integer.parseInt(scanner.nextLine());
                gymService.update(updateId);
                break;
            case 4:
                System.out.println("Enter gym ID to delete:");
                int deleteId = Integer.parseInt(scanner.nextLine());
                gymService.delete(deleteId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleGymMembershipOperations(int operation) throws InvalidDataException, SQLException {
        switch (operation) {
            case 1:
                System.out.println("Enter gym ID:");
                int gymId = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter price:");
                double price = Double.parseDouble(scanner.nextLine());
                System.out.println("Enter duration in months:");
                int duration = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter code:");
                String code = scanner.nextLine();
                gymMembershipService.registerNewEntity(gymId, price, duration, code);
                break;
            case 2:
                System.out.println("Enter membership ID:");
                int id = Integer.parseInt(scanner.nextLine());
                GymMembership membership = gymMembershipService.get(id);
                System.out.println(membership);
                break;
            case 3:
                System.out.println("Enter membership ID to update:");
                int updateId = Integer.parseInt(scanner.nextLine());
                gymMembershipService.update(updateId);
                break;
            case 4:
                System.out.println("Enter membership ID to delete:");
                int deleteId = Integer.parseInt(scanner.nextLine());
                gymMembershipService.delete(deleteId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleOrderOperations(int operation) throws InvalidDataException, SQLException {
        switch (operation) {
            case 1:
                System.out.println("Enter customer ID:");
                int customerId = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter date (YYYY-MM-DD):");
                LocalDate date = LocalDate.parse(scanner.nextLine());
                System.out.println("Enter price:");
                double price = Double.parseDouble(scanner.nextLine());
                orderService.registerNewEntity(customerId, date, price);
                break;
            case 2:
                System.out.println("Enter order ID:");
                int id = Integer.parseInt(scanner.nextLine());
                Order order = orderService.get(id);
                System.out.println(order);
                break;
            case 3:
                System.out.println("Enter order ID to update:");
                int updateId = Integer.parseInt(scanner.nextLine());
                orderService.update(updateId);
                break;
            case 4:
                System.out.println("Enter order ID to delete:");
                int deleteId = Integer.parseInt(scanner.nextLine());
                orderService.delete(deleteId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleLocationOperations(int location) throws InvalidDataException, SQLException {
        switch (location) {
            case 1:
                System.out.println("Country name:");
                String countryName = scanner.nextLine();
                System.out.println("City name:");
                String cityName = scanner.nextLine();
                System.out.println("Latitude:");
                double latitude = Double.parseDouble(scanner.nextLine());
                System.out.println("Longitude:");
                double longitude = Double.parseDouble(scanner.nextLine());
                locationService.registerNewEntity(countryName, cityName, latitude, longitude);
                break;
            case 2:
                System.out.println("Location ID:");
                int locationId = Integer.parseInt(scanner.nextLine());
                Location loc = locationService.get(locationId);
                System.out.println(loc);
                break;
            case 3:
                System.out.println("Location ID to update:");
                int updateLocationId = Integer.parseInt(scanner.nextLine());
                locationService.update(updateLocationId);
                break;
            case 4:
                System.out.println("Location ID to delete:");
                int deleteLocationId = Integer.parseInt(scanner.nextLine());
                locationService.delete(deleteLocationId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleEventOperations(int event) throws InvalidDataException, SQLException {
        switch (event) {
            case 1:
                System.out.println("Start date:");
                Date startDate = Date.valueOf(scanner.nextLine());
                System.out.println("End date:");
                Date endDate = Date.valueOf(scanner.nextLine());
                System.out.println("Name:");
                String name = scanner.nextLine();
                System.out.println("Description:");
                String description = scanner.nextLine();
                System.out.println("Capacity:");
                Integer capacity = Integer.parseInt(scanner.nextLine());
                System.out.println("Location ID:");
                Integer locationId = Integer.parseInt(scanner.nextLine());
                eventService.registerNewEntity(startDate.toLocalDate(), endDate.toLocalDate(), name, description, capacity, locationId);
                break;
            case 2:
                System.out.println("Event ID:");
                int eventId = Integer.parseInt(scanner.nextLine());
                Event ev = eventService.get(eventId);
                System.out.println(ev);
                break;
            case 3:
                System.out.println("Event ID to update:");
                int updateEventId = Integer.parseInt(scanner.nextLine());
                eventService.update(updateEventId);
                break;
            case 4:
                System.out.println("Event ID to delete:");
                int deleteEventId = Integer.parseInt(scanner.nextLine());
                eventService.delete(deleteEventId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleProductOperations(int product) throws InvalidDataException, SQLException {
        switch (product) {
            case 1:
                System.out.println("Code:");
                String code = scanner.nextLine();
                System.out.println("Price:");
                Double price = Double.parseDouble(scanner.nextLine());
                productService.registerNewEntity(price, code);
                break;
            case 2:
                System.out.println("Price ID:");
                int productId = Integer.parseInt(scanner.nextLine());
                Product prod = productService.get(productId);
                System.out.println(prod);
                break;
            case 3:
                System.out.println("Product ID to update:");
                int updateProductId = Integer.parseInt(scanner.nextLine());
                productService.update(updateProductId);
                break;
            case 4:
                System.out.println("Product ID to delete:");
                int deleteProductId = Integer.parseInt(scanner.nextLine());
                productService.delete(deleteProductId);
                break;
            default:
                System.out.println("Invalid operation choice.");
        }
    }

    private static void handleOrder() throws InvalidDataException, SQLException {
        System.out.println("We will be creating an order!");
        System.out.println("First, we need to know the id of the customer:");
        int customerId = Integer.parseInt(scanner.nextLine());
        System.out.println("Now, we'd like you to select a few products:");

        try {
            List<Product> products = productService.getAllProducts();
            List<Integer> selectedProductsIds = new ArrayList<>();
            double totalPrice = 0;
            for (Product product : products) {
                boolean is_membership = false;

                if (product instanceof GymMembership) {
                    GymMembership gymMembership = (GymMembership) product;
                    System.out.println(gymMembership);
                    is_membership = true;
                } else {
                    System.out.println(product);
                }

                System.out.println("Do you wish to buy this product? 1 for yes and 0 for no:");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    selectedProductsIds.add(product.getId());
                    totalPrice += product.getPrice();
                    if (is_membership) {
                        cmService.addCustomerMembership(product.getId(), customerId);
                    }
                }
            }

            int idOfNewOrder = orderService.registerNewEntity(customerId, LocalDate.now(), totalPrice);
            for (int id : selectedProductsIds) {
                opService.addOrderProduct( idOfNewOrder, id );
            }

            System.out.println("A new order has been created with your products, here are the details:");
            System.out.println("Total price:" + totalPrice);

            Order newOrder = orderService.get(idOfNewOrder);
            Customer customer = customerService.get(newOrder.getCustomerId());

            System.out.println("Customer:" + customer.getName() + ", " + customer.getEmail() + ", " + customer.getAddress());

            List<Product> boughtProducts = opService.getProductsByOrderId(idOfNewOrder);

            for (Product product : boughtProducts) {
                System.out.println("Product name:" + product.getCode());
                System.out.println("Product price:" + product.getPrice());
            }

            System.out.println("Write anything to continue");
            String x = scanner.nextLine();
        } catch (SQLException e) {
            throw new SQLException("Couldn't create order.", e);
        }
    }

    private static void completeChallenge() throws InvalidDataException, SQLException {
        System.out.println("We will be completing a challenge!");
        System.out.println("First, we need to know the id of the customer:");
        int customerId = Integer.parseInt(scanner.nextLine());
        System.out.println("Now, we'd like you to select the challenge:");

        try {
            List<FitnessChallenge> challenges = fitnessChallengeService.getAllChallenges();
            List<Integer> selectedChallengeIds = new ArrayList<>();
            double totalPoints = 0;
            for (FitnessChallenge challenge : challenges) {
                System.out.println(challenge);

                System.out.println("Do you wish to complete this challenge?");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    selectedChallengeIds.add(challenge.getId());
                    totalPoints += challenge.getPoints();
                }
            }

            for (int id : selectedChallengeIds) {
                ccService.addChallengeCustomer(id, customerId);
            }

            System.out.println("The challenges have been added. Here are the challenges this customer has completed:");

            List<FitnessChallenge> completedChallenges = ccService.getChallengesByCustomerId(customerId);

            for (FitnessChallenge challenge : completedChallenges) {
                System.out.println(challenge);
            }

            System.out.println("Write anything to continue");
            String x = scanner.nextLine();
        } catch (SQLException e) {
            throw new SQLException("Couldn't complete challenges.", e);
        }
    }

    private static void findGymBasedOnMembershipPrices() throws InvalidDataException, SQLException{

        System.out.println("Starting price:");
        double startPrice = Double.parseDouble(scanner.nextLine());
        System.out.println("Ending price:");
        double endPrice = Double.parseDouble(scanner.nextLine());

        List<Gym> gyms = gymService.findGymBasedOnMembershipPrices(startPrice, endPrice);

        gyms.forEach(gym -> System.out.println(gym.getName()));
    }

    private static void changeMembershipPrices () throws InvalidDataException, SQLException {
        System.out.println("Name of gym:");
        String gymName = scanner.nextLine();
        System.out.println("Percentage:");
        Integer percentage = Integer.parseInt(scanner.nextLine());

        gymService.changeMembershipPrices(gymName, percentage);

        System.out.println("Membership prices updated successfully.");
    }

    private static void showPeopleSortedByAge() {
        personService.showPeopleSortedByAge().forEach(System.out::println);
    }

    private static void increaseSalaryOfPopularAthletes() throws InvalidDataException, SQLException {
        System.out.println("Number of followers:");
        Integer followers = Integer.parseInt(scanner.nextLine());
        System.out.println("Percentage:");
        Double percentage = Double.parseDouble(scanner.nextLine());
        athleteService.increaseSalaryOfPopularAthletes(followers, percentage);
    }

    private static void showCustomersWithBalanceOverThreshold() throws SQLException, InvalidDataException {
        System.out.println("Threshold:");
        Integer threshold = Integer.parseInt(scanner.nextLine());

        customerService.showCustomersWithBalanceOverThreshold(threshold);

    }

    private static void upgradeChallengeCompletedLessThanNTimes() throws InvalidDataException, SQLException {
        System.out.println("Number:");
        Integer n = Integer.parseInt(scanner.nextLine());
        System.out.println("Points:");
        Integer points = Integer.parseInt(scanner.nextLine());

        fitnessChallengeService.upgradeChallengeCompletedLessThanNTimes(n, points);
    }

    private static void findUsersThatCompletedChallenge() throws SQLException {
        System.out.println("Give id of challenge:");
        Integer challengeId = Integer.parseInt(scanner.nextLine());

        List<Customer> customers = customerService.getCustomersThatCompletedChallenge(challengeId);

        customers.forEach(System.out::println);
    }

    private static void getAllEventsFromAPeriodAndACity() throws SQLException, InvalidDataException {
        System.out.println("Start date:");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.println("End date:");
        Date endDate = Date.valueOf(scanner.nextLine());
        System.out.println("City:");
        String city = scanner.nextLine();
        List<Event> events = eventService.getAllEventsFromAPeriodAndACity(startDate, endDate, city);

        events.forEach(System.out::println);
    }

    private static void deleteOldEvents() throws SQLException {
        eventService.deleteOldEvents();

        System.out.println("Old events were deleted successfully!");
    }

    private static void rewardCustomerWithMostOrder() throws SQLException, InvalidDataException {
        System.out.println("Reward:");
        Double reward = Double.parseDouble(scanner.nextLine());
        Customer customer = customerService.getCustomerWithMostOrders();

        customerService.rewardCustomerWithMostOrders(customer.getId(), reward);
    }

    private static void getCustomerWithMostOrders() throws SQLException {
        System.out.println(customerService.getCustomerWithMostOrders());
    }

    private static void deleteExpensiveAthletes() throws InvalidDataException, SQLException {
        System.out.println("Percentage:");
        Integer percents = Integer.parseInt(scanner.nextLine());
        athleteService.deleteExpensiveAthletes(percents);

    }

    private static void getMostFrequentEventLocations() throws SQLException {
        List<Location> locations = locationService.getMostFrequentEventLocations();

        locations.forEach(System.out::println);
    }

    private static void getNumberOfMembershipsOfGyms() throws SQLException {
        Map<String, Integer> gymMemberships = gymService.getGymMembershipCounts();

        for (Map.Entry<String, Integer> entry : gymMemberships.entrySet()) {
            System.out.println("Gym: " + entry.getKey() + ", Memberships: " + entry.getValue());
        }

    }

    private static void getChallengesSortedByPoints() throws SQLException {
        List<FitnessChallenge> challenges = fitnessChallengeService.getAllChallengesSortedByPoints();
        challenges.forEach(System.out::println);
    }

    private static void handleSQLException(SQLException e) {
        System.err.println("A database error occurred: " + e.getMessage() + ". Please try again later or contact support if the issue persists.");
    }
}























































