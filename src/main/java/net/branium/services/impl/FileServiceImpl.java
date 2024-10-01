package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.constants.ApplicationConstants;
import net.branium.domains.User;
import net.branium.dtos.file.FileResponse;
import net.branium.dtos.file.ImageResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.UserRepository;
import net.branium.services.FileService;
import net.branium.utils.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final UserRepository userRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public FileResponse uploadUserAvatarById(String id, MultipartFile multipartFile) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // check if user already have avatar and not the default avatar => then delete the current avatar (the name with 8 random code not the default)
        // in folder and replace with the new one
        if (user.getImage().contains(user.getId()) && !user.getImage().startsWith("default.jpg")) {
            FileUtils.deleteUserAvatarFile(user.getId());
        }

        String fileName;
        try {
            fileName = FileUtils.saveUserAvatarFile(user.getId(), multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        user.setImage(fileName);
        User savedUser = userRepo.save(user);

        FileResponse response = ImageResponse.builder()
                .fileName(savedUser.getImage())
                .downloadUrl(ApplicationConstants.DOWNLOAD_API_PATH + "/" + savedUser.getImage().substring(0, 8))
                .size(multipartFile.getSize())
                .build();

        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public FileResponse getUserAvatarById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        String uri;
        if (user.getImage().contains("default.jpg")) {
            uri = "default";
        } else {
            uri = user.getImage().substring(0, 8);
        }

        Path path = Path.of(ApplicationConstants.AVATAR_FOLDER_PATH).resolve(user.getImage());
        long fileSize = path.toFile().length();

        FileResponse response = ImageResponse.builder()
                .fileName(user.getImage())
                .downloadUrl(ApplicationConstants.DOWNLOAD_API_PATH + "/" + uri)
                .size(fileSize)
                .build();
        return response;
    }

    @Override
    public FileResponse uploadStudentAvatar(MultipartFile multipartFile, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // check if user already have avatar and not the default avatar => then delete the current avatar (the name with 8 random code not the default)
        // in folder and replace with the new one
        if (user.getImage().contains(user.getId()) && !user.getImage().startsWith("default.jpg")) {
            FileUtils.deleteUserAvatarFile(user.getId());
        }

        String fileName;
        try {
            fileName = FileUtils.saveUserAvatarFile(user.getId(), multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        user.setImage(fileName);
        User savedUser = userRepo.save(user);

        FileResponse response = ImageResponse.builder()
                .fileName(savedUser.getImage())
                .downloadUrl(ApplicationConstants.DOWNLOAD_API_PATH + "/" + savedUser.getImage().substring(0, 8))
                .size(multipartFile.getSize())
                .build();

        return response;
    }

    @Override
    public FileResponse getStudentAvatar(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        String uri;
        if (user.getImage().contains("default.jpg")) {
            uri = "default";
        } else {
            uri = user.getImage().substring(0, 8);
        }

        Path path = Path.of(ApplicationConstants.AVATAR_FOLDER_PATH).resolve(user.getImage());
        long fileSize = path.toFile().length();

        FileResponse response = ImageResponse.builder()
                .fileName(user.getImage())
                .downloadUrl(ApplicationConstants.DOWNLOAD_API_PATH + "/" + uri)
                .size(fileSize)
                .build();
        return response;
    }
}
