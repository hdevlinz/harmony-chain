package com.tth.commonlibrary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileCategory {

    AVATAR("avatars");

    private final String folderName;

}
