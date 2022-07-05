package com.gamejigi.attend.model.service;

import javax.servlet.http.Part;

public interface FileService {
    Boolean uploadImage(Part filePart, String absolutePath, String fileName);
    String makeFileName(Part filePart);
    void deleteFile(String fileName, String absolutePath);
}
