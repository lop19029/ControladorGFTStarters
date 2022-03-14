package com.gft.ControleStarterMVC.utils;
/**
 * Creates directory if not exists, and saves uploaded file from MultipartFile object
 * to a file in system.
 *
 * Credits: https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
 */

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Não foi possivel salvar o arquivo: " + fileName, ioe);
        }
    }
}
