package ca.bertsa.cal.operationdeconfinementandroid.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

import ca.bertsa.cal.operationdeconfinementandroid.enums.CategoryLicence;
import ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense;

@SuppressWarnings("unused")
public class License implements Serializable {

    public License() {
    }

    private Long id;
    private TypeLicense type;
    private CategoryLicence category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date dateCreation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date dateExpire;

    public Long getId() {
        return id;
    }

    public TypeLicense getType() {
        return type;
    }

    public CategoryLicence getCategory() {
        return category;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateExpire() {
        return dateExpire;
    }
}
