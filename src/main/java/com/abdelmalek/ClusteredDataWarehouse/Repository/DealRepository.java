package com.abdelmalek.ClusteredDataWarehouse.Repository;

import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository <Deal, Integer> {
    @Procedure("GetAllDeals")
    List<Deal> GetAllDeals();

    @Procedure("isRequestedTwice")
    List<Deal> isRequestedTwice(int ID, String from_currency, String to_currency, String date, int deal_amount);
}
