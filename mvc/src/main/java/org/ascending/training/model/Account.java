package org.ascending.training.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    public Account() {}
    public Account(String accountType, BigDecimal balance) {
        this.accountType = accountType;
        this.balance = balance;
    }
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }
}