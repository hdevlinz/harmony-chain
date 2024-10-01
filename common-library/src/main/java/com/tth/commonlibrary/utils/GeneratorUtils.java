package com.tth.commonlibrary.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class GeneratorUtils {

    public static String generateNanoId() {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 5);
    }

    public static String generateOrderNumber() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());

        return String.format("ORD-%s-%s", generateNanoId(), datePart);
    }

    public static String generateInvoiceNumber() {
        String datePart = new SimpleDateFormat("yyyyMM").format(new Date());

        return String.format("INV-%s-%s", generateNanoId(), datePart);
    }

}
