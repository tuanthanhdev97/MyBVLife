package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceDTO extends ResponseDTO implements Serializable {
    private List<Invoice> invoiceList;
    private String policyHolder;
    private BigDecimal invoiceAmountSum;
    private int maxSearch;

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    public BigDecimal getInvoiceAmountSum() {
        return invoiceAmountSum;
    }

    public void setInvoiceAmountSum(BigDecimal invoiceAmountSum) {
        this.invoiceAmountSum = invoiceAmountSum;
    }

    public int getMaxSearch() {
        return maxSearch;
    }

    public void setMaxSearch(int maxSearch) {
        this.maxSearch = maxSearch;
    }
}
