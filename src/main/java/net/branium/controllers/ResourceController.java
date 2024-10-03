package net.branium.controllers;

import com.google.common.net.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.resource.ResourceResponse;
import net.branium.services.ResourceService;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
