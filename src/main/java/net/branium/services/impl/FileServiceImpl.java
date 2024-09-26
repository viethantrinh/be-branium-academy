package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.file.FileResponse;
import net.branium.repositories.UserRepository;
import net.branium.services.FileService;
import net.branium.utils.FileUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final UserRepository userRepo;

    @Override
    public FileResponse uploadUserAvatarById(String id, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public FileResponse getUserAvatarById(String id, MultipartFile multipartFile) {
        return null;
    }
}
