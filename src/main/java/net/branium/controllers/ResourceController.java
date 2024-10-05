package net.branium.controllers;

import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import net.branium.services.ResourceService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping(path = "/images/{file_code}")
    public ResponseEntity<?> getImageResource(@PathVariable(name = "file_code") String fileCode) {
        Resource resource = resourceService.getResourceByFileCode(fileCode);

        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping(path = "/videos/{file_code}")
    public ResponseEntity<?> getVideoResource(@PathVariable(name = "file_code") String fileCode) {
        Resource resource = resourceService.getResourceByFileCode(fileCode);

        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
