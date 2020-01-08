package ee.raintree.test.numbers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ee.raintree.test.numbers.utils.ArgsChecker;

public class Main {
    private static int fileSize = 67108864;
    private static String fileName;

    public static void main(String args[]) throws InterruptedException, ExecutionException, IOException {
        ArgsChecker.checkArgs(args);
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = args[0];
            fileSize = Integer.valueOf(args[1]) * 1024 * 1024;
        }

        NumbersFileCreatorMT creator = new NumbersFileCreatorMT(fileSize, fileName);
        File numbersFile = creator.create();

        long analysisStart = System.currentTimeMillis();
        FileAnalyzerMT analyzer = new FileAnalyzerMT(numbersFile);
        analyzer.analyze();
        AnalysisPrinter printer = new AnalysisPrinter(analyzer);
        long analysisEnd = System.currentTimeMillis();
        System.out.println("Analysis took " + (analysisEnd - analysisStart) + " milliseconds.");
        printer.print();

    }
}