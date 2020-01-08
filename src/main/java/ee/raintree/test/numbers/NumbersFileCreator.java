package ee.raintree.test.numbers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ee.raintree.test.numbers.utils.IOCopier;

public class NumbersFileCreator {
    private static final char SEPARATOR = ' ';
    private File file;
    private Random random;
    private int size;
    private int coreCount;
    private List<File> tmpFiles;
    private ExecutorService service;

    public NumbersFileCreator(int size, String fileName) {
        this.size = size;
        this.file = new File(fileName);
        this.random = new Random();
        this.coreCount = Runtime.getRuntime().availableProcessors();
        this.tmpFiles = new ArrayList<>();
        this.service = Executors.newFixedThreadPool(coreCount);
    }

    public File create() throws IOException, InterruptedException, ExecutionException {
        createTmpFiles();
        IOCopier.joinFiles(file, tmpFiles);
        return file;
    }

    private void createTmpFiles() throws InterruptedException, ExecutionException {
        List<Future> futureTmpFiles = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) {
            Future<File> futureTmpFile = service.submit(() -> {
                return createTmpFile();
            });
            futureTmpFiles.add(futureTmpFile);
        }
        for (int i = 0; i < coreCount; i++) {
            Future<File> futureTmpFile = futureTmpFiles.get(i);
            tmpFiles.add(futureTmpFile.get());
        }
        service.shutdown();
    }

    private File createTmpFile() throws IOException {
        File tmpFile = File.createTempFile("numbers", "txt");
        tmpFile.deleteOnExit();
        writeNumbersToFile(tmpFile);
        return tmpFile;
    }

    private void writeNumbersToFile(File file) throws IOException {
        int separatorByteLength = 1;
        int fileSize = size / coreCount;
        int bytesWrited = 0;
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file.getPath()), StandardCharsets.UTF_8))) {
            while (bytesWrited < fileSize) {
                String number = getRandomNumber().toString();
                writer.write(number);
                writer.write(SEPARATOR);
                bytesWrited = bytesWrited + number.getBytes(StandardCharsets.UTF_8).length + separatorByteLength;
            }
        }
    }

    private BigInteger getRandomNumber() {
        BigInteger number;
        do {
            number = new BigInteger(64, random);
        } while (number.equals(BigInteger.ZERO));
        return number;
    }
}
