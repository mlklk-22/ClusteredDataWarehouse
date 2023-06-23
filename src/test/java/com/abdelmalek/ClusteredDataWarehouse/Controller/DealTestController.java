package com.abdelmalek.ClusteredDataWarehouse.Controller;

import com.abdelmalek.ClusteredDataWarehouse.DTO.CurrencyCode;
import com.abdelmalek.ClusteredDataWarehouse.DTO.Status;
import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import com.abdelmalek.ClusteredDataWarehouse.Service.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DealTestController {
    @InjectMocks
    DealController dealController;

    @Mock
    private DealService _dealService;

    @Test
    public void createDealTest() throws JOSEException{

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Deal deal = new Deal();
        deal.setFrom_currency_iso_code(CurrencyCode.ALL.toString());
        deal.setTo_currency_iso_code(CurrencyCode.AED.toString());
        deal.setDate(Calendar.getInstance().getTime().toString());
        deal.setDeal_amount(2500);

        ResponseEntity<Status> createDeal = dealController.createDeal(deal);

        assertEquals(createDeal.getStatusCode(), HttpStatus.CREATED);
    }


    @Test
    public void createDealWrongFormatTest() throws JOSEException{

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Deal deal = new Deal();
        deal.setFrom_currency_iso_code(CurrencyCode.AMD.toString());
        deal.setTo_currency_iso_code(CurrencyCode.AED.toString());
        deal.setDate(Calendar.getInstance().getTime().toString());
        deal.setDeal_amount(2500);

        ResponseEntity<Status> createDeal = dealController.createDeal(deal);
        System.out.println(createDeal);
        assertEquals(createDeal.getStatusCode(),HttpStatus.BAD_REQUEST);
    }


}
