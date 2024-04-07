//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import classes.*;
import exceptions.InvalidDataException;
import services.*;
import repositories.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AthleteService athleteService = new AthleteService();
    private static EventService eventService = new EventService();
    private static CustomerService customerService = new CustomerService();
    private static FitnessChallengeService challengeService = new FitnessChallengeService();
    private static GymService gymService = new GymService();
    private static  LocationService locationService = new LocationService();

//    public static void main(String[] args) {
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
//                    handleChallengeCRUD();
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
//        }
//    }
//
//    // CRUD methods for each model
//    private static void handleAthleteCRUD() throws InvalidDataException {
//        System.out.println("You chose Athlete. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
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
//                System.out.println("Salary:");
//                double salary= Double.parseDouble(scanner.nextLine());
//                System.out.println("Followers:");
//                Integer followers = Integer.parseInt(scanner.nextLine());
//                System.out.println("Bonus:");
//                Integer bonusPerTenThousandLikes = Integer.parseInt(scanner.nextLine());
//                athleteService.registerNewEntity(name, email, phone, address, salary, followers, bonusPerTenThousandLikes);
//                break;
//            case "read":
//                System.out.println("Index:");
//                Integer index = Integer.parseInt(scanner.nextLine());
//                athleteService.get(index);
//                break;
//            case "delete":
//                deleteAthlete();
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleCustomerCRUD() {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                createCustomer();
//                break;
//            case "read":
//                readCustomer();
//                break;
//            case "delete":
//                deleteCustomer();
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleEventCRUD() {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                createCustomer();
//                break;
//            case "read":
//                readCustomer();
//                break;
//            case "delete":
//                deleteCustomer();
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleChallengeCRUD() {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                createCustomer();
//                break;
//            case "read":
//                readCustomer();
//                break;
//            case "delete":
//                deleteCustomer();
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleGymCRUD() {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                createCustomer();
//                break;
//            case "read":
//                readCustomer();
//                break;
//            case "delete":
//                deleteCustomer();
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }
//
//    private static void handleLocationCRUD() {
//        System.out.println("You chose Customer. Select CRUD operation: create, read, or delete");
//        String crudOperation = scanner.nextLine();
//
//        switch (crudOperation.toLowerCase()) {
//            case "create":
//                createCustomer();
//                break;
//            case "read":
//                readCustomer();
//                break;
//            case "delete":
//                deleteCustomer();
//                break;
//            default:
//                System.out.println("Invalid CRUD operation. Please choose create, read, or delete.");
//        }
//    }


}

