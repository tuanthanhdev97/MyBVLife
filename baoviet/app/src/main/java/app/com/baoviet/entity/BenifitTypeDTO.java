package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class BenifitTypeDTO implements Serializable {

    private String benefitAccountNumber;
    private BigDecimal benefitAmount;
    private String benefitCategoryCode;
    private String benefitCategoryDescription;
    private String benefitComments;

    public String getBenefitAccountNumber() {
        return benefitAccountNumber;
    }

    public void setBenefitAccountNumber(String benefitAccountNumber) {
        this.benefitAccountNumber = benefitAccountNumber;
    }

    public BigDecimal getBenefitAmount() {
        return benefitAmount;
    }

    public void setBenefitAmount(BigDecimal benefitAmount) {
        this.benefitAmount = benefitAmount;
    }

    public String getBenefitCategoryCode() {
        return benefitCategoryCode;
    }

    public void setBenefitCategoryCode(String benefitCategoryCode) {
        this.benefitCategoryCode = benefitCategoryCode;
    }

    public String getBenefitCategoryDescription() {
        return benefitCategoryDescription;
    }

    public void setBenefitCategoryDescription(String benefitCategoryDescription) {
        this.benefitCategoryDescription = benefitCategoryDescription;
    }

    public String getBenefitComments() {
        return benefitComments;
    }

    public void setBenefitComments(String benefitComments) {
        this.benefitComments = benefitComments;
    }
}