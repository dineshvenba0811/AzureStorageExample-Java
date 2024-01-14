package com.dinesh.azure.samples.storageaccount.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlobServiceImpl implements BlobService {
    @Value("${spring.cloud.azure.storage.blob.endpoint}")
    private String connectionString;
    String localPath = "./data/";
    String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
    String fileNameDownloaded = "quickstart" + java.util.UUID.randomUUID() + "downloaded.txt";
    @Override
    public BlobServiceClient createConnection() {
        // Create a BlobServiceClient object using a connection string
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
      return blobServiceClient;
    }

    @Override
    public BlobContainerClient createContainer() {
        BlobServiceClient blobServiceClient=createConnection();
        // Create a unique name for the container
        String containerName = "quickstartblobs" + java.util.UUID.randomUUID();
        // Create the container and return a container client object
        return blobServiceClient.createBlobContainer(containerName);
    }

    @Override
    public void  uploadFileToStorageAccount() {
        BlobContainerClient blobContainerClient=createContainer();
        // Create a local file in the ./data/ directory for uploading and downloading

        BlobClient blobClient=blobContainerClient.getBlobClient(fileName);
        // Write text to the file
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(localPath + fileName, true);
            writer.write("Hello, World!");
            writer.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        blobClient.uploadFromFile(localPath+fileName);
    }

    @Override
    public void listAllBlobsInContainer() {
        BlobContainerClient blobContainerClient=createContainer();
        PagedIterable<BlobItem> blobItem=blobContainerClient.listBlobs();
        blobItem.stream().forEach(blob -> System.out.println(blob.getName()));
    }

    @Override
    public void downloadBlobs() {
        BlobContainerClient blobContainerClient=createContainer();
        BlobClient blobClient=blobContainerClient.getBlobClient(fileName);
        blobClient.downloadToFile(fileNameDownloaded);
    }

    @Override
    public void deleteContainer() {
        BlobContainerClient blobContainerClient=createContainer();
        BlobClient blobClient=blobContainerClient.getBlobClient(fileName);
        blobClient.delete();
        // adding mete data
        Map<String,String> metadataMap=new HashMap<>();
        metadataMap.put("key","1");
        blobClient.setMetadata(metadataMap);
        // getting values from metadata
        BlobProperties blobProperties=blobClient.getProperties();
        Map<String,String> metadataMapretival= blobProperties.getMetadata();

    }
}
