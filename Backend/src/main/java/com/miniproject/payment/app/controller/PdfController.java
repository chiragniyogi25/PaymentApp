package com.miniproject.payment.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.entity.Transactions;
import com.miniproject.payment.app.jpaAuth.CustomUserDetail;
import com.miniproject.payment.app.pdfExporter.StatementPDFExporter;
import com.miniproject.payment.app.pdfExporter.UploadFile;
import com.miniproject.payment.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PdfController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFile uploadFile;
//
//    @Autowired
//    private RecurringPayments recurringPayments;

    @GetMapping("/viewStatements/pdf/{startDate}/{endDate}")
    public String exportToPDF(Authentication authentication, @PathVariable String startDate, @PathVariable String endDate, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        CustomUserDetail customUserDetail=(CustomUserDetail) authentication.getPrincipal();
        List<Transactions> listUsers = showYourStatement(customUserDetail,startDate,endDate);

        StatementPDFExporter exporter = new StatementPDFExporter(listUsers);
        exporter.export(response);
        return "success";

    }
    public List<Transactions> showYourStatement(CustomUserDetail myUserDetail, String startDate, String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date start = formatter.parse(startDate);
            Date end = formatter.parse(endDate);
            return userService.getStatement(myUserDetail.getId(),start,end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public String uploadData(Authentication authentication,@RequestParam("file") MultipartFile file) throws IOException{
//        return uploadFile.uploadFile(authentication,file);
//    }

    @PostMapping("/addBills/upload")
    public ResponseEntity<?> upload(Authentication authentication,@RequestParam("file") MultipartFile file){
        CustomUserDetail myUserDetail=(CustomUserDetail) authentication.getPrincipal();
        if(UploadFile.checkExcelFormat(file)){
            userService.save(myUserDetail.getId(),file);

            return ResponseEntity.ok(Map.of("message","Bills are added to DB"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Upload Excel FIle");
    }
}
