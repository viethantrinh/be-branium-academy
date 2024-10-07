package net.branium.utils;

import net.branium.constants.ApplicationConstants;
import net.branium.domains.ResourceType;

import java.util.concurrent.TimeUnit;

public class ResourceUtils {

    public static String buildDownloadUrl(String fileCode, ResourceType type) {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type) {
            case IMAGE -> urlBuilder.append("http://").append(ApplicationConstants.RESOURCE_HOST)
                    .append("/branium-academy/api/v1/resources/images").append("/").append(fileCode);
            case VIDEO -> urlBuilder.append("http://").append(ApplicationConstants.RESOURCE_HOST)
                    .append("/branium-academy/api/v1/resources/videos").append("/").append(fileCode);
        }
        return urlBuilder.toString();
    }


    // Format duration as "xx hours xx minutes"
    public static String formatVideoDurationToHoursAndMinutes(long totalSeconds) {
        long hours = TimeUnit.SECONDS.toHours(totalSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - TimeUnit.HOURS.toMinutes(hours);
//        long seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalSeconds));
        return String.format("%02d hours %02d minutes", hours, minutes);
    }

    // Format duration as "mm:ss"
    public static String formatVideoDurationToMMSS(long totalSeconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds);
        long seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
