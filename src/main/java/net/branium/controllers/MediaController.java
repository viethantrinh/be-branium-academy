package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.constants.ApplicationConstants;
import net.branium.utils.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class MediaController {

    @GetMapping(path = "/downloadFile/{code}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "code") String fileCode) throws IOException {
        Resource resource = FileUtils.getFileAsResource(fileCode, Path.of(ApplicationConstants.AVATAR_FOLDER_PATH));
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
