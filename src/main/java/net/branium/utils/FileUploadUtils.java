package net.branium.utils;

import lombok.RequiredArgsConstructor;
import net.branium.constants.ApplicationConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@RequiredArgsConstructor
public class FileUploadUtils {
    private ResourceLoader resourceLoader;

    public static String saveUserAvatarFile(String userId, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Path.of(ApplicationConstants.AVATAR_FOLDER_PATH);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileExtension = com.google.common.io.Files.getFileExtension(fileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }

        String randomCode = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream fileStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(userId + "-" + randomCode + fileExtension);
            Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + multipartFile.getName());
        }

        return userId + randomCode;
    }
}
