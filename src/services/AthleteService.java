package services;

import repositories.AthleteRepository;
import classes.Athlete;
import exceptions.InvalidDataException;


import java.util.ArrayList;
import java.util.List;

public class AthleteService {

    private AthleteRepository AthleteRepository = new AthleteRepository();

    public void registerNewEntity(String name, String email, String phone, String address, double salary, int socialMediaFollowers, double bonusPerTenThousandLikes) throws InvalidDataException {

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

        if (salary < 0) {
            throw new InvalidDataException("Invalid salary");
        }

        if (socialMediaFollowers < 0) {
            throw new InvalidDataException("Invalid followers");
        }

        if (bonusPerTenThousandLikes < 0) {
            throw new InvalidDataException("Invalid bonus");
        }

        Athlete entity = new Athlete(name, email, phone, address, salary, socialMediaFollowers, bonusPerTenThousandLikes);
        AthleteRepository.add(entity);
    }
}