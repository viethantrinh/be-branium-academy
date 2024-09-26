package net.branium.utils;

import lombok.RequiredArgsConstructor;
import net.branium.constants.ApplicationConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class FileUtils {
    public static String saveUserAvatarFile(String userId, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Path.of(ApplicationConstants.AVATAR_FOLDER_PATH);
        if (multipartFile.getOriginalFilename() == null) {
            return null;
        }

        String fileExtension = "." + com.google.common.io.Files
                .getFileExtension(StringUtils.cleanPath(multipartFile.getOriginalFilename()));

        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }

        String randomCode = RandomStringUtils.randomAlphanumeric(8);
        StringBuilder fileName = new StringBuilder();
        try (InputStream fileStream = multipartFile.getInputStream()) {
            fileName.append(randomCode).append("-").append(userId).append(fileExtension);
            Path filePath = uploadPath.resolve(fileName.toString());
            Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + multipartFile.getOriginalFilename());
        }

        return fileName.toString();
    }


    public static void deleteUserAvatarFile(String userId) {
        try {
            Path path = Path.of(ApplicationConstants.AVATAR_FOLDER_PATH);
            Set<Path> files = Files.list(path).collect(Collectors.toSet());
            for (Path file : files) {
                if (file.getFileName().toString().contains(userId)) {
                    Files.delete(file);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Resource getFileAsResource(String fileCode, Path path) throws IOException {
        Path foundedFile = null;
        Set<Path> files = Files.list(path).collect(Collectors.toSet());
        for (Path file : files) {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundedFile = file;
            }
        }
        if (foundedFile == null) {
            return null;
        }
        System.out.println(foundedFile.toAbsolutePath());
        System.out.println(foundedFile.toUri());
        System.out.println(new UrlResource(foundedFile.toUri()));
        return new UrlResource(foundedFile.toUri());
    }

}
