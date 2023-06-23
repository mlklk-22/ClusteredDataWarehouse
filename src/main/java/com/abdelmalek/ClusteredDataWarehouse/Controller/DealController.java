package com.abdelmalek.ClusteredDataWarehouse.Controller;

import com.abdelmalek.ClusteredDataWarehouse.DTO.Status;
import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import com.abdelmalek.ClusteredDataWarehouse.Service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Deals")

public class DealController {

    @Autowired
    private DealService _dealService;

    @PostMapping("/CreateDeals")
    public ResponseEntity<Status> createDeal(@RequestBody Deal deal) {
        Status createDeal = _dealService.createDeal(deal);

        if (createDeal.statusCode == 0)
            return new ResponseEntity<>(createDeal, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(createDeal, HttpStatus.CREATED);
    }

    @PutMapping("UpdateDeals/{id}")
    public ResponseEntity<Status> updateDeal(@PathVariable int id, @RequestBody Deal updateddeal) {
        Status deal = _dealService.updateDeal(id, updateddeal);

        if (deal.statusCode == 0)
            return new ResponseEntity<>(deal, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(deal, HttpStatus.OK);
    }

    @DeleteMapping("DeleteDeals/{id}")
    public ResponseEntity<Status> deleteDeal(@PathVariable int id) {
        Status deleteDeal = _dealService.deleteDeal(id);

        if (deleteDeal.statusCode == 0)
            return new ResponseEntity<>(deleteDeal, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(deleteDeal, HttpStatus.OK);
    }

    @GetMapping("/GetAllDeals")
    public ResponseEntity<List<Deal>> getAllDeals() {
        return ResponseEntity.ok(_dealService.getAllDeals());
    }
}
