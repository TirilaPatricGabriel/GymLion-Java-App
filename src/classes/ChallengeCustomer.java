package classes;

public class ChallengeCustomer {
    public Integer challengeId, customerId;

    public ChallengeCustomer(Integer challengeId, Integer customerId) {
        this.challengeId = challengeId;
        this.customerId = customerId;
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
