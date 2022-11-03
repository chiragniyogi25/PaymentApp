package com.miniproject.payment.app.pdfExporter;

import com.miniproject.payment.app.dto.RecurringPaymentsDTO;
import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.entity.User;
import com.miniproject.payment.app.jpaAuth.CustomUserDetail;
import com.miniproject.payment.app.repository.RecurringPaymentsRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
@Service
public class UploadFile {
    @Autowired
    private RecurringPaymentsRepository recurringPaymentsRepository;

    //checking that file is of Excel type or not
    public static boolean checkExcelFormat(MultipartFile file){
        if(file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }else{
            return false;
        }
    }

    //convert Excel to List of Bills
    public static List<RecurringPaymentsDTO> convertExcelToList(User user, InputStream is){
        List<RecurringPaymentsDTO> list_dto=new ArrayList<>();
        List<RecurringPayments> list=new ArrayList<>();
        try{
           XSSFWorkbook workbook= new XSSFWorkbook(is);
           XSSFSheet sheet=workbook.getSheet("Sheet1");
           int rowNumber=0;
            Iterator<Row> row_itr = sheet.iterator();

            while(row_itr.hasNext()){
                Row row= row_itr.next();
                //skip first row
                if(rowNumber==0){
                    rowNumber++;
                    continue;
                }
                //iterating cells
                Iterator<Cell> cells_itr=row.iterator();
                RecurringPaymentsDTO bill_dto=new RecurringPaymentsDTO();
                int cid=0;
                while(cells_itr.hasNext()){
                    Cell cell = cells_itr.next();
                    switch(cid){
                        case 0:
                            bill_dto.setDescription(cell.getStringCellValue());
                            break;
                        case 1:
                            bill_dto.setAmount((int)(cell.getNumericCellValue()));
                            break;
                        case 2:
                            LocalDateTime date=cell.getLocalDateTimeCellValue();
                            String str=String.valueOf(date);
                            String[] arr=str.split("-");
                            String str1=""+arr[2].charAt(0)+arr[2].charAt(1)+"-"+arr[1]+"-"+arr[0];
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                Date start = formatter.parse(str1);
                                bill_dto.setStartDate(start);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 3:
                            bill_dto.setNoOfTimes((int)(cell.getNumericCellValue()));
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list_dto.add(bill_dto);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list_dto;
    }
//    public String uploadFile(Authentication authentication, MultipartFile file) throws IOException{
//        CustomUserDetail myUserDetails = (CustomUserDetail) authentication.getPrincipal();
//        List<RecurringPayments> recurringList=new ArrayList<>();
//
//        InputStream inputStream=file.getInputStream();//getting the inputStream from File
//        CsvParserSettings setting=new CsvParserSettings();
//        setting.setHeaderExtractionEnabled(true);//first row consists of column name so skip that part
//        CsvParser parser=new CsvParser(setting);
//        List<Record> parseAllRecords=parser.parseAllRecords(inputStream);//parsing all datasets in xml file
//        parseAllRecords.forEach(record -> {
//            RecurringPaymentsDTO billsDTO=new RecurringPaymentsDTO();
//
//            billsDTO.setDescription(record.getString("Description"));
//            billsDTO.setAmount(Integer.parseInt(record.getString("Amount")));
//            String str=record.getString("StartDate");
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//            try {
//                Date start = formatter.parse(str);
//                billsDTO.setStartDate(start);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//            billsDTO.setNoOfTimes(Integer.parseInt(record.getString("NoOfTimes")));
//
//            //mapping to normal form
//            RecurringPayments bill=modelMapper.map(billsDTO,RecurringPayments.class);
//
//            recurringList.add(bill);
//        });
//        recurringPaymentsRepository.saveAll(recurringList);
//
//        return "Bills Successfully added";
//    }
}
