package com.abdelmalek.ClusteredDataWarehouse.Repository;

import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository <Deal, Integer> {
}
