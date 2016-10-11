package com.evgenyshilov.web.contacts.commands.search;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class SearchParams {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private DateSearchParam dateSearchParam;
    private String sex;
    private Long maritalStatus;
    private String nationality;
    private String state;
    private String city;
    private String street;
    private String house;
    private String flat;
    private String zipcode;

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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public DateSearchParam getDateSearchParam() {
        return dateSearchParam;
    }

    public void setDateSearchParam(DateSearchParam dateSearchParam) {
        this.dateSearchParam = dateSearchParam;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Long maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return String.format("first_name: %s last_name: %s patronymic: %s birthday: %s date_search_param: %s " +
                "sex: %s marital_status: %s nationality: %s state: %s city: %s street: %s house: %s flat: %s " +
                "zipcode: %s", firstName, lastName, patronymic, birthday != null ? birthday : "", dateSearchParam.toString(),
                sex, maritalStatus, nationality, state, city, street, house, flat, zipcode);
    }
}
