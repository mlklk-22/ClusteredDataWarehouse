package com.abdelmalek.ClusteredDataWarehouse.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "DEAL")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "from_currency_iso_code")
    private String from_currency_iso_code;

    @Column(name = "to_currency_iso_code")
    private String to_currency_iso_code;

    @Column(name = "date")
    private Date date;

    @Column(name = "deal_amount")
    private String deal_amount;



}
