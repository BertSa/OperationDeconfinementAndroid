package ca.bertsa.cal.operationdeconfinementandroid.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import ca.bertsa.cal.operationdeconfinementandroid.enums.CategoryLicence;
import ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense;
import lombok.Getter;

@Getter
public class License implements Serializable {

    private Long id;
    private TypeLicense type;
    private CategoryLicence category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date dateCreation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date dateExpire;

}
