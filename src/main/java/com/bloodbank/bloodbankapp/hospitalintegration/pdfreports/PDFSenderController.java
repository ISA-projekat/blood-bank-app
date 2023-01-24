package com.bloodbank.bloodbankapp.hospitalintegration.pdfreports;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/pdf-sender")
@RequiredArgsConstructor
public class PDFSenderController {

    @CrossOrigin
    @PostMapping("/send")
    public ResponseEntity sendPdf(@RequestBody MultipartFile pdf) throws Exception {
        // Used for sending PDFs of monthly reports

        String filePath = "..\\PDF\\" + pdf.getOriginalFilename();
        Files.copy(pdf.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok("Sent");
    }
}
