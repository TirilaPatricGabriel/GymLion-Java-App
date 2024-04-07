package services;

import exceptions.InvalidDataException;
import repositories.GymRepository;
import repositories.GymMembershipRepository;


import java.util.ArrayList;

public class GymService {

    private GymRepository gymRepo = new GymRepository();

    public ArrayList<String> registerNewEntity(GymMembershipRepository membershipRepostiory, Integer startPrice, Integer endPrice) throws InvalidDataException {
        if (startPrice == null || startPrice < 0) {
            throw new InvalidDataException("Invalid start price");
        }
        if (endPrice == null || endPrice < 0) {
            throw new InvalidDataException("Invalid end price");
        }
        return gymRepo.findGymsBasedOnMembershipPrices(membershipRepostiory, startPrice, endPrice);
    }

    public void changeMembershipPrices (GymMembershipRepository membershipRepository, String gymName, Integer percent) throws InvalidDataException {
        if (gymName == null || gymName == "") {
            throw new InvalidDataException("Invalid gym name");
        }
        if (percent == 0) {
            throw new InvalidDataException("Invalid percent. Must be higher or lower than 0.");
        }
        gymRepo.changeMembershipPrices(membershipRepository, gymName, percent);
        System.out.println("Changes were made successfully!");
    }

}