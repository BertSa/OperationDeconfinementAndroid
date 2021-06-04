package ca.bertsa.cal.operationdeconfinementandroid.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import ca.bertsa.cal.operationdeconfinementandroid.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public CitizenData(String noAssuranceMaladie, String email, String password, String phone) {
        this.noAssuranceMaladie = noAssuranceMaladie;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
