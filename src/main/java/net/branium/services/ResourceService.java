package net.branium.services;

import net.branium.domains.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    String uploadUserImage(User user, MultipartFile file);
    void delete(String fileName, String path);
    Resource getResourceByFileCode(String fileCode);
}
