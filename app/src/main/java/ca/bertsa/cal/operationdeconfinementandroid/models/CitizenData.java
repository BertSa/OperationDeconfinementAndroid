package ca.bertsa.cal.operationdeconfinementandroid.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

import ca.bertsa.cal.operationdeconfinementandroid.enums.Sex;

@SuppressWarnings("unused")
public class CitizenData implements Serializable {
    private Long id;
    private String noAssuranceMaladie;
    private boolean active;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate birth;
    private Sex sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate dateJoined;
    private String phone;
    private Address address;
    private License license;
    private boolean profileCompleted;
    private CitizenData tutor;

    public CitizenData() {
    }

    public CitizenData(String noAssuranceMaladie, String email, String password, String phone) {
        this.noAssuranceMaladie = noAssuranceMaladie;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoAssuranceMaladie() {
        return noAssuranceMaladie;
    }

    public void setNoAssuranceMaladie(String noAssuranceMaladie) {
        this.noAssuranceMaladie = noAssuranceMaladie;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public CitizenData getTutor() {
        return tutor;
    }

    public void setTutor(CitizenData tutor) {
        this.tutor = tutor;
    }
}
