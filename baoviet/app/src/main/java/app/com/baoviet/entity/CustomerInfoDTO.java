package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.List;

public class CustomerInfoDTO implements Serializable {
    private PolicyOwnerDTO policyOwnerList;
    private LifeInsuredDTO lifeInsuredList;
    private List<BeneficiaryDTO> beneficiaryInfoList;
    private boolean ownerLife;

    public PolicyOwnerDTO getPolicyOwnerList() {
        return policyOwnerList;
    }

    public void setPolicyOwnerList(PolicyOwnerDTO policyOwnerList) {
        this.policyOwnerList = policyOwnerList;
    }

    public LifeInsuredDTO getLifeInsuredList() {
        return lifeInsuredList;
    }

    public void setLifeInsuredList(LifeInsuredDTO lifeInsuredList) {
        this.lifeInsuredList = lifeInsuredList;
    }

    public List<BeneficiaryDTO> getBeneficiaryInfoList() {
        return beneficiaryInfoList;
    }

    public void setBeneficiaryInfoList(List<BeneficiaryDTO> beneficiaryInfoList) {
        this.beneficiaryInfoList = beneficiaryInfoList;
    }

    public boolean isOwnerLife() {
        return ownerLife;
    }

    public void setOwnerLife(boolean ownerLife) {
        this.ownerLife = ownerLife;
    }
}
