package ee.raintree.test.numbers.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class IOCopier {
    public static void joinFiles(File destination, List<File> sources) throws IOException {
        try (OutputStream output = createAppendableStream(destination)) {
            for (File source : sources) {
                appendFile(output, source);
            }
        }
    }

    private static BufferedOutputStream createAppendableStream(File destination) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(destination, true));
    }

    private static void appendFile(OutputStream output, File source) throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(source))) {
            IOUtils.copy(input, output);
        }
    }
}