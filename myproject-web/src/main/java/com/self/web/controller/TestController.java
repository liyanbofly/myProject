package com.self.web.controller;


import com.alibaba.fastjson.JSON;
import com.self.model.vo.OpenApiTest;
import com.self.model.vo.UserInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Resource
    RestTemplate restTemplate;



    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel() throws IOException {

//        List<OpenApiTest> listApi =new ArrayList<>();
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName("张三");
//        userInfo.setAge(20);
//        listApi.add(userInfo);

        String txt01=readTxtFileToString("");

        List<OpenApiTest> listAPI= JSON.parseArray(txt01,OpenApiTest.class);
        // 创建一个Workbook对象
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建一个Sheet对象
            Sheet sheet = workbook.createSheet("开放平台API信息");
            // 创建表头
            Row headerRow = sheet.createRow(0);
            List<String> fields=getFields();
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fields.get(i));
            }

            int rowNum = 1;
            for (OpenApiTest apiTest : listAPI) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < fields.size(); i++) {
                    row.createCell(i).setCellValue(getValueByFieldName(apiTest, fields.get(i)));
                }
            }
            // 将Workbook转换为字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx");
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getFields(){
        List<String> fieldNames = new ArrayList<>();
        try {
            // 获取UserInfo类的Class对象
            Class<?> userInfoClass = OpenApiTest.class; // 替换成你的实际包名和类名

            // 获取UserInfo类的所有声明字段
            Field[] fields = userInfoClass.getDeclaredFields();
            for (Field field : fields) {
                fieldNames.add(field.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldNames;
    }

    public  String getValueByFieldName(OpenApiTest openApiTest, String fieldName) {
        try {
            // 根据字段名获取字段
            Field field = openApiTest.getClass().getDeclaredField(fieldName);
            // 设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            // 获取对象的属性值
            return field.get(openApiTest).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public String readTxtFileToString(String filePath) throws IOException {
          filePath = "Attachment/txt.text";
        StringBuilder stringBuilder = new StringBuilder();
        // 使用ClassPathResource来获取txt文件的输入流
        ClassPathResource resource = new ClassPathResource(filePath);
        InputStream inputStream = resource.getInputStream();

        // 使用BufferedReader逐行读取文件内容，并追加到StringBuilder中
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }

        // 将StringBuilder转换为String并返回
        return stringBuilder.toString();
    }







    @RequestMapping("/httpSend")
    public  String  httpSend() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.set("dmall_chain_flag","gray");
        headers.set("dmall_chain_type", "true");
        headers.set("guid", "123456");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"张三\",\"age\":20}", headers);


        ResponseEntity<String> response =  restTemplate.postForEntity("http://localhost:8081/saveEntity", request, String.class);


        return "it is success!";
    }


}
