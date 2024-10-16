-- Inserting data into USER table
INSERT INTO `USER` (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, DOB, IMAGE,
                    PHONE_NUMBER, CREATED_AT)
VALUES ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'hntrnn12@gmail.com',
        '$2a$10$B3MFJWFT04c9hSeqy2TseurKFoADyeHG3yzDOuWXUNUGruerDsqBK',
        'Viet Han', 'Trinh', 1, 1, '2003-12-02', NULL,
        '0768701056', '2024-09-12 07:08:18');

INSERT INTO `USER` (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, DOB, IMAGE,
                    PHONE_NUMBER, CREATED_AT)
VALUES ('9a39e0ff-27a0-4789-8ba2-b7e49677db34', 'hntrnn195@gmail.com',
        '$2a$10$Z9mB1/tSfBLpcxX.Y4.KDeDNoqNivdRgsepjIEgeH3AmtBovDtPIa',
        'Mai Anh', 'Do', 1, 0, '2001-10-06', 'rKftfWen.jpg',
        '0987666543', '2024-09-12 07:08:18');

INSERT INTO `USER` (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, DOB, IMAGE,
                    PHONE_NUMBER, CREATED_AT)
VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 'hntrnn19@gmail.com',
        '$2a$10$Z9mB1/tSfBLpcxX.Y4.KDeDNoqNivdRgsepjIEgeH3AmtBovDtPIa',
        'Linh', 'Khanh', 1, 0, '2001-10-06', 'riftfWen.jpg',
        '0987666543', '2024-09-12 07:08:18');

-- Inserting data into ROLE table
INSERT INTO `ROLE` (NAME)
VALUES ('ADMIN');

INSERT INTO `ROLE` (NAME)
VALUES ('STUDENT');

-- Inserting data into USER_ROLE table
INSERT INTO `USER_ROLE` (USER_ID, ROLE_NAME)
VALUES ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'ADMIN');

INSERT INTO `USER_ROLE` (USER_ID, ROLE_NAME)
VALUES ('9a39e0ff-27a0-4789-8ba2-b7e49677db34', 'STUDENT');

INSERT INTO `USER_ROLE` (USER_ID, ROLE_NAME)
VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34', 'STUDENT');

-- Inserting data into CATEGORY table
INSERT INTO `CATEGORY` (id, title, image, created_at, updated_at)
VALUES (1, 'Java', 'FR12KyLv.jpg', '2024-09-12 07:08:18', NULL),
       (2, 'C++', 'LvHa6smM.jpg', '2024-09-12 07:08:18', NULL),
       (3, 'C#', 'eeIwf2xZ.jpg', '2024-09-12 07:08:18', NULL),
       (4, 'C', 'YiqD8kFp.jpg', '2024-09-12 07:08:18', NULL),
       (5, 'Python', 'TpwlYG38.jpg', '2024-09-12 07:08:18', NULL),
       (6, 'Android', 'OB7LSnIF.jpg', '2024-09-12 07:08:18', NULL),
       (7, 'Git', 'EgpNjhc6.jpg', '2024-09-12 07:08:18', NULL),
       (8, 'Flutter', 'kMsacbbD.jpg', '2024-09-12 07:08:18', NULL),
       (9, 'SQL', '7m3lwHvU.jpg', '2024-09-12 07:08:18', NULL);

-- Inserting data into COURSE table
INSERT INTO `COURSE` (id, title, image, short_description, full_description, price, discount_price, study_count,
                      buy_count, created_at, updated_at, category_id)
