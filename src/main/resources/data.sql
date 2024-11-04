-- Inserting data into USER table
INSERT INTO "user" (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, DOB, IMAGE, PHONE_NUMBER, CREATED_AT)
VALUES ('fa211ceb-be09-4370-9a98-a55ea8fed578',
        'hntrnn12@gmail.com',
        '$2a$10$B3MFJWFT04c9hSeqy2TseurKFoADyeHG3yzDOuWXUNUGruerDsqBK',
        'Viet Han',
        'Trinh',
        1,
        1,
        TO_DATE('2003-12-02', 'YYYY-MM-DD'),
        NULL,
        '0768701056',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO "user" (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, DOB, IMAGE, PHONE_NUMBER, CREATED_AT)
VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34',
        'hntrnn19@gmail.com',
        '$2a$10$Z9mB1/tSfBLpcxX.Y4.KDeDNoqNivdRgsepjIEgeH3AmtBovDtPIa',
        'Linh',
        'Khanh',
        1,
        0,
        TO_DATE('2001-10-06', 'YYYY-MM-DD'),
        'riftfWen.jpg',
        '0987666543',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'));

-- Inserting data into ROLE table
INSERT INTO "role" (NAME)
VALUES ('ADMIN');

INSERT INTO "role" (NAME)
VALUES ('STUDENT');

-- Inserting data into USER_ROLE table
INSERT INTO "user_role" (USER_ID, ROLE_NAME)
VALUES ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'ADMIN');

INSERT INTO "user_role" (USER_ID, ROLE_NAME)
VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 'STUDENT');

-- Inserting data into CATEGORY table
INSERT INTO "category" (title, image, created_at, updated_at)
SELECT 'Java', 'FR12KyLv.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'C++', 'LvHa6smM.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'C#', 'eeIwf2xZ.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'C', 'YiqD8kFp.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'Python', 'TpwlYG38.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'Android', 'OB7LSnIF.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'Git', 'EgpNjhc6.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'Flutter', 'kMsacbbD.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual
UNION ALL
SELECT 'SQL', '7m3lwHvU.jpg', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL
FROM dual;

-- Inserting data into COURSE table
INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Pro Flutter 2025',
        'XgYqkzQJ.jpg',
        'Khóa học Pro Flutter sẽ cung cấp cho bạn các kiến thức và kĩ năng...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        4499000,
        1999000,
        344,
        234,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        8);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Khóa học lập trình Java cơ bản',
        'IypolLPp.jpg',
        'Khóa học này cung cấp cho các bạn kiến thức từ cơ bản của ngôn ngữ lập trình Java.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        399000,
        349000,
        554,
        234,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        1);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Lập trình C cho người mới',
        'NvosnH7x.jpg',
        'Đây là khóa học lập trình HƯỚNG CẤU TRÚC dựa trên nền tảng ngôn ngữ lập trình C.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        899000, 520000,
        123,
        45,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        4);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Android Jetpack Compose',
        'KI7iFcji.jpg',
        'Khóa học này sẽ hướng dẫn bạn tạo ứng dụng Android hoàn chỉnh...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        899000,
        520000,
        234,
        123,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        6);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Android Kotlin Thực Chiến',
        'As3xxLM1.jpg',
        'Khóa học này sẽ hướng dẫn bạn THỰC HÀNH tạo ứng dụng Android native...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        2199000,
        1499000,
        789,
        345,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        6);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Git Cơ Bản & Nâng Cao',
        'dXCDEoao.jpg',
        'Nội dung khóa học này sẽ hướng dẫn bạn sử dụng các lệnh trong công cụ...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        399000,
        349000,
        987,
        567,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        7);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('CSDL SQL từ cơ bản đến nâng cao', 'mP9nbbX3.jpg',
        'Nội dung khóa học SQL từ cơ bản đến nâng cao cung cấp cho bạn...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1249000,
        649000,
        1874,
        567,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        9);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Lập trình C# OOP từ cơ bản đến nâng cao',
        'wTJPergu.jpg',
        'Đây là khóa học lập trình HƯỚNG ĐỐI TƯỢNG trên nền tảng C#.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1799000,
        729000,
        1344,
        456,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        3);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Cấu trúc dữ liệu và Giải thuật',
        '3IY3X2sK.jpg',
        'Đây là khóa học cấu trúc dữ liệu và giải thuật với C++.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1799000,
        729000,
        2345,
        500,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        2);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Cấu trúc dữ liệu và Giải thuật với Java',
        'XoRrONX9.jpg',
        'Đây là khóa học cấu trúc dữ liệu và giải thuật với Java.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1299000,
        599000,
        1234,
        245,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        1);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Cấu trúc dữ liệu và Giải thuật với Python',
        'IIxQqy9w.jpg',
        'Đây là khóa học cấu trúc dữ liệu và giải thuật với Python.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1799000,
        729000,
        456,
        123,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        5);

