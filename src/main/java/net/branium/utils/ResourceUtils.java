package net.branium.utils;

import net.branium.constants.ApplicationConstants;
import net.branium.domains.ResourceType;

public class ResourceUtils {

    public static String buildDownloadUrl(String fileCode, ResourceType type) {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type) {
            case IMAGE -> urlBuilder.append("http://").append(ApplicationConstants.RESOURCE_HOST)
                    .append("/branium-academy/api/v1/resources/images").append("/").append(fileCode);
            case VIDEO -> urlBuilder.append("http://").append(ApplicationConstants.RESOURCE_HOST)
                    .append("/branium-academy/api/v1/resources/video").append("/").append(fileCode);
        }
        return urlBuilder.toString();
    }
}
