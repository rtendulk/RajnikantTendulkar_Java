package com.rtendulk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TransactionDomain implements Serializable {

    private static final long serialVersionUID = 3981129707488019480L;

    @JsonProperty("TransactionId")
    private String transactionId;
    @JsonProperty("Instrument")
    private String instrument;
    @JsonProperty("TransactionType")
    private String transactionType;
    @JsonProperty("TransactionQuantity")
    private Integer transactionQuantity;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getTransactionQuantity() {
        return transactionQuantity;
    }

    public void setTransactionQuantity(Integer transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    @Override
    public String toString() {
        return "TransactionDomain{" +
                "transactionId='" + transactionId + '\'' +
                ", instrument='" + instrument + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionQuantity='" + transactionQuantity + '\'' +
                '}';
    }
}
