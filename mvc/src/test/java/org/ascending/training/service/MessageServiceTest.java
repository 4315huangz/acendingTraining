package org.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.ascending.training.ApplicationBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class MessageServiceTest {
    @Autowired
    private AmazonSQS mockSqs;

    @Autowired
    MessageService messageService;

    @Mock
    private GetQueueUrlResult mockGetQueueUrlResult;

    @Test
    public void sendMessageTest() {
        when(mockSqs.getQueueUrl(anyString())).thenReturn(mockGetQueueUrlResult);
        when(mockGetQueueUrlResult.getQueueUrl()).thenReturn(anyString());

        messageService.sendMessage("testQueue","testMessage",0);

        verify(mockSqs, times(1)).sendMessage(any(SendMessageRequest.class));
    }
}
