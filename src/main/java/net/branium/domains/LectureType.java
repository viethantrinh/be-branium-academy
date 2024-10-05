package net.branium.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LectureType {
    VIDEO("video"),
    QUIZ("quiz"),
    DOCUMENT("document");

    private final String name;
}
