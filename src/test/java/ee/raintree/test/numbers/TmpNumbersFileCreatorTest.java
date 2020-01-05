package ee.raintree.test.numbers;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TmpNumbersFileCreatorTest {
    @Test
    public void givenOneMbFileSize_whenGetFile_thenGetFile() throws IOException {
        int oneMbSizeInBytes = 1 * 1024 * 1024;
        TmpNumbersFileCreator creator = new TmpNumbersFileCreator(oneMbSizeInBytes);
        File file = creator.getTempFile();

        int expectedSize = 1;
        int actualSize = (int) file.length() / (oneMbSizeInBytes);

        assertThat(actualSize, equalTo(expectedSize));
    }
}