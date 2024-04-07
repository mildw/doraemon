package com.example.excel.application.member;

import com.example.core.s3.Bucket;
import com.example.core.s3.S3Utils;
import com.example.excel.application.excel.ExcelTemplate;
import com.example.excel.config.property.BucketProperty;
import com.example.excel.domain.member.Member;
import com.example.excel.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final BucketProperty bucketProperty;
    private final MemberRepository memberRepository;

    public String getMemberNames() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(Member::getName).collect(Collectors.joining(","));
    }

    public ResponseEntity<Resource> excel() {
        ExcelTemplate excelTemplate = new ExcelTemplate();
        Workbook workbook = excelTemplate.createExcelTemplate();
        return getResponseEntity(workbook);
    }

    private static ResponseEntity<Resource> getResponseEntity(Workbook workbook) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] bytes = outputStream.toByteArray();
            outputStream.close();
            Resource resource = new ByteArrayResource(bytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=my-excel.xlsx")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String tempFileUpload() {
        String tempName = "temp.xlsx";
        try {
            ExcelTemplate excelTemplate = new ExcelTemplate();
            Workbook workbook = excelTemplate.createExcelTemplate();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] bytes = outputStream.toByteArray();
            outputStream.close();
            Bucket bucket = new Bucket(
                    bucketProperty.getName(),
                    bucketProperty.getAccessKey(),
                    bucketProperty.getSecretKey()
            );
            S3Utils.tempFileUpload(bucket, tempName, bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), "에러입니다.");
            tempName = null;
        }
        return tempName;
    }
}
