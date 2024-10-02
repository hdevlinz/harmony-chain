package com.tth.commonlibrary.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tth.services")
public record ServiceUrlConfig(String media, String product) {
}
