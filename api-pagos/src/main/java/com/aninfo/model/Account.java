package com.aninfo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "cbu", unique = true)
    private Long cbu;

    @NotNull
    private Currency currency;

    @NotNull
    private String name;

    @NotNull
    private Double balance;

    public Account(){
    }

    public Account(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency(){
        return this.currency;
    }

    public void setCurrency(Currency newCurrency){
        this.currency = newCurrency;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
