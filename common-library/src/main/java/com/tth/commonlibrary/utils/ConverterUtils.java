package com.tth.commonlibrary.utils;

import com.tth.commonlibrary.constants.Constants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ConverterUtils {

    public static Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(value);
        } else if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(value);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return ConverterUtils.parseBoolean(value);
        } else if (fieldType == LocalDate.class) {
            return ConverterUtils.parseLocalDate(value);
        } else if (fieldType == LocalDateTime.class) {
            return ConverterUtils.parseLocalDateTime(value);
        } else if (fieldType == BigDecimal.class) {
            return new BigDecimal(value);
        } else {
            throw new IllegalArgumentException("Không hỗ trợ kiểu dữ liệu: " + fieldType.getName());
        }
    }

    public static LocalDate parseLocalDate(String value) {
        return LocalDate.parse(value, Constants.DATE_FORMAT);
    }

    public static LocalDateTime parseLocalDateTime(String value) {
        return LocalDateTime.parse(value, Constants.DATE_TIME_FORMAT);
    }

    public static Boolean parseBoolean(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        value = value.trim().toLowerCase();
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        }

        return null;
    }

}
