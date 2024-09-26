package net.branium.services;

import net.branium.dtos.file.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileResponse uploadUserAvatarById(String id ,MultipartFile multipartFile);
    FileResponse getUserAvatarById(String id);
}
