package com.datasoft.bkash.ea.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class IndividualPersonInfoRequest {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("dateOfBirth")
    @JsonFormat(pattern = "yyyy-MM-dd")  // Add this annotation
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("nationalIdPassportNo")
    private String nationalIdPassportNo;

    @JsonProperty("idCopyUrl")
    private String idCopyUrl;

    // Constructors
    public IndividualPersonInfoRequest() {
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalIdPassportNo() {
        return nationalIdPassportNo;
    }

    public void setNationalIdPassportNo(String nationalIdPassportNo) {
        this.nationalIdPassportNo = nationalIdPassportNo;
    }

    public String getIdCopyUrl() {
        return idCopyUrl;
    }

    public void setIdCopyUrl(String idCopyUrl) {
        this.idCopyUrl = idCopyUrl;
    }
}