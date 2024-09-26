package net.branium.dtos.file;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ImageResponse extends FileResponse {
    public ImageResponse(String fileName, String downloadUrl, long size) {
        super(fileName, downloadUrl, size);
    }
}
