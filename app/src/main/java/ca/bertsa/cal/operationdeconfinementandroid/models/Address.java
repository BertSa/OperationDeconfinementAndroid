package ca.bertsa.cal.operationdeconfinementandroid.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address implements Serializable {
    private Integer id;
    private String zipCode;
    private String street;
    private String city;
    private String province;
    private String apt;

    public Address(String zipCode, String street, String city, String province, String apt) {
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.province = province;
        this.apt = apt;
    }
}
