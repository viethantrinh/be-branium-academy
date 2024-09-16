-- INSERT INTO
--     USERS(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, BIRTH_DATE, AVATAR, VIP_LEVEL, PHONE_NUMBER, CREATED_AT)
-- VALUES
--     ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'hntrnn12@gmail.com', '$2a$10$B3MFJWFT04c9hSeqy2TseurKFoADyeHG3yzDOuWXUNUGruerDsqBK', 'Viet Han', 'Trinh', 1, 1, TO_DATE('2003-12-02', 'YYYY-MM-DD'), 'fa211ceb-be09-4370-9a98-a55ea8fed578-hntrnn12@gmail.jpg', 9999, '0768701056', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS.SSSSS'));
--
-- INSERT INTO
--     USERS(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, BIRTH_DATE, AVATAR, VIP_LEVEL, PHONE_NUMBER, CREATED_AT)
-- VALUES
--     ('9a39e0ff-27a0-4789-8ba2-b7e49677db34', 'hntrnn195@gmail.com', '$2a$10$Z9mB1/tSfBLpcxX.Y4.KDeDNoqNivdRgsepjIEgeH3AmtBovDtPIa', 'Mai Anh', 'Do', 0, 1, TO_DATE('2001-10-06', 'YYYY-MM-DD'), '9a39e0ff-27a0-4789-8ba2-b7e49677db34-maianhdo0610@gmail.com', 0, '0987666543', TO_TIMESTAMP('2024-09-12 07:08:18', 'YYYY-MM-DD HH24:MI:SS.SSSSS'));
--
-- INSERT INTO
--     ROLES(NAME, DESCRIPTION)
-- VALUES
--     ('ADMIN', 'admin role');
--
-- INSERT INTO
--     ROLES(NAME, DESCRIPTION)
-- VALUES
--     ('STUDENT', 'student role');
--
-- INSERT INTO
--     USERS_ROLES(USERS_ID, ROLES_NAME)
-- VALUES
--     ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'ADMIN');
--
-- INSERT INTO
--     USERS_ROLES(USERS_ID, ROLES_NAME)
-- VALUES
--     ('9a39e0ff-27a0-4789-8ba2-b7e49677db34', 'STUDENT');


-- Inserting data into USERS table
INSERT INTO USERS (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, BIRTH_DATE, AVATAR, VIP_LEVEL, PHONE_NUMBER, CREATED_AT)
VALUES
    ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'hntrnn12@gmail.com', '$2a$10$B3MFJWFT04c9hSeqy2TseurKFoADyeHG3yzDOuWXUNUGruerDsqBK', 'Viet Han', 'Trinh', 1, 1, '2003-12-02', 'fa211ceb-be09-4370-9a98-a55ea8fed578-hntrnn12@gmail.jpg', 9999, '0768701056', '2024-09-12 07:08:18');

INSERT INTO USERS (ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED, GENDER, BIRTH_DATE, AVATAR, VIP_LEVEL, PHONE_NUMBER, CREATED_AT)
VALUES
    ('9a39e0ff-27a0-4789-8ba2-b7e49677db34', 'hntrnn195@gmail.com', '$2a$10$Z9mB1/tSfBLpcxX.Y4.KDeDNoqNivdRgsepjIEgeH3AmtBovDtPIa', 'Mai Anh', 'Do', 0, 1, '2001-10-06', '9a39e0ff-27a0-4789-8ba2-b7e49677db34-maianhdo0610@gmail.com', 0, '0987666543', '2024-09-12 07:08:18');

-- Inserting data into ROLES table
INSERT INTO ROLES (NAME, DESCRIPTION)
VALUES
    ('ADMIN', 'admin role');

INSERT INTO ROLES (NAME, DESCRIPTION)
VALUES
    ('STUDENT', 'student role');

-- Inserting data into USERS_ROLES table
INSERT INTO USERS_ROLES (USERS_ID, ROLES_NAME)
VALUES
    ('fa211ceb-be09-4370-9a98-a55ea8fed578', 'ADMIN');

INSERT INTO USERS_ROLES (USERS_ID, ROLES_NAME)
VALUES
    ('9a39e0ff-27a0-4789-8ba2-b7e49677db34', 'STUDENT');