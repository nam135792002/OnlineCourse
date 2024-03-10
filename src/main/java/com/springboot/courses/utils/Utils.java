package com.springboot.courses.utils;

public class Utils {
    public static String removeVietnameseAccents(String input) {
        String result = input;

        result = result.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        result = result.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        result = result.replaceAll("[ìíịỉĩ]", "i");
        result = result.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        result = result.replaceAll("[ùúụủũưừứựửữ]", "u");
        result = result.replaceAll("[ỳýỵỷỹ]", "y");
        result = result.replaceAll("đ", "d");

        String slug = result.trim().toLowerCase();

        slug = slug.replaceAll("\\s+", "-");

        return slug;
    }
}
