package ca.bertsa.cal.operationdeconfinementandroid.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import ca.bertsa.cal.operationdeconfinementandroid.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Citizen implements Serializable {
    private Long id;
    private String noAssuranceMaladie;
    private boolean active;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date birth;
    private Sex sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date dateJoined;
    private String phone;
    private Address address;
    private License license;
    private boolean profileCompleted;
    private Citizen tutor;

    public Citizen(String noAssuranceMaladie, String email, String password, String phone) {
        this.noAssuranceMaladie = noAssuranceMaladie;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
