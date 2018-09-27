package com.rtendulk.domain;

import java.io.Serializable;

public class PositionDomain implements Serializable {
    private static final long serialVersionUID = 6399077774366688755L;
    private String instrument;
    private String account;
    private String accountType;
    private Integer quantity;
    private Integer delta;

    public PositionDomain() {
    }

    public PositionDomain(String instrument, String account, String accountType, Integer quantity) {
        this.instrument = instrument;
        this.account = account;
        this.accountType = accountType;
        this.quantity = quantity;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }


    @Override
    public String toString() {
        return instrument + "," + account + "," + accountType + "," + quantity +
                "," + delta;
    }
}
