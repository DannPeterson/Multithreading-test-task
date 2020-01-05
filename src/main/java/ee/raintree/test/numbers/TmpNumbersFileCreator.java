package ee.raintree.test.numbers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.Callable;

public class TmpNumbersFileCreator implements Callable<File> {
    private File file;
    private PrintWriter printWriter;
    private static final String SEPARATOR = " ";
    private int size;

    public TmpNumbersFileCreator(int size) {
        this.size = size;
    }

    @Override
    public File call() throws IOException {
        return getTempFile();
    }

    public File getTempFile() throws IOException {
        createTempFile();
        writeNumbersToFile();
        return file;
    }

    private void createTempFile() throws IOException {
        file = File.createTempFile("numbers-", "-txt");
        file.deleteOnExit();
    }

    private void writeNumbersToFile() throws FileNotFoundException {
        printWriter = new PrintWriter(file);
        while (!isFileSizeMax()) {
            printWriter.write(getRandomNumber().toString() + SEPARATOR);
        }
        printWriter.flush();
        printWriter.close();
    }

    private BigInteger getRandomNumber() {
        Random random = new Random();
        BigInteger number;
        do {
            number = new BigInteger(64, random);
        } while (number.equals(BigInteger.ZERO));
        return number;
    }

    private boolean isFileSizeMax() {
        if (file.length() <= size) {
            return false;
        }
        return true;
    }
}
