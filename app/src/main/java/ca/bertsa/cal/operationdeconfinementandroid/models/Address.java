package ca.bertsa.cal.operationdeconfinementandroid.models;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Address implements Serializable {
    private Integer id;
    private String zipCode;
    private String street;
    private String city;
    private String province;
    private String apt;

    public Address() {
    }

    public Address(String zipCode, String street, String city, String province, String apt) {
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.province = province;
        this.apt = apt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }
}
