package net.branium.utils;

import com.google.common.io.Files;
import net.branium.constants.ApplicationConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

class FileUtilsTests {


    @Test
    void testGetFileFromClassPath() throws IOException {
//        Resource resource = new ClassPathResource("/static/images/avatar/default.jpg");
//        File defaultImageFile = resource.getFile();
//        String defaultImageFileName = defaultImageFile.getName();
//
//        if (defaultImageFile.exists()) {
//            Path path = Path.of(ApplicationConstants.AVATAR_FOLDER_PATH);
//            String resolvePath = UUID.randomUUID() + "-" + RandomGenerateUtils.randomAlphanumericString(10) + defaultImageFileName;
//            Path afterResolvePath = path.resolve(resolvePath);
//            Files.copy(resource.getInputStream(), afterResolvePath, StandardCopyOption.REPLACE_EXISTING);
//        }

        String fileName = "hm.m.jpg";
        System.out.println(Files.getFileExtension(fileName));
    }
}
