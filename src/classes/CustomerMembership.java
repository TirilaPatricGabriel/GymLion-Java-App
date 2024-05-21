package classes;

public class CustomerMembership {
    public Integer membershipId, customerId;

    public CustomerMembership(Integer membershipId, Integer athleteId) {
        this.membershipId = membershipId;
        this.customerId = athleteId;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public Integer getAthleteId() {
        return customerId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public void setAthleteId(Integer athleteId) {
        this.customerId = athleteId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
