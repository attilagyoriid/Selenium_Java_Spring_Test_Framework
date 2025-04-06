package com.coding.challenge.selenium.framework.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Service
public class StringService {

    /**
     * Convert given string to file name friendly string with current date
     *
     * @param fileName file name tobe converted
     * @return file name with whitespaces replaced with _ and date as postfix
     */
    public String convertScenarioNameToFileNameWithDate(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("File name is empty!");
        }
        final String extension = ".mp4";

        String scenarioNameFileNameFriendly = fileName.replaceAll("\\s", "_").toLowerCase();


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy-hh-mm-ss-SSS");
        String strDate = formatter.format(date);

        String videoFileNameWithDate = scenarioNameFileNameFriendly + "-" + strDate + extension;
        return videoFileNameWithDate;

    }

    /**
     * This method takes a string input and extracts the first number found in it.
     * It removes all non-numeric characters and returns the result as a string.
     *
     * @param input The input string from which to extract the number.
     * @return A string containing the extracted number, or an empty string if no number is found.
     */
    public String extractNumberFromString(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.replaceAll("[^0-9.]", "");
    }


    /**
     * This method takes a string input and returns a list of words separated by spaces.
     * It handles multiple spaces, non-breaking spaces, and trims each word.
     *
     * @param text The input string to be processed.
     * @return A list of words separated by spaces. If the input is null or empty, an empty list is returned.
     */
    public List<String> getListFromTextSeparatedBySpaces(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(text.trim()
                        .replaceAll("\u00A0", " ")
                        .replaceAll("\\s+", " ")
                        .split(" "))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
    }

}
