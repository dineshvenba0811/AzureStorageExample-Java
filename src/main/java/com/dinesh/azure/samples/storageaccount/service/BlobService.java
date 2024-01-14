package com.dinesh.azure.samples.storageaccount.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;

import java.util.List;

public interface BlobService {

    BlobServiceClient createConnection();

    BlobContainerClient createContainer();

    void uploadFileToStorageAccount();

    void listAllBlobsInContainer();

    void downloadBlobs();

    void deleteContainer();

}