INSERT INTO "course" (title, image, short_description, full_description, price, discount_price, study_count, buy_count,
                      created_at, updated_at, category_id)
VALUES ('Lập trình C++ OOP từ cơ bản đến nâng cao',
        '9Cy7M42S.jpg',
        'Đây là khóa học lập trình HƯỚNG ĐỐI TƯỢNG dựa trên nền tảng ngôn ngữ lập trình C++.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1199000,
        649000,
        987,
        234,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL,
        2);

-- Inserting data into SECTION table
INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 1. Nhập môn và cài đặt', 1, 1, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 2. Ngôn ngữ lập trình Dart cơ bản', 1, 2, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 3. Ngôn ngữ lập trình Dart nâng cao', 1, 3,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 4. Flutter Widget', 1, 4, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 5. Flutter Layout', 1, 5, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 6. Kiến trúc ứng dụng và giao diện', 1, 6, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 7. Tương tác với ứng dụng', 1, 7, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 8. Xử lý đa phương tiện', 1, 8, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 9. Các hiệu ứng và chuyển đổi', 1, 9, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 10. Đa luồng, bất đồng bộ', 1, 10, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 1: Chuẩn bị trước khi học', 2, 1, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 2: Nhập môn', 2, 2, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 3: Các cấu trúc ra quyết định và vòng lặp', 2, 3,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 4: Phương thức trong Java', 2, 4, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 5: Mảng trong ngôn ngữ lập trình Java', 2, 5,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 1: Chuẩn bị trước khi học', 3, 1, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 2: Nhập môn lập trình C', 3, 2, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 3: Cấu trúc ra quyết định & vòng lặp', 3, 3,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 4: Các hàm - Functions', 3, 4, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 5: Cấu trúc dữ liệu mảng - Arrays', 3, 5, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 6: Con trỏ và địa chỉ của biến', 3, 6, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "section" (title, course_id, ord, created_at, updated_at)
VALUES ('Chương 7: Kí tự và xâu kí tự', 3, 7, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);


