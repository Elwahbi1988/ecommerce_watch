package com.arkx.Backend.ecomwatches.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor

public class Customer {
    @Getter
    private String id;
    @Getter
    private String FirstName;
    @Getter
    private String LastName;
    private String email;
    private String password;
    private int creationDate;
    private Boolean validAccount;
    @Getter
    private Boolean active;

    public Customer(String id, String FirstName, String LastName, Boolean active){
        this.id = id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.active = active;

    }
}
  // public String getFirstName() {
        //return firstName;




