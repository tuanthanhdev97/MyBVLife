package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;


public class AnnualReport implements Serializable {
    private String contractAgentCode;
    private String contractAgentName;
    private String contractBuyer;
    private String contractCompany;
    private Date contractCreateDate;
    private Date contractDeadline;
    private String contractId;
    private String contractPolicyholder;
    private BigDecimal contractYear;
    private String contractFilePath;
    private Long contractUpdateDate;

    public String getContractAgentCode() {
        return contractAgentCode;
    }

    public void setContractAgentCode(String contractAgentCode) {
        this.contractAgentCode = contractAgentCode;
    }

    public String getContractAgentName() {
        return contractAgentName;
    }

    public void setContractAgentName(String contractAgentName) {
        this.contractAgentName = contractAgentName;
    }

    public String getContractBuyer() {
        return contractBuyer;
    }

    public void setContractBuyer(String contractBuyer) {
        this.contractBuyer = contractBuyer;
    }

    public String getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(String contractCompany) {
        this.contractCompany = contractCompany;
    }

    public Date getContractCreateDate() {
        return contractCreateDate;
    }

    public void setContractCreateDate(Date contractCreateDate) {
        this.contractCreateDate = contractCreateDate;
    }

    public Date getContractDeadline() {
        return contractDeadline;
    }

    public void setContractDeadline(Date contractDeadline) {
        this.contractDeadline = contractDeadline;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractPolicyholder() {
        return contractPolicyholder;
    }

    public void setContractPolicyholder(String contractPolicyholder) {
        this.contractPolicyholder = contractPolicyholder;
    }

    public BigDecimal getContractYear() {
        return contractYear;
    }

    public void setContractYear(BigDecimal contractYear) {
        this.contractYear = contractYear;
    }

    public String getContractFilePath() {
        return contractFilePath;
    }

    public void setContractFilePath(String contractFilePath) {
        this.contractFilePath = contractFilePath;
    }

    public Long getContractUpdateDate() {
        return contractUpdateDate;
    }

    public void setContractUpdateDate(Long contractUpdateDate) {
        this.contractUpdateDate = contractUpdateDate;
    }
}
