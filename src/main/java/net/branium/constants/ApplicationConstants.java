package net.branium.constants;

public class ApplicationConstants {
    public static final String BA_WP_BASE_URL = "https://braniumacademy.net";
    public static final String BA_BASE_URL = "http://localhost:8080/branium-academy/api/v1";

    public static String USER_IMAGE_RESOURCE_PATH = "src/main/resources/static/images/user";
    public static String COURSE_IMAGE_RESOURCE_PATH = "src/main/resources/static/images/course";
    public static String LECTURE_VIDEO_RESOURCE_PATH = "src/main/resources/static/videos/lecture";
    public static String CATEGORY_IMAGE_RESOURCE_PATH = "src/main/resources/static/images/category";

    // change this to 10.0.0.2 if the android is emulator.
    // In case using external android device => change to the local ip of dev's device (eg: 192.168.1.7)
    public static String RESOURCE_HOST = "localhost".concat(":8080");


}

