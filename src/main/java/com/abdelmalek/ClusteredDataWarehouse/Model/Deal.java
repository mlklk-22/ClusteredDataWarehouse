package com.abdelmalek.ClusteredDataWarehouse.Model;

import com.abdelmalek.ClusteredDataWarehouse.DTO.DealValueDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Deal")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "GetAllDeals",
                procedureName = "GetAllDeals",
                resultClasses = Deal.class
        ),
        @NamedStoredProcedureQuery(
                name = "isRequestedTwice",
                procedureName = "isRequestedTwice",
                resultClasses = Deal.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "ID", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "from_currency", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "to_currency", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "date", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "deal_amount", type = Integer.class)
                }
        )

})
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "from_currency_iso_code")
    private String from_currency_iso_code;

    @Column(name = "to_currency_iso_code")
    private String to_currency_iso_code;

    @Column(name = "date")
    private String date;

    @Column(name = "deal_amount")
    private int deal_amount;



}
