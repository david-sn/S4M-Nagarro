/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagarro.s4m.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Formula;

/**
 *
 * @author david
 */
@Entity
@Table(name = "statement")
public class Statement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer account_id;
    @Formula(value="CAST(datefield as DATE )")
    private String datefield;
    @Formula(value="CAST(amount as INTEGER )")
    private String amount;
    @Transient
    private String accountHashed;

    public Statement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getDatefield() {
        return datefield;
    }

    public void setDatefield(String datefield) {
        this.datefield = datefield;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccountHashed() {
        return accountHashed;
    }

    public void setAccountHashed(String accountHashed) {
        this.accountHashed = accountHashed;
    }

}
