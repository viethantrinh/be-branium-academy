package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.User;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.UserRepository;
import net.branium.services.ResourceService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.simplifyPath;
import static net.branium.constants.ApplicationConstants.USER_IMAGE_RESOURCE_PATH;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final UserRepository userRepo;

    @Override
    public String uploadUserImage(User user, MultipartFile file) {
        Path path = Paths.get(simplifyPath(USER_IMAGE_RESOURCE_PATH + "/" + user.getId()));

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        InputStream fileStream = null;
        String fullFileName = "";

        try {
            fileStream = file.getInputStream();
            String fileNameExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

            // check if this user already have the image or not.
            // If yes so update the current in the folder and update new image in there
            if (user.getImage() == null) {
                String randomFileCode = RandomStringUtils.randomAlphanumeric(8);
                fullFileName = randomFileCode.concat(".").concat(fileNameExtension);
                user.setImage(fullFileName);
                userRepo.save(user);
            } else {
                fullFileName = user.getImage();
            }

            Path resolvedPath = path.resolve(fullFileName);
            Files.copy(fileStream, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
            fileStream.close();
        } catch (IOException ex) {
            throw new RuntimeException("Something wrong with the file stream!");
        }

        return fullFileName.substring(0, 8);
    }

    @Override
    public void delete(String fileName, String path) {
        Path delPath = Path.of(simplifyPath(path));
        Stream<Path> pathStream = null;
        try {
            pathStream = Files.list(delPath);
            Set<Path> paths = pathStream.collect(Collectors.toSet());
            for (Path filePath : paths) {
                if (filePath.getFileName().toString().startsWith(fileName)) {
                    Files.delete(filePath);
                }
            }
            pathStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource getResourceByFileCode(String fileCode) {
        String staticResourcePath = "src/main/resources/static/";
        Path path = Path.of(staticResourcePath);
        Path foundFile = null;

        try (Stream<Path> pathStream = Files.walk(path)) {
            Set<Path> filePaths = pathStream.collect(Collectors.toSet());
            for (Path filePath : filePaths) {
                if (Files.isRegularFile(filePath) && filePath.getFileName().toString().startsWith(fileCode)) {
                    foundFile = filePath;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (foundFile == null) {
            throw new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED);
        }

        UrlResource urlResource;

        try {
            urlResource = new UrlResource(foundFile.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return urlResource;
    }
}
