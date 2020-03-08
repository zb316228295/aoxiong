package org.example.message;

import java.io.Serializable;

public class CountryMessage implements Serializable {
    private static final long serialVersionUID = 7879879271617177814L;
    private String country = "China";
    private String provice;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public CountryMessage(String country, String provice) {
        this.country = country;
        this.provice = provice;
    }
}
