package net.branium.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {
    VIDEO("video"),
    IMAGE("image");

    private final String name;
}
