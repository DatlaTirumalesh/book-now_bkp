package com.journey_book_now.helper;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class StringToIntArrayConverter {
	
	private String array;
	
    public static int[] getArrayAsInt(String arr) {
        if ( arr.isEmpty()) {
            return new int[0];
        }
        return Arrays.stream(arr.split(","))
                     .mapToInt(Integer::parseInt)
                     .toArray();
    }

    public static String setArrayFromInt(int[] array) {
        return Arrays.stream(array)
                              .mapToObj(String::valueOf)
                              .collect(Collectors.joining(","));
    }

}
