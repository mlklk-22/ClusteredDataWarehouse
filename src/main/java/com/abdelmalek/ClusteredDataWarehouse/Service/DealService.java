package com.abdelmalek.ClusteredDataWarehouse.Service;

import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import com.abdelmalek.ClusteredDataWarehouse.Repository.DealRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {
    @Autowired
    private DealRepository _DealRepository;

    public Deal createDeal(Deal deal){
        return _DealRepository.save(deal);
    }
    public Deal updateDeal(int id, Deal updatedDeal){
        return _DealRepository.findById(id)
                        .map(deal -> {
                            deal.setFrom_currency_iso_code(updatedDeal.getFrom_currency_iso_code());
                            deal.setTo_currency_iso_code(updatedDeal.getTo_currency_iso_code());
                            deal.setDate(updatedDeal.getDate());
                            deal.setDeal_amount(updatedDeal.getDeal_amount());
                            return _DealRepository.save(deal);
                        })
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    public void deleteDeal(int id){
        System.out.println(id+"  ----------------------------->");
        _DealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        _DealRepository.deleteById(id);
    }

}

