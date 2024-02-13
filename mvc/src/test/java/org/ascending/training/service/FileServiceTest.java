package org.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import org.ascending.training.ApplicationBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Mock
    private File mockFile;

    @Autowired
    private AmazonS3 mockS3Client;

    @Test
    public void uploadFile() throws IOException {
        when(mockFile.getName()).thenReturn("testFile");

        fileService.uploadFile(mockFile);
        verify(mockS3Client, times(1))
                .putObject(anyString(),anyString(),any(File.class));
    }
}