-- Inserting data into LECTURE table
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.1. Cài đặt Android Studio cho máy Windows', 1, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.2. Cài đặt Android Studio cho máy Mac', 1, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.3. Cài đặt Dart SDK và Flutter SDK', 1, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.4. Cài đặt Flutter cho máy Mac', 1, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.5. Cấu hình máy ảo iOS', 1, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.6. Nội quy sử dụng khóa học', 1, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.7. Hướng dẫn Coding conventions', 1, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.8. Hướng dẫn về viết tài liệu', 1, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.1. Nhập môn', 2, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.2. Các biến và hằng số', 2, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.3. Các kiểu dữ liệu cơ bản', 2, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.4. Comment trong Dart', 2, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.5. Các toán tử', 2, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.6. Các keyword', 2, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.7. Thư viện và import', 2, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.8. Các lệnh rẽ nhánh', 2, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.9. Vòng lặp', 2, 9, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.10. Kiểu List', 2, 10, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.11. Hàm và tham số của hàm', 2, 11, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.12. Kiểu Set', 2, 12, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 2', 2, 13, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.1. Lớp và đối tượng', 3, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.2. Các constructor', 3, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.3. Các thành phần static', 3, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.4. Tính chất kế thừa', 3, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.5. Tính chất trừu tượng', 3, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.6. interface', 3, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.7. Nạp chồng toán tử', 3, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.8. Mixin', 3, 8, 'VIDEO', 'pzIeMntY.mp4', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 3', 3, 9, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.1. Nhập môn', 4, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.2. Hiển thị văn bản với Text', 4, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.3. Sử dụng Button', 4, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.4. Sử dụng TextField', 4, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.5. Hiển thị ảnh với Image', 4, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.6. Tìm hiểu Scaffold', 4, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.7. Vòng đời của Widget Flutter', 4, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.8. Tìm hiểu IconButton', 4, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 4', 4, 9, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.1. Giới thiệu', 5, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.2. Center', 5, 2, 'VIDEO', 'pzIeMntY.mp4', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.3. Padding', 5, 3, 'VIDEO', 'pzIeMntY.mp4', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.4. Row', 5, 4, 'VIDEO', 'pzIeMntY.mp4', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.5. Column', 5, 5, 'VIDEO', 'pzIeMntY.mp4', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.6. Container', 5, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.7. GridView', 5, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.8. ListView', 5, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.9. AdapterView', 5, 9, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 5', 5, 10, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.1. Những điều cần chuẩn bị trước khi học', 11, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.2. Cài đặt công cụ IntelliJ IDEA, JDK', 11, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.3. Nhóm và trang Facebook hỗ trợ học tập của Branium', 11, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.4. Cách học lập trình hiệu quả', 11, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.1: Nhập môn ngôn ngữ lập trình Java', 12, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.2: Các kiểu dữ liệu trong Java', 12, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.3: Các biến trong Java', 12, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.4: Các toán tử', 12, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.5: Thao tác ép kiểu, làm tròn số', 12, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.6: Lớp String', 12, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.7: Kiểu tự suy luận', 12, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.8: Lớp Math', 12, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.9. Các định dạng nhập xuất dữ liệu trong Java', 12, 9, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.10. StringBuilder và StringBuffer', 12, 10, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 2 - nhập môn java', 12, 11, 'QUIZ', NULL,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.1: Cấu trúc ra quyết định if-else', 13, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.2: Cấu trúc ra quyết định switch', 13, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.3: Toán tử ba ngôi', 13, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.4: Thực hành vẽ sơ đồ khối cho chương trình', 13, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.5: Vòng lặp for', 13, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.6: Vòng lặp while, do-while', 13, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.7: Vòng lặp lồng nhau', 13, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.8: Vòng lặp vô hạn, lệnh break, continue', 13, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.9: Debugging-gỡ lỗi trong lập trình Java', 13, 9, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 3 - cấu trúc quyết định', 13, 10, 'QUIZ', NULL,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.1: Tổng quan về phương thức', 14, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.2: Nạp chồng phương thức', 14, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.3: Biểu thức switch', 14, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.4: Phương thức đệ quy', 14, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 4 - Phương thức trong java', 14, 5, 'QUIZ', NULL,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.1: Mảng 1 chiều và vòng lặp foreach', 15, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.2: Thực hành sử dụng mảng và công cụ IntelliJ', 15, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.3: Sắp xếp các phần tử mảng', 15, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.4: Tìm kiếm trong mảng', 15, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.5: Mảng nhiều chiều', 15, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 5 - mảng trong java', 15, 6, 'QUIZ', NULL,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.1. Cài đặt Công cụ IDE để Học Lập trình C', 16, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.2. Cách học lập trình C hiệu quả', 16, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.3. Nhóm hỗ trợ học tập lập trình C/C++ của Branium', 16, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.4. Cài Visual Studio Code Cho máy Mac', 16, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.5. Thay đổi giao diện web', 16, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 1.6. Hướng dẫn về viết tài liệu', 16, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.1. Giới thiệu ngôn ngữ lập trình C và cấu trúc chung của máy tính', 17, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.2. Chương trình C đầu tiên', 17, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.3. Cộng hai số nguyên', 17, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.4. Tìm hiểu về các biến', 17, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.5. Tìm hiểu về các kiểu dữ liệu', 17, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.6. Minh họa nhập liệu và kiểu dữ liệu', 17, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 2.7. Tìm hiểu các phép toán', 17, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 2', 17, 8, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.1. Giới thiệu các cấu trúc ra quyết định', 18, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.2 Cấu trúc if-else', 18, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.3. Cấu trúc switch-case', 18, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.4. Chuẩn bị gì trước khi code', 18, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.5. Vòng lặp for', 18, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.6. Vòng lặp while và do-while', 18, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.7. Lệnh break, continue và nested loop', 18, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 3.8. Hướng dẫn gỡ lỗi-Debugging', 18, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 3 - cấu trúc quyết định', 18, 9, 'QUIZ', NULL,
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.1. Tìm hiểu về hàm', 19, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.2. Tìm hiểu thư viện math.h', 19, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.3. Các hàm nguyên mẫu', 19, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.4. Gọi hàm bằng truyền tham chiếu và truyền giá trị', 19, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.5. Hàm sinh số ngẫu nhiên', 19, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.6. Quy tắc phạm vi của một định danh', 19, 6, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.7. Hàm đệ quy', 19, 7, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 4.8. So sánh giữa đệ quy và vòng lặp', 19, 8, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 4', 19, 9, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);

INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.1. Mảng một chiều', 20, 1, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.2. Truyền mảng vào hàm', 20, 2, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.3. Sắp xếp các phần tử trong mảng', 20, 3, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.4. Tìm kiếm trong mảng', 20, 4, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài 5.5. Mảng hai chiều', 20, 5, 'VIDEO', 'pzIeMntY.mp4',
        TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), NULL);
INSERT INTO "lecture" (title, section_id, ord, type, resources, created_at, updated_at)
VALUES ('Bài kiểm tra cuối chương 5', 20, 6, 'QUIZ', NULL, TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'),
        NULL);


-- Inserting data into ENROLLMENT table
INSERT INTO "enrollment" (enrolled_at, user_id, course_id) VALUES (TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 1);
INSERT INTO "enrollment" (enrolled_at, user_id, course_id) VALUES (TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 2);
INSERT INTO "enrollment" (enrolled_at, user_id, course_id) VALUES (TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 3);

-- Inserting data into CART table
INSERT INTO "cart" (id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34');

-- Inserting data into CART_ITEM table
INSERT INTO "cart_item" (cart_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 5);
INSERT INTO "cart_item" (cart_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 6);
INSERT INTO "cart_item" (cart_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 7);
INSERT INTO "cart_item" (cart_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 8);
INSERT INTO "cart_item" (cart_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 9);

-- Inserting data into WISH_LIST table
INSERT INTO "wish_list" (id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34');

-- Inserting data into WISH_LIST_ITEM table
INSERT INTO "wish_list_item" (wish_list_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 10);
INSERT INTO "wish_list_item" (wish_list_id, course_id) VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 11);

-- Inserting data into ORDER table
INSERT INTO "order" (order_status, total_price, total_discount_price, stripe_payment_intent_id, created_at, user_id)
VALUES ('SUCCEEDED', 3798556, 3798556, '234', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS'), '4a39e0fe-27a0-4789-8ba2-b7e49677db34');

-- Inserting data into ORDER_DETAIL table
INSERT INTO "order_detail" (order_id, course_id) VALUES (1, 1);
INSERT INTO "order_detail" (order_id, course_id) VALUES (1, 2);
INSERT INTO "order_detail" (order_id, course_id) VALUES (1, 3);
INSERT INTO "order_detail" (order_id, course_id) VALUES (1, 4);




