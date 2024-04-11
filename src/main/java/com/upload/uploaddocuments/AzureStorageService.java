package com.upload.uploaddocuments;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class AzureStorageService {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    public String uploadFile(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        //uuid.toString()
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;", accountName, accountKey);
        BlobClient blobClient = new BlobClientBuilder().connectionString(connectionString)
                .containerName("testcontainer")
                .blobName(uuid.toString()+"_"+file.getOriginalFilename())
                .buildClient();

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }
}