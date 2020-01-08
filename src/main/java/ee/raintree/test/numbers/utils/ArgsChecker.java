package ee.raintree.test.numbers.utils;

import java.io.File;

public class ArgsChecker {
    public static void checkArgs(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (args.length == 0 || args.length > 2) {
            throw new IllegalArgumentException(
                    "Please check arguments. Program require 1 or 2 (optionally) arguments: " +
                            "First argument is generated filename " +
                            "and second optional argument is generated file size in Megabytes");
        }
        if (args.length == 1) {
            checkFileNameArg(args[0]);
        }
        if (args.length == 2) {
            checkFileNameArg(args[0]);
            checkFileSizeArg(args[1]);
        }
    }

    private static void checkFileNameArg(String fileName) {
        if (fileName.length() == 0 || fileName == null) {
            throw new IllegalArgumentException("File name cannot be empty or null");
        }
        File file = new File(fileName);
        if (file.isFile()) {
            throw new IllegalArgumentException("File with this name already exists");
        }
        try {
            if (file.createNewFile()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot create file with this name, check name for OS filename restricted characters");
        }
    }

    private static void checkFileSizeArg(String fileSize) {
        int size = 0;
        if (fileSize.length() == 0 || fileSize == null) {
            throw new IllegalArgumentException("File size argument cannot be empty or null");
        }
        try {
            size = Integer.valueOf(fileSize);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot parse file size argument, please check it has numeric integer value");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("File size cannot be negative or 0");
        }
    }
}