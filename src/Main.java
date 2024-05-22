////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import classes.*;
import exceptions.InvalidDataException;
import services.*;
import repositories.*;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import classes.*;
import exceptions.InvalidDataException;
import repositories.*;
import services.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static AthleteService athleteService = new AthleteService(new AthleteRepository());
    private static CustomerService customerService = new CustomerService(new CustomerRepository());
    private static FitnessChallengeService fitnessChallengeService = new FitnessChallengeService(new FitnessChallengeRepository());
    private static GymService gymService = new GymService(new GymRepository());
    private static GymMembershipService gymMembershipService = new GymMembershipService(new GymMembershipRepository());
    private static OrderService orderService = new OrderService(new OrderRepository());
    private static LocationService locationService = new LocationService(new LocationRepository());
    private static EventService eventService = new EventService(new EventRepository());
    private static ProductService productService = new ProductService(new ProductRepository());

    public static void main(String[] args) {
        while (true) {
            System.out.println("Select a class:");
            System.out.println("1. Athlete");
            System.out.println("2. Customer");
            System.out.println("3. FitnessChallenge");
            System.out.println("4. Gym");
            System.out.println("5. GymMembership");
            System.out.println("6. Order");
            System.out.println("7. Location");
            System.out.println("8. Event");
            System.out.println("9. Product");
            System.out.println("10. Buy products (OrderProduct, CustomerMembership)");
            System.out.println("11. Complete challenge (ChallengeCustomer)");
            System.out.println("0. Exit");

            int classChoice = Integer.parseInt(scanner.nextLine());

            if (classChoice == 0) {
                break;
            }

            System.out.println("Select an operation:");
            if (classChoice < 10) {
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
            } else {

            }

            int operationChoice = Integer.parseInt(scanner.nextLine());

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
                throw new RuntimeException(e);
            }
        }
    }

    private static void handleAthleteOperations(int operation) throws InvalidDataException {
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

    private static void handleCustomerOperations(int operation) throws InvalidDataException {
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

    private static void handleFitnessChallengeOperations(int operation) throws InvalidDataException {
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

    private static void handleGymOperations(int operation) throws InvalidDataException {
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

    private static void handleGymMembershipOperations(int operation) throws InvalidDataException {
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

    private static void handleOrderOperations(int operation) throws InvalidDataException {
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

    private static void handleLocationOperations(int location) throws InvalidDataException {
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

    private static void handleEventOperations(int event) throws InvalidDataException {
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
}

//public class Main {
//    private static Scanner scanner = new Scanner(System.in);
//    private static GymMembershipRepository membershipRepository = new GymMembershipRepository();
//    private static EventRepository eventRepository = new EventRepository();
//    private static OrderRepository orderRepository = new OrderRepository();
//    private static CustomerRepository customerRepository = new CustomerRepository();
//    private static AthleteRepository athleteRepository = new AthleteRepository();
//    private static GymRepository gymRepository = new GymRepository();
//    private static LocationRepository locationRepository = new LocationRepository();
//    private static FitnessChallengeRepository challengeRepository = new FitnessChallengeRepository();
//    private static PersonRepository personRepository = new PersonRepository();
//
//
//
//    private static AthleteService athleteService = new AthleteService(athleteRepository);
//    private static EventService eventService = new EventService(eventRepository);
//    private static CustomerService customerService = new CustomerService(customerRepository);
//    private static FitnessChallengeService challengeService = new FitnessChallengeService(challengeRepository);
//    private static GymService gymService = new GymService(gymRepository);
//    private static  LocationService locationService = new LocationService(locationRepository);
//    private static  PersonService personService = new PersonService(personRepository);
//
//    public static void addDataToRepositories () throws InvalidDataException {
//        locationService.registerNewEntity("Romania", "Campina", 39.5, 24.3);
//        locationService.registerNewEntity("USA", "LA", 344.4, 255.5);
//        locationService.registerNewEntity("USA", "NY", 2550, 254);
//        locationService.registerNewEntity("Spain", "Madrid", 15.5, 255.5);
//        locationService.registerNewEntity("Spain", "Barcelona", 10.4, 300.3);
//
//        gymService.registerNewEntity("name1", "desc1", 30, 1);
//        gymService.registerNewEntity("name2", "desc2", 54, 2);
//        gymService.registerNewEntity("name3", "desc3", 22, 3);
//
//        GymMembership membership1 = new GymMembership(1, 344.4, 12, "H2roEQ"),
//                membership2 = new GymMembership(2, 355.5, 6, "QPWRO"),
//                membership3 = new GymMembership(3, 2500, 12, "vwoWowv");
//        membershipRepository.add(membership1);
//        membershipRepository.add(membership2);
//        membershipRepository.add(membership3);
//
//        eventService.registerNewEntity(LocalDate.of(2022, 12, 1), LocalDate.of(2022, 12, 31), "ev1", "desc1", 30000, 1);
//        eventService.registerNewEntity(LocalDate.of(2020, 2, 1), LocalDate.of(2022, 3, 1), "ev2", "desc2", 30000, 2);
//        eventService.registerNewEntity(LocalDate.of(2022, 12, 1), LocalDate.now(), "ev5", "desc5 ", 100000, 2);
//
//        FitnessChallenge ch1 = new FitnessChallenge("ch1", "desc1", 30),
//                ch2 = new FitnessChallenge("ch2", "desc2", 100),
//                ch3 = new FitnessChallenge("ch3", "desc3", 23);
//        challengeRepository.add(ch1);
//        challengeRepository.add(ch2);
//        challengeRepository.add(ch3);
////        challengeService.registerNewEntity("ch1", "desc1", 30);
////        challengeService.registerNewEntity("ch2", "desc2", 100);
////        challengeService.registerNewEntity("ch3", "desc3", 23);
//
////        ArrayList<FitnessChallenge> chArr1 = new ArrayList<>(), chArr2 = new ArrayList<>();
////        chArr1.add(ch1); chArr2.add(ch2); chArr1.add(ch2);
//        Customer customer1 = new Customer("c1", "e1", "p1", "a1", 20, 39.9);
//        Customer customer2 = new Customer("c2", "e2", "p2", "a2", 25, 400);
//        customerService.registerNewEntity("c1", "e1", "p1", "a1", 20, 39.9);
//        customerService.registerNewEntity("c2", "e2", "p2", "a2", 25, 400);
//        Order o1 = new Order(2, LocalDate.now(), 399.9),
//              o2 = new Order(2, LocalDate.now(), 399.9);
//        orderRepository.add(o1); orderRepository.add(o2);
//
//        Athlete ath1 = new Athlete("n1", "e1", "p1", "a1", 20, 5, 1000, 2000),
//                ath2 = new Athlete("n2", "e2", "p2", "a2", 32, 5000000, 1000, 2000),
//                ath3 = new Athlete("n3", "e3", "p3", "a3", 55, 5000000, 500000, 2000);
//        athleteService.registerNewEntity("n1", "e1", "p1", "a1", 20, 5, 1000, 2000);
//        athleteService.registerNewEntity("n2", "e2", "p2", "a2", 32, 5000000, 1000, 2000);
//        athleteService.registerNewEntity("n3", "e3", "p3", "a3", 55, 5000000, 500000, 2000);
//
//        personRepository.add(ath1);
//        personRepository.add(customer1);
//        personRepository.add(ath2);
//        personRepository.add(customer2);
//        personRepository.add(ath3);
//    }
//    public static void main(String[] args) throws InvalidDataException {
//        addDataToRepositories();
//        System.out.println("Welcome to the CRUD Menu!");
//        System.out.println("Do you wish to perform CRUD operations? (yes/no): ");
//        String crudChoice = scanner.nextLine();
//
//        if (crudChoice.equalsIgnoreCase("yes")) {
//            System.out.println("Choose a model: Athlete, Customer, Event, Challenge, Gym, or Location");
//            String modelChoice = scanner.nextLine();
//
//            switch (modelChoice.toLowerCase()) {
//                case "athlete":
//                    handleAthleteCRUD();
//                    break;
//                case "customer":
//                    handleCustomerCRUD();
//                    break;
//                case "event":
//                    handleEventCRUD();
//                    break;
//                case "challenge":
////                    handleChallengeCRUD();
//                    break;
//                case "gym":
//                    handleGymCRUD();
//                    break;
//                case "location":
//                    handleLocationCRUD();
//                    break;
//                default:
//                    System.out.println("Invalid model choice. Please select a valid model.");
//            }
//        } else {
//            // Handle non-CRUD menu here (you can add more options later)
//            System.out.println("Non-CRUD menu will be implemented later.");
//
//            System.out.println("These are our options for now:");
//            System.out.println("1. Filter gyms by price");
//            System.out.println("2. Change membership prices");
//            System.out.println("3. Get most frequent locations for GymLion events");
//            System.out.println("4. Delete expensive athletes");
//            System.out.println("5. Get customer with most orders");
//            System.out.println("6. Reward customer of the month");
//            System.out.println("7. Delete all events that already took place");
//            System.out.println("8. Get all events that took place in a period of time in a city selected");
//            System.out.println("9. Find all users that completed a challenge");
//            System.out.println("10. Upgrade points of a challenge that was completed less than a number n of times");
//            System.out.println("11. Show customers with balance over threshold");
//            System.out.println("12. Increase salary of popular athletes");
//            System.out.println("13. Show people sorted by age");
//
//            String crudOperation = scanner.nextLine();
//
//            switch (crudOperation.toLowerCase()) {
//                case "1":
//                    System.out.println("Starting price:");
//                    Double startPrice = Double.parseDouble(scanner.nextLine());
//                    System.out.println("Last price:");
//                    Double endPrice = Double.parseDouble(scanner.nextLine());
//                    List<String> gyms = gymService.findGymsBasedOnMembershipPrices(startPrice, endPrice);
//                    System.out.println(gyms.size());
//                    for (int i=0; i<gyms.size(); i++) {
//                        System.out.println(gyms.get(i));
//                    }
//
//                    break;
//                case "2":
//                    System.out.println("Gym name:");
//                    String name = scanner.nextLine();
//                    System.out.println("Percent:");
//                    Integer percent = Integer.parseInt(scanner.nextLine());
//
//                    gymService.changeMembershipPrices(membershipRepository, name, percent);
//                    for (int i=0; i<membershipRepository.getSize(); i++) {
//                        if (membershipRepository.get(i) != null) {
//                            System.out.println(membershipRepository.get(i).getPrice());
//                        }
//                    }
//                    System.out.println("Membership prices for this gym were changed sucessfully!");
//                    break;
//                case "3":
//                    ArrayList<Location> locations = locationService.mostFrequentLocations(eventRepository);
//                    for (int i=0; i<locations.size(); i++) {
//                        if (locations.get(i) != null) {
//                            System.out.println(locations.get(i).getCountryName() + "->" + locations.get(i).getCityName());
//                        }
//                    }
//                    break;
//                case "4":
//                    Integer percents = Integer.parseInt(scanner.nextLine());
//                    athleteService.deleteExpensiveAthletes(percents);
//                    for (int i=0; i<athleteRepository.getSize(); i++) {
//                        if (athleteRepository.get(i) != null){
//                            System.out.println(athleteRepository.get(i).getId());
//                        }
//                    }
//                    break;
//                case "5":
//                    System.out.println("the customer with most orders is: " + customerService.getCustomerWithMostOrders(orderRepository));
//                    break;
//                case "6":
//                    System.out.println("Reward:");
//                    Integer reward = Integer.parseInt(scanner.nextLine());
//                    Integer rewardedCustomerId = customerService.rewardCustomerOfTheMonth(orderRepository, reward);
//                    System.out.println("Customer of the month was rewarded " + reward + "$");
//                    System.out.println("New balance: " + customerRepository.get(rewardedCustomerId).getBalance());
//                    break;
//                case "7":
////                    eventService.deleteAllEventsThatAlreadyTookPlace();
//                    for (int i=0; i<eventRepository.getSize(); i++) {
//                        if (eventRepository.get(i) != null) {
//                            System.out.println(eventRepository.get(i).getName());
//                        }
//                    }
//                    break;
//                case "8":
//                    System.out.println("Start date (day, month, year):");
//                    Integer sDay = Integer.parseInt(scanner.nextLine());
//                    Integer sMonth = Integer.parseInt(scanner.nextLine());
//                    Integer sYear = Integer.parseInt(scanner.nextLine());
//                    System.out.println("End date (day, month, year):");
//                    Integer eDay = Integer.parseInt(scanner.nextLine());
//                    Integer eMonth = Integer.parseInt(scanner.nextLine());
//                    Integer eYear = Integer.parseInt(scanner.nextLine());
//                    System.out.println("Country name:");
//                    String nameOfCountry = scanner.nextLine();
//
////                    ArrayList<HashMap<String, String>> arr = eventService.getAllEventsFromAPlaceWithinAPeriod(
////                                                            LocalDate.of(sYear, sMonth, sDay),
////                                                            LocalDate.of(eYear, eMonth, eDay),
////                                                            nameOfCountry,
////                                                            locationRepository
////                                                            );
////                    for (int i=0; i<arr.size(); i++) {
////                        System.out.println(arr.get(i).get("name") + " " + arr.get(i).get("description"));
////                    }
//                    break;
//                case "9":
//                    System.out.println("Challenge name:");
//                    String challengeName = scanner.nextLine();
////                    ArrayList<Integer> ids = challengeService.getAllCustomersThatCompletedChallenge(customerRepository, challengeName);
////                    for (int i=0; i<ids.size(); i++) {
////                        System.out.println(ids.get(i));
////                    }
//                    break;
//                case "10":
//                    System.out.println("Number of completions:");
//                    Integer num = Integer.parseInt(scanner.nextLine());
//                    System.out.println("Points:");
//                    Integer points = Integer.parseInt(scanner.nextLine());
//
////                    challengeService.upgradeChallenge(customerRepository, num, points);
////                    for (int i=0; i<challengeRepository.getSize(); i++) {
////                        if (challengeRepository.get(i) != null) {
////                            System.out.println(challengeRepository.get(i).getPoints());
////                        }
////                    }
//                    System.out.println("Challenges upgraded successfully!");
//                    break;
//                case "11":
//                    System.out.println("Threshold:");
//                    double threshold = Double.parseDouble(scanner.nextLine());
//
//                    personService.showCustomersWithBalanceOverThreshold(threshold);
//
//                    break;
//                case "12":
//                    personService.increaseSalaryOfPopularAthletes();
//
//                    break;
//                case "13":
//                    personService.showPeopleSortedByAge();
//                    break;
//                default:
//                    System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//            }
//        }
//    }
//
//    // CRUD methods for each model
//    private static void handleAthleteCRUD() throws InvalidDataException {
//        System.out.println("You chose Athlete. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//        Integer index;
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                System.out.println("Name:");
//                String name = scanner.nextLine();
//                System.out.println("Email:");
//                String email = scanner.nextLine();
//                System.out.println("Phone:");
//                String phone = scanner.nextLine();
//                System.out.println("Address:");
//                String address = scanner.nextLine();
//                System.out.println("Age:");
//                Integer age= Integer.parseInt(scanner.nextLine());
//                System.out.println("Salary:");
//                double salary= Double.parseDouble(scanner.nextLine());
//                System.out.println("Followers:");
//                Integer followers = Integer.parseInt(scanner.nextLine());
//                System.out.println("Bonus:");
//                Integer bonusPerTenThousandLikes = Integer.parseInt(scanner.nextLine());
//                athleteService.registerNewEntity(name, email, phone, address, age, salary, followers, bonusPerTenThousandLikes);
//                break;
//            case "read":
//                System.out.println("Index:");
//                index = Integer.parseInt(scanner.nextLine());
//                athleteService.get(index);
//                break;
//            case "delete":
//                System.out.println("Index:");
//                index = Integer.parseInt(scanner.nextLine());
//                athleteService.delete(index);
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleCustomerCRUD() throws InvalidDataException {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//        Integer index;
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                System.out.println("Name:");
//                String name = scanner.nextLine();
//                System.out.println("Email:");
//                String email = scanner.nextLine();
//                System.out.println("Phone:");
//                String phone = scanner.nextLine();
//                System.out.println("Address:");
//                String address = scanner.nextLine();
//                System.out.println("Age:");
//                Integer age= Integer.parseInt(scanner.nextLine());
//                System.out.println("Balance:");
//                double balance= Double.parseDouble(scanner.nextLine());
//
//                ArrayList<FitnessChallenge> challengesCompleted = new ArrayList<>();
//
//                customerService.registerNewEntity(name, email, phone, address, age, balance);
//                break;
//            case "read":
//                System.out.println("Index:");
//                index = Integer.parseInt(scanner.nextLine());
//                customerService.get(index);
//                break;
//            case "delete":
//                System.out.println("Index:");
//                index = Integer.parseInt(scanner.nextLine());
//                customerService.delete(index);
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleEventCRUD() throws InvalidDataException {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//        Integer index;
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                System.out.println("Name:");
//                String name = scanner.nextLine();
//                System.out.println("Description:");
//                String description = scanner.nextLine();
//                System.out.println("Start date (day, month, year):");
//                Integer sDay = Integer.parseInt(scanner.nextLine());
//                Integer sMonth = Integer.parseInt(scanner.nextLine());
//                Integer sYear = Integer.parseInt(scanner.nextLine());
//                System.out.println("End date (day, month, year):");
//                Integer eDay = Integer.parseInt(scanner.nextLine());
//                Integer eMonth = Integer.parseInt(scanner.nextLine());
//                Integer eYear = Integer.parseInt(scanner.nextLine());
//                System.out.println("Capacity:");
//                Integer capacity = Integer.parseInt(scanner.nextLine());
//                System.out.println("Location's id:");
//                Integer locationId = Integer.parseInt(scanner.nextLine());
//                eventService.registerNewEntity(LocalDate.of(sYear, sMonth, sDay),
//                        LocalDate.of(eYear, eMonth, eDay), name, description, capacity, locationId);
//                break;
//            case "read":
//                System.out.println("Index:");
//                index = Integer.parseInt(scanner.nextLine());
//                eventService.get(index);
//                break;
//            case "delete":
//                System.out.println("Index:");
//                index = Integer.parseInt(scanner.nextLine());
//                eventService.delete(index);
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
////    private static void handleChallengeCRUD() {
////        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
////        String crudOperation = scanner.nextLine();
////
////        switch (crudOperation.toLowerCase()) {
////            case "create":
////                createCustomer();
////                break;
////            case "read":
////                readCustomer();
////                break;
////            case "delete":
////                deleteCustomer();
////                break;
////            default:
////                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
////        }
////    }
//
//    private static void handleGymCRUD() throws InvalidDataException {
//        System.out.println("You chose Gym. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                System.out.println("Name:");
//                String name = scanner.nextLine();
//                System.out.println("Description:");
//                String description = scanner.nextLine();
//                System.out.println("Capacity:");
//                Integer capacity = Integer.parseInt(scanner.nextLine());
//                System.out.println("Location ID:");
//                Integer locationID = Integer.parseInt(scanner.nextLine());
//
//                gymService.registerNewEntity(name, description, capacity, locationID);
//                System.out.println("Succesfully added a new gym!");
//                break;
//            case "read":
//                break;
//            case "delete":
//                System.out.println("Gym ID:");
//                Integer gymId = Integer.parseInt(scanner.nextLine());
//
//                gymService.delete(gymId);
//                System.out.println("Succesfully deleted the gym!");
//                break;
//            case "update":
//                System.out.println("Gym ID:");
//                Integer gymID = Integer.parseInt(scanner.nextLine());
//
////                gymService.update(gymID);
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//
//    private static void handleLocationCRUD() throws InvalidDataException {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//        Integer locationID;
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                System.out.println("Country name:");
//                String countryName = scanner.nextLine();
//                System.out.println("City name:");
//                String cityName = scanner.nextLine();
//                System.out.println("Latitude:");
//                Double latitude = Double.parseDouble(scanner.nextLine());
//                System.out.println("Latitude:");
//                Double longitude = Double.parseDouble(scanner.nextLine());
//
//                locationService.registerNewEntity(countryName, cityName, latitude, longitude);
//                System.out.println("Succesfully added a new location!");
//                break;
//            case "read":
//                break;
//            case "delete":
//                System.out.println("Location ID:");
//                locationID = Integer.parseInt(scanner.nextLine());
//
//                locationService.delete(locationID);
//                System.out.println("Succesfully deleted the location!");
//                break;
//            case "update":
//                System.out.println("Location ID:");
//                locationID = Integer.parseInt(scanner.nextLine());
//
//                locationService.update(locationID);
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//}
//