VALUES (1, 'Pro Flutter 2025', 'XgYqkzQJ.jpg',
        'Khóa học Pro Flutter sẽ cung cấp cho bạn các kiến thức và kĩ năng...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        4499000, 1999000,
        344, 234, '2024-09-12 07:08:18', NULL, 8),
       (2, 'Khóa học lập trình Java cơ bản',
        'IypolLPp.jpg',
        'Khóa học này cung cấp cho các bạn kiến thức từ cơ bản của ngôn ngữ lập trình Java.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        399000, 349000,
        554, 234, '2024-09-12 07:08:18', NULL, 1),
       (3, 'Lập trình C cho người mới', 'NvosnH7x.jpg',
        'Đây là khóa học lập trình HƯỚNG CẤU TRÚC dựa trên nền tảng ngôn ngữ lập trình C.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        899000, 520000,
        123, 45, '2024-09-12 07:08:18', NULL, 4),
       (4, 'Android Jetpack Compose', 'KI7iFcji.jpg',
        'Khóa học này sẽ hướng dẫn bạn tạo ứng dụng Android hoàn chỉnh...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        899000, 520000,
        234, 123, '2024-09-12 07:08:18', NULL, 6),
       (5, 'Android Kotlin Thực Chiến',
        'As3xxLM1.jpg',
        'Khóa học này sẽ hướng dẫn bạn THỰC HÀNH tạo ứng dụng Android native...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        2199000, 1499000,
        789, 345, '2024-09-12 07:08:18', NULL, 6),
       (6, 'Git Cơ Bản & Nâng Cao', 'dXCDEoao.jpg',
        'Nội dung khóa học này sẽ hướng dẫn bạn sử dụng các lệnh trong công cụ...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        399000, 349000,
        987, 567, '2024-09-12 07:08:18', NULL, 7),
       (7, 'CSDL SQL từ cơ bản đến nâng cao', 'mP9nbbX3.jpg',
        'Nội dung khóa học SQL từ cơ bản đến nâng cao cung cấp cho bạn...',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1249000, 649000,
        1874, 567, '2024-09-12 07:08:18', NULL, 9),
       (8, 'Lập trình C# OOP từ cơ bản đến nâng cao',
        'wTJPergu.jpg',
        'Đây là khóa học lập trình HƯỚNG ĐỐI TƯỢNG trên nền tảng C#.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1799000, 729000,
        1344, 456, '2024-09-12 07:08:18', NULL, 3),
       (9, 'Cấu trúc dữ liệu và Giải thuật',
        '3IY3X2sK.jpg',
        'Đây là khóa học cấu trúc dữ liệu và giải thuật với C++.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1799000, 729000,
        2345, 500, '2024-09-12 07:08:18', NULL, 2),
       (10, 'Cấu trúc dữ liệu và Giải thuật với Java',
        'XoRrONX9.jpg',
        'Đây là khóa học cấu trúc dữ liệu và giải thuật với Java.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1299000, 599000,
        1234, 245, '2024-09-12 07:08:18', NULL, 1),
       (11, 'Cấu trúc dữ liệu và Giải thuật với Python',
        'IIxQqy9w.jpg',
        'Đây là khóa học cấu trúc dữ liệu và giải thuật với Python.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1799000, 729000,
        456, 123, '2024-09-12 07:08:18', NULL, 5),
       (12, 'Lập trình C++ OOP từ cơ bản đến nâng cao',
        'h9Cy7M42S.jpg',
        'Đây là khóa học lập trình HƯỚNG ĐỐI TƯỢNG dựa trên nền tảng ngôn ngữ lập trình C++.',
        'Khóa học được lồng ghép các yêu cầu nâng cao vào các bài học để bạn có thể đáp ứng được yêu cầu của nhà tuyển dụng ngày càng tăng#Sau khi kết thúc khóa học bạn có thể tự tin đi thực tập, intern, fresher vị trí Mobile App Developer với Flutter#Khóa học sẽ được cập nhật, thay đổi nội dung mới nhất mà không có thông báo trước',
        1199000, 649000,
        987, 234, '2024-09-12 07:08:18', NULL, 2);

-- Inserting data into SECTION table
INSERT INTO `SECTION` (id, title, course_id, ord, created_at, updated_at)
VALUES (1, 'Chương 1. Nhập môn và cài đặt', 1, 1, '2024-09-12 07:08:18', NULL),
       (2, 'Chương 2. Ngôn ngữ lập trình Dart cơ bản', 1, 2, '2024-09-12 07:08:18', NULL),
       (3, 'Chương 3. Ngôn ngữ lập trình Dart nâng cao', 1, 3, '2024-09-12 07:08:18', NULL),
       (4, 'Chương 4. Flutter Widget', 1, 4, '2024-09-12 07:08:18', NULL),
       (5, 'Chương 5. Flutter Layout', 1, 5, '2024-09-12 07:08:18', NULL),
       (6, 'Chương 6. Kiến trúc ứng dụng và giao diện', 1, 6, '2024-09-12 07:08:18', NULL),
       (7, 'Chương 7. Tương tác với ứng dụng', 1, 7, '2024-09-12 07:08:18', NULL),
       (8, 'Chương 8. Xử lý đa phương tiện', 1, 8, '2024-09-12 07:08:18', NULL),
       (9, 'Chương 9. Các hiệu ứng và chuyển đổi', 1, 9, '2024-09-12 07:08:18', NULL),
       (10, 'Chương 10. Đa luồng, bất đồng bộ', 1, 10, '2024-09-12 07:08:18', NULL),

       (11, 'Chương 1: Chuẩn bị trước khi học', 2, 1, '2024-09-12 07:08:18', NULL),
       (12, 'Chương 2: Nhập môn', 2, 2, '2024-09-12 07:08:18', NULL),
       (13, 'Chương 3: Các cấu trúc ra quyết định và vòng lặp', 2, 3, '2024-09-12 07:08:18', NULL),
       (14, 'Chương 4: Phương thức trong Java', 2, 4, '2024-09-12 07:08:18', NULL),
       (15, 'Chương 5: Mảng trong ngôn ngữ lập trình Java', 2, 5, '2024-09-12 07:08:18', NULL),

       (16, 'Chương 1: Chuẩn bị trước khi học', 3, 1, '2024-09-12 07:08:18', NULL),
       (17, 'Chương 2: Nhập môn lập trình C', 3, 2, '2024-09-12 07:08:18', NULL),
       (18, 'Chương 3: Cấu trúc ra quyết định & vòng lặp', 3, 3, '2024-09-12 07:08:18', NULL),
       (19, 'Chương 4: Các hàm - Functions', 3, 4, '2024-09-12 07:08:18', NULL),
       (20, 'Chương 5: Cấu trúc dữ liệu mảng - Arrays', 3, 5, '2024-09-12 07:08:18', NULL),
       (21, 'Chương 6: Con trỏ và địa chỉ của biến', 3, 6, '2024-09-12 07:08:18', NULL),
       (22, 'Chương 7: Kí tự và xâu kí tự', 3, 7, '2024-09-12 07:08:18', NULL);

