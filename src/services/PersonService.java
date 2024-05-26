package services;

import classes.Athlete;
import classes.Customer;
import repositories.AthleteRepository;
import repositories.PersonRepository;
import classes.Person;
import exceptions.InvalidDataException;

import java.util.List;

public class PersonService {
    private PersonRepository personRepository = new PersonRepository();

    private static PersonService instance;
    private AuditService auditService;

    private PersonService(PersonRepository repo) {
        this.personRepository = repo;
        this.auditService = AuditService.getInstance();
    }

    public static PersonService getInstance(PersonRepository repo) {
        if (instance == null) {
            instance = new PersonService(repo);
        }
        return instance;
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

    public List<Person> showPeopleSortedByAge(){
        return personRepository.showPeopleSortedByAge();
    }
}