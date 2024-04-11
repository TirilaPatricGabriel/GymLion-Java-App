package services;

import classes.Athlete;
import classes.Customer;
import repositories.PersonRepository;
import classes.Person;
import exceptions.InvalidDataException;

public class PersonService {
    private PersonRepository personRepository = new PersonRepository();

    public PersonService(PersonRepository repo) {
        this.personRepository = repo;
    }

    public void registerNewEntity(String name, String email, String phone, String address, int age, double salary, int socialMediaFollowers, double bonusPerTenThousandLikes) throws InvalidDataException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Invalid email");
        }

        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("Invalid phone");
        }

        if (address == null || address.trim().isEmpty()) {
            throw new InvalidDataException("Invalid address");
        }

        if (salary < 18) {
            throw new InvalidDataException("Invalid age");
        }

        if (salary < 0) {
            throw new InvalidDataException("Invalid salary");
        }

        if (socialMediaFollowers < 0) {
            throw new InvalidDataException("Invalid followers");
        }

        if (bonusPerTenThousandLikes < 0) {
            throw new InvalidDataException("Invalid bonus");
        }

        Person entity = new Person(name, email, phone, address, age);
        personRepository.add(entity);
    }

    public Person get(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return personRepository.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Person pers = personRepository.get(index);
        personRepository.update(pers);
    }

    public void delete(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Person pers = personRepository.get(index);
        personRepository.delete(pers);
    }

    public void showCustomersWithBalanceOverThreshold(double threshold) throws InvalidDataException {
        if (threshold < 0){
            throw new InvalidDataException("Threshold can't be lower than 0");
        }

        personRepository.showCustomersWithBalanceOverThreshold(threshold);
    }

    public void increaseSalaryOfPopularAthletes() {
        personRepository.increaseSalaryOfPopularAthletes();
    }

    public void showPeopleSortedByAge(){
        personRepository.showPeopleSortedByAge();
    }
}