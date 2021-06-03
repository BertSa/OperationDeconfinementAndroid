package ca.bertsa.cal.operationdeconfinementandroid.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address implements Serializable {
    private Integer id;
    private String zipCode;
    private String street;
    private String city;
    private String province;
    private String apt;


//    /**
//     * @param zipCode  Postal code
//     * @param apt      Appartement number (If applicable)
//     * @param street   Street name
//     * @param city     City
//     * @param province Province
//     */
//    public Address(String zipCode, String street, String city, String province, String apt) {
//        this.zipCode = zipCode;
//        this.street = street;
//        this.city = city;
//        this.province = province;
//        this.apt = apt;
//    }
}