-- Inserting data into LECTURE table
INSERT INTO `LECTURE` (id, title, section_id, ord, type, resource, created_at, updated_at)
VALUES (1, 'Bài 1.1. Cài đặt Android Studio cho máy Windows', 1, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (2, 'Bài 1.2. Cài đặt Android Studio cho máy Mac', 1, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (3, 'Bài 1.3. Cài đặt Dart SDK và Flutter SDK', 1, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (4, 'Bài 1.4. Cài đặt Flutter cho máy Mac', 1, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (5, 'Bài 1.5. Cấu hình máy ảo iOS', 1, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (6, 'Bài 1.6. Nội quy sử dụng khóa học', 1, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (7, 'Bài 1.7. Hướng dẫn Coding conventions', 1, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (8, 'Bài 1.8. Hướng dẫn về viết tài liệu', 1, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),

       (9, 'Bài 2.1. Nhập môn', 2, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (10, 'Bài 2.2. Các biến và hằng số', 2, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (11, 'Bài 2.3. Các kiểu dữ liệu cơ bản', 2, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (12, 'Bài 2.4. Comment trong Dart', 2, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (13, 'Bài 2.5. Các toán tử', 2, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (14, 'Bài 2.6. Các keyword', 2, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (15, 'Bài 2.7. Thư viện và import', 2, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (16, 'Bài 2.8. Các lệnh rẽ nhánh', 2, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (17, 'Bài 2.9. Vòng lặp', 2, 9, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (18, 'Bài 2.10. Kiểu List', 2, 10, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (19, 'Bài 2.11. Hàm và tham số của hàm', 2, 11, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (20, 'Bài 2.12. Kiểu Set', 2, 12, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (21, 'Bài kiểm tra cuối chương 2', 2, 13, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (22, 'Bài 3.1. Lớp và đối tượng', 3, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (23, 'Bài 3.2. Các constructor', 3, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (24, 'Bài 3.3. Các thành phần static', 3, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (25, 'Bài 3.4. Tính chất kế thừa', 3, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (26, 'Bài 3.5. Tính chất trừu tượng', 3, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (27, 'Bài 3.6. interface', 3, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (28, 'Bài 3.7. Nạp chồng toán tử', 3, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (29, 'Bài 3.8. Mixin', 3, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (30, 'Bài kiểm tra cuối chương 3', 3, 9, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (31, 'Bài 4.1. Nhập môn', 4, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (32, 'Bài 4.2. Hiển thị văn bản với Text', 4, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (33, 'Bài 4.3. Sử dụng Button', 4, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (34, 'Bài 4.4. Sử dụng TextField', 4, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (35, 'Bài 4.5. Hiển thị ảnh với Image', 4, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (36, 'Bài 4.6. Tìm hiểu Scaffold', 4, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (37, 'Bài 4.7. Vòng đời của Widget Flutter', 4, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (38, 'Bài 4.8. Tìm hiểu IconButton', 4, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (39, 'Bài kiểm tra cuối chương 4', 4, 9, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (40, 'Bài 5.1. Giới thiệu', 5, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (41, 'Bài 5.2. Center', 5, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (42, 'Bài 5.3. Padding', 5, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (43, 'Bài 5.4. Row', 5, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (44, 'Bài 5.5. Column', 5, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (45, 'Bài 5.6. Container', 5, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (46, 'Bài 5.7. GridView', 5, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (47, 'Bài 5.8. ListView', 5, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (48, 'Bài 5.9. AdapterView', 5, 9, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (49, 'Bài kiểm tra cuối chương 5', 5, 10, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (50, 'Bài 1.1. Những điều cần chuẩn bị trước khi học', 11, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (51, 'Bài 1.2. Cài đặt công cụ IntelliJ IDEA, JDK', 11, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (52, 'Bài 1.3. Nhóm và trang Facebook hỗ trợ học tập của Branium', 11, 3, 'video', 'pzIeMntY.mp4',
        '2024-09-12 07:08:18', NULL),
       (53, 'Bài 1.4. Cách học lập trình hiệu quả', 11, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),

       (54, 'Bài 2.1: Nhập môn ngôn ngữ lập trình Java', 12, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (55, 'Bài 2.2: Các kiểu dữ liệu trong Java', 12, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (56, 'Bài 2.3: Các biến trong Java', 12, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (57, 'Bài 2.4: Các toán tử', 12, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (58, 'Bài 2.5: Thao tác ép kiểu, làm tròn số', 12, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (59, 'Bài 2.6: Lớp String', 12, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (60, 'Bài 2.7: Kiểu tự suy luận', 12, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (61, 'Bài 2.8: Lớp Math', 12, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (62, 'Bài 2.9. Các định dạng nhập xuất dữ liệu trong Java', 12, 9, 'video', 'pzIeMntY.mp4',
        '2024-09-12 07:08:18', NULL),
       (63, 'Bài 2.10. StringBuilder và StringBuffer', 12, 10, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (64, 'Bài kiểm tra cuối chương 2 - nhập môn java', 12, 11, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (65, 'Bài 3.1: Cấu trúc ra quyết định if-else', 13, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (66, 'Bài 3.2: Cấu trúc ra quyết định switch', 13, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (67, 'Bài 3.3: Toán tử ba ngôi', 13, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (68, 'Bài 3.4: Thực hành vẽ sơ đồ khối cho chương trình', 13, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (69, 'Bài 3.5: Vòng lặp for', 13, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (70, 'Bài 3.6: Vòng lặp while, do-while', 13, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (71, 'Bài 3.7: Vòng lặp lồng nhau', 13, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (72, 'Bài 3.8: Vòng lặp vô hạn, lệnh break, continue', 13, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (73, 'Bài 3.9: Debugging-gỡ lỗi trong lập trình Java', 13, 9, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (74, 'Bài kiểm tra cuối chương 3 - cấu trúc quyết định', 13, 10, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (75, 'Bài 4.1: Tổng quan về phương thức', 14, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (76, 'Bài 4.2: Nạp chồng phương thức', 14, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (77, 'Bài 4.3: Biểu thức switch', 14, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (78, 'Bài 4.4: Phương thức đệ quy', 14, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (79, 'Bài kiểm tra cuối chương 4 - Phương thức trong java', 14, 5, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (80, 'Bài 5.1: Mảng 1 chiều và vòng lặp foreach', 15, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (81, 'Bài 5.2: Thực hành sử dụng mảng và công cụ IntelliJ', 15, 2, 'video', 'pzIeMntY.mp4',
        '2024-09-12 07:08:18', NULL),
       (82, 'Bài 5.3: Sắp xếp các phần tử mảng', 15, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (83, 'Bài 5.4: Tìm kiếm trong mảng', 15, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (84, 'Bài 5.5: Mảng nhiều chiều', 15, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (85, 'Bài kiểm tra cuối chương 5 - mảng trong java', 15, 6, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (86, 'Bài 1.1. Cài đặt Công cụ IDE để Học Lập trình C', 16, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (87, 'Bài 1.2. Cách học lập trình C hiệu quả', 16, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (88, 'Bài 1.3. Nhóm hỗ trợ học tập lập trình C/C++ của Branium', 16, 3, 'video', 'pzIeMntY.mp4',
        '2024-09-12 07:08:18', NULL),
       (89, 'Bài 1.4. Cài Visual Studio Code Cho máy Mac', 16, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (90, 'Bài 1.5. Thay đổi giao diện web', 16, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (91, 'Bài 1.6. Hướng dẫn về viết tài liệu', 16, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),

       (92, 'Bài 2.1. Giới thiệu ngôn ngữ lập trình C và cấu trúc chung của máy tính', 17, 1, 'video', 'pzIeMntY.mp4',
        '2024-09-12 07:08:18', NULL),
       (93, 'Bài 2.2. Chương trình C đầu tiên', 17, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (94, 'Bài 2.3. Cộng hai số nguyên', 17, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (95, 'Bài 2.4. Tìm hiểu về các biến', 17, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (96, 'Bài 2.5. Tìm hiểu về các kiểu dữ liệu', 17, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (97, 'Bài 2.6. Minh họa nhập liệu và kiểu dữ liệu', 17, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (98, 'Bài 2.7. Tìm hiểu các phép toán', 17, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (99, 'Bài kiểm tra cuối chương 2', 17, 8, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (100, 'Bài 3.1. Giới thiệu các cấu trúc ra quyết định', 18, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (101, 'Bài 3.2 Cấu trúc if-else', 18, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (102, 'Bài 3.3. Cấu trúc switch-case', 18, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (103, 'Bài 3.4. Chuẩn bị gì trước khi code', 18, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (104, 'Bài 3.5. Vòng lặp for', 18, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (105, 'Bài 3.6. Vòng lặp while và do-while', 18, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (106, 'Bài 3.7. Lệnh break, continue và nested loop', 18, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18',
        NULL),
       (107, 'Bài 3.8. Hướng dẫn gỡ lỗi-Debugging', 18, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (108, 'Bài kiểm tra cuối chương 3 - cấu trúc quyết định', 18, 9, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (109, 'Bài 4.1. Tìm hiểu về hàm', 19, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (110, 'Bài 4.2. Tìm hiểu thư viện math.h', 19, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (111, 'Bài 4.3. Các hàm nguyên mẫu', 19, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (112, 'Bài 4.4. Gọi hàm bằng truyền tham chiếu và truyền giá trị', 19, 4, 'video', 'pzIeMntY.mp4',
        '2024-09-12 07:08:18', NULL),
       (113, 'Bài 4.5. Hàm sinh số ngẫu nhiên', 19, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (114, 'Bài 4.6. Quy tắc phạm vi của một định danh', 19, 6, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (115, 'Bài 4.7. Hàm đệ quy', 19, 7, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (116, 'Bài 4.8. So sánh giữa đệ quy và vòng lặp', 19, 8, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (117, 'Bài kiểm tra cuối chương 4', 19, 9, 'quiz', NULL, '2024-09-12 07:08:18', NULL),

       (118, 'Bài 5.1. Mảng một chiều', 20, 1, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (119, 'Bài 5.2. Truyền mảng vào hàm', 20, 2, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (120, 'Bài 5.3. Sắp xếp các phần tử trong mảng', 20, 3, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (121, 'Bài 5.4. Tìm kiếm trong mảng', 20, 4, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (122, 'Bài 5.5. Mảng hai chiều', 20, 5, 'video', 'pzIeMntY.mp4', '2024-09-12 07:08:18', NULL),
       (123, 'Bài kiểm tra cuối chương 5', 20, 6, 'quiz', NULL, '2024-09-12 07:08:18', NULL);

-- Inserting data into ENROLLMENT table
INSERT INTO `ENROLLMENT` (id, enrolled_at, user_id, course_id)
VALUES (1, '2024-09-12 07:08:18', '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 1),
       (2, '2024-09-12 07:08:18', '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 2),
       (3, '2024-09-12 07:08:18', '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 3);

-- Inserting data into CART table
INSERT INTO `CART` (id)
VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34');

-- Inserting data into CART_ITEM table
INSERT INTO `CART_ITEM` (id, cart_id, course_id)
VALUES (1, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 5),
       (2, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 6),
       (3, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 7),
       (4, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 8),
       (5, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 9);

-- Inserting data into WISH_LIST table
INSERT INTO `WISH_LIST` (id)
VALUES ('4a39e0fe-27a0-4789-8ba2-b7e49677db34');

-- Inserting data into WISH_LIST_ITEM table
INSERT INTO `WISH_LIST_ITEM` (id, wish_list_id, course_id)
VALUES (1, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 10),
       (2, '4a39e0fe-27a0-4789-8ba2-b7e49677db34', 11);


-- Inserting data into ORDER table
INSERT INTO `ORDER` (id, order_status, total_price, total_discount_price, stripe_payment_intent_id, created_at, user_id)
VALUES (1, 'succeeded', 3798556, 3798556, '234', '2024-09-12 07:08:18', '4a39e0fe-27a0-4789-8ba2-b7e49677db34');

-- Inserting data into ORDER_DETAIL table
INSERT INTO ORDER_DETAIL (id, order_id, course_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4);







