package com.abdelmalek.ClusteredDataWarehouse.Controller;


import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import com.abdelmalek.ClusteredDataWarehouse.Service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Deals")

public class DealController {

    @Autowired
    private DealService _dealService;

    @PostMapping("/CreateDeals")
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal){
        Deal createDeal = _dealService.createDeal(deal);
        return new ResponseEntity<>(createDeal, HttpStatus.CREATED);
    }

    @PutMapping("UpdateDeals/{id}")
    public ResponseEntity<Deal> createDeal(@PathVariable int id, @RequestBody Deal updateddeal){
        Deal deal = _dealService.updateDeal(id, updateddeal);
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }

    @DeleteMapping("DeleteDeals/{id}")
    public ResponseEntity<Void> deleteDeal(@PathVariable int id){
        _dealService.deleteDeal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
