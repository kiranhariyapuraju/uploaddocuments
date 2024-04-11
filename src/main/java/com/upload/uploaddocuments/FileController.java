package com.upload.uploaddocuments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private AzureStorageService azureStorageService;

    @Autowired
    private AzureDBService azureDBService;

    @Autowired
    private AsyncRestService asyncRestService;

    @PostMapping("/upload")
    public int uploadFile(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        String file_path =  azureStorageService.uploadFile(file);
        int id = azureDBService.createEntry(file_path);
        Document d = new Document();
        d.setId(String.valueOf(id));
        asyncRestService.makeAsyncPostCall("http://localhost:8000/generateSearchablePDF", d);
        Thread.sleep(10000);
        return id;
    }
}