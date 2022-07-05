package com.gamejigi.attend.model.service;

import javax.servlet.http.Part;
import java.io.*;
import java.util.Random;

public class FileServiceImpl implements FileService {

    @Override
    public Boolean uploadImage(Part filePart, String absolutePath, String fileName) {
        Boolean result = false;
        try {
            InputStream fis = filePart.getInputStream(); // 바이너리 input stream
            String filePath = absolutePath + File.separator + fileName;// 저장될 파일 경로
            FileOutputStream fos = new FileOutputStream(filePath); // 바이너리 out stream

            int size = 0;
            byte[] buf = new byte[1024]; // 1mb buffer
            while((size = fis.read(buf)) != -1) { // 파일 read
                fos.write(buf, 0, size); // 파일 write
            }

            fos.close();
            fis.close();
            result = true;
        } catch (IOException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public String makeFileName(Part filePart) {
        String fileName = filePart.getSubmittedFileName(); // 파일명

        // 10자리 난수 생성
        int count = 10;
        String randomStr = "";
        Random random = new Random();
        for (int i=0; i < count; i++) {
            randomStr += Integer.toString(random.nextInt(9)); // 0 ~ 9 랜덤값
        }

        // 파일명 앞에 붙임
        fileName = randomStr + "_" +  fileName;

        return fileName;
    }

    @Override
    public void deleteFile(String fileName, String absolutePath) {
        String filePath = absolutePath + File.separator + fileName;
        File file = new File(filePath);
        if(file.exists()) { // 파일이 서버에 존재하는지 다시 확인
            file.delete(); // 파일 삭제
        }
    }
}
