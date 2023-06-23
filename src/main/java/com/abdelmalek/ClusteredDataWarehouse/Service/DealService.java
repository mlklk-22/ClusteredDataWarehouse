package com.abdelmalek.ClusteredDataWarehouse.Service;

import com.abdelmalek.ClusteredDataWarehouse.DTO.CurrencyCode;
import com.abdelmalek.ClusteredDataWarehouse.DTO.Status;
import com.abdelmalek.ClusteredDataWarehouse.Model.Deal;
import com.abdelmalek.ClusteredDataWarehouse.Repository.DealRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DealService {
    @Autowired
    private DealRepository _dealRepository;
    @Transactional
    public Status createDeal(Deal deal){
        generateJson("Create_Deals_Input", deal);
      return createDealInternal(deal);
    }
    public Status updateDeal(int id, Deal updatedDeal){
        generateJson("Update_Deals_Input", updatedDeal);
        return updateDealInternal(id, updatedDeal);
    }
    public Status deleteDeal(int id){
        Object objID = id;
        generateJson("Delete_Deals_Input", objID);
        return deleteDealInternal(id);
    }
    @Transactional
    public List<Deal> getAllDeals(){
        try
        {
            return _dealRepository.GetAllDeals();
        }

        catch (Exception exception)
        {
            exception.getMessage();
        }
        return _dealRepository.GetAllDeals();
    }
    @Transactional
    public Status createDealInternal(Deal deal)  {
        Status status = new Status();
        Calendar calendar = Calendar.getInstance();
        String inputPattern = "yyyy-MM-dd";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern);
        LocalDate localDate ;

        if (!isRequestedTwice(deal)) {

            status.statusCode = 1;
            if (!deal.getFrom_currency_iso_code().isEmpty()) {
                if (CurrencyCode.ISO_CURRENCY_CODES.contains(deal.getFrom_currency_iso_code())) {
                    deal.setFrom_currency_iso_code(deal.getFrom_currency_iso_code());
                } else {
                    return outStatusError("The Format for From_currency_iso_code Not Found!");
                }
            } else {
                return outStatusError("The Value For From_currency_iso_code is Null!");
            }

            if (!deal.getTo_currency_iso_code().isEmpty()) {
                if (CurrencyCode.ISO_CURRENCY_CODES.contains(deal.getTo_currency_iso_code())) {
                    deal.setTo_currency_iso_code(deal.getTo_currency_iso_code());
                } else {
                    return outStatusError("The Format for To_currency_iso_code Not Found!");
                }
            } else {
                return outStatusError("The Value For To_currency_iso_code is Null!");
            }

            if (deal.getDate() != null) {
                try {
                    localDate = LocalDate.parse(deal.getDate(), inputFormatter);
                    deal.setDate(localDate.format(inputFormatter));

                } catch (Exception ex) {
                    ex.getMessage();
                }
            } else {
                return outStatusError("The Value For Date is Null!");
            }

            if (deal.getDeal_amount() != 0) {
                deal.setDeal_amount(deal.getDeal_amount());
            } else {
                return outStatusError("The Value For deal_amount is Null!");
            }

            _dealRepository.save(deal);

            status.reasonMsg = "Row Inserted!";
            status.statusDate = calendar.getTime();

            generateJson("Create_Deals_Output", status);

        }
        else {
            return outStatusError("System should not import same request twice!");
        }
        return status;
    }
    public Status updateDealInternal(int id, Deal updatedDeal){
        Status status = new Status();
        Calendar calendar = Calendar.getInstance();
        status.statusCode = 1;

        if(_dealRepository.findById(id).isEmpty() || _dealRepository.findById(id) == null)
        {
            return outStatusError("User not found with id: " + id);
        }
            return _dealRepository.findById(id)
                   .map(deal -> {

                       if (!updatedDeal.getFrom_currency_iso_code().isEmpty())
                       {
                           if (CurrencyCode.ISO_CURRENCY_CODES.contains(updatedDeal.getFrom_currency_iso_code()))
                           {
                               deal.setFrom_currency_iso_code(updatedDeal.getFrom_currency_iso_code());
                           }
                           else
                           {
                               return outStatusError("The Format for From_currency_iso_code is Incorrect!");
                           }
                       }
                       else
                       {
                           return outStatusError("The Value For From_currency_iso_code is Null!");
                       }

                       if (!updatedDeal.getTo_currency_iso_code().isEmpty())
                       {
                           if (CurrencyCode.ISO_CURRENCY_CODES.contains(updatedDeal.getTo_currency_iso_code()))
                           {
                               deal.setTo_currency_iso_code(updatedDeal.getTo_currency_iso_code());
                           }
                           else
                           {
                               return outStatusError("The Format for To_currency_iso_code is Incorrect!");
                           }
                       }
                       else
                       {
                           return outStatusError("The Value For To_currency_iso_code is Null!");
                       }

                       if (updatedDeal.getDate() != null)
                       {
                           try
                           {
                               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                               deal.setDate(dateFormat.format(deal.getDate()));
                           }
                           catch (Exception ex)
                           {
                               ex.getMessage();
                           }
                       }
                       else
                       {
                           return outStatusError("The Value For Date is Null!");
                       }

                       if (updatedDeal.getDeal_amount() != 0)
                       {
                           deal.setDeal_amount(updatedDeal.getDeal_amount());
                       }
                       else
                       {
                           return outStatusError("The Value For Date is Null!");
                       }

                       _dealRepository.save(deal);

                       status.reasonMsg = "Row Updated!";
                       status.statusDate = calendar.getTime();
                       generateJson("Update_Deals_Output", status);
                       return status;
                   })
                   .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
       }
    public Status deleteDealInternal(int id){

        Status status = new Status();
        Calendar calendar = Calendar.getInstance();
        status.statusCode = 1;

        if(_dealRepository.findById(id).isEmpty() || _dealRepository.findById(id) == null)
        {
            return outStatusError("User not found with id: " + id);
        }

        _dealRepository.deleteById(id);


        status.statusDate = calendar.getTime();
        status.reasonMsg = "Row Deleted!";

        generateJson("Delete_Deals_Output", status);
        return status;
    }
    public Status outStatusError(String errMsg){
     Status out = new Status();
        Calendar calendar = Calendar.getInstance();

     out.statusCode = 0;
     out.statusDate = calendar.getTime();
     out.reasonMsg = errMsg;
     return out;
    }
    public void generateJson(String title, Object request){
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            String json = objectMapper.writeValueAsString(request);
            saveJsonLog(title, json);
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }
    public void saveJsonLog(String strFileName,  String request) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String directoryPath = "C:\\Log\\" + LocalDate.now();

        Path path = Paths.get(directoryPath);
        boolean directoryExists = Files.exists(path) && Files.isDirectory(path);


        if (!directoryExists)
            Files.createDirectory(path);

        String filePath = path + "\\" + strFileName + "_" + ".txt";
        boolean fileExists = Files.exists(Path.of(filePath));
        if (!fileExists)
            Files.createFile(Path.of(filePath));


        FileWriter fileWriter = new FileWriter(filePath, true);
        String toBeWrite = "\nTime:\n" + LocalTime.now() + "\nResponse:\n" + request + "\n///////////////\n\n";
        fileWriter.write(toBeWrite);
        fileWriter.close();
    }

    @Transactional
    public boolean isRequestedTwice(Deal deal){
        String inputPattern = "yyyy-MM-dd";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern);
        LocalDate localDate = LocalDate.parse(deal.getDate(), inputFormatter);
        deal.setDate(localDate.format(inputFormatter));

        List<Deal> ThereSameRecord =
                _dealRepository.isRequestedTwice(
                        deal.getId(),
                        deal.getFrom_currency_iso_code(),
                        deal.getTo_currency_iso_code(),
                        deal.getDate(),
                        deal.getDeal_amount()
                );

        if (ThereSameRecord.size() > 0)
            return true;

        return false;
    }


}
