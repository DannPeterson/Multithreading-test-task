package ee.raintree.test.numbers;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class NumbersFileCreatorTest {
	@Test
	public void givenFileSizeAndName_whenGetFile_thenGetFile()
			throws IOException, InterruptedException, ExecutionException {
		int oneMbSizeInBytes = 10 * 1024 * 1024;
		NumbersFileCreator nfc = new NumbersFileCreator(oneMbSizeInBytes, "test");
		File file = nfc.create();
		file.deleteOnExit();

		assertTrue(file.isFile());
		assertTrue(file.length() > 0);
	}
}