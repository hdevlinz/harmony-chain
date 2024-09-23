package com.tth.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileCategory {

    AVATAR("avatars");

    private final String folderName;

}
