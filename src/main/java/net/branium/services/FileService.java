package net.branium.services;

import net.branium.dtos.file.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileResponse uploadUserAvatarById(String id ,MultipartFile multipartFile);
    FileResponse getUserAvatarById(String id);
    FileResponse uploadStudentAvatar(MultipartFile multipartFile, Authentication authentication);
    FileResponse getStudentAvatar(Authentication authentication);
}
