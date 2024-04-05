package classes;

public class AthleteMembership {
    public Integer membershipId, athleteId;

    public AthleteMembership(Integer membershipId, Integer athleteId) {
        this.membershipId = membershipId;
        this.athleteId = athleteId;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public Integer getAthleteId() {
        return athleteId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public void setAthleteId(Integer athleteId) {
        this.athleteId = athleteId;
    }
}
