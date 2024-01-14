package com.dinesh.azure.samples.storageaccount.controller;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.dinesh.azure.samples.storageaccount.service.BlobService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@RestController
@RequestMapping("blob")
public class BlobController {

    // Retrieve the connection string for use with the application.

    private BlobService blobService;
    @GetMapping("/createConnection")
    public BlobServiceClient readBlobFile() throws IOException {
        return blobService.createConnection();
    }



}
