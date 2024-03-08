package org.dnyanyog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.dnyanyog.NotificationServiceMain;
import org.dnyanyog.service.NotificationService;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = NotificationServiceMain.class)
public class NotificationControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    NotificationService service;
    
    @Test
    public void verifyNotificationOperationForNotificationSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification/v1/notify")
                .content("{\r\n"
                        + "\"clientId\":\"CLIENT001\",\r\n"
                        + "\"mode\": \"EMAIL\",\r\n"
                        + "\"subject\": \"Important Notification\",\r\n"
                        + "\"body\": \"Hello, this is an important notification.\",\r\n"
                        + "\"footer\": \"Best regards, Your App\",\r\n"
                        + "\"from\" : \"pratikshahodade@gmail.com\",\r\n"
                        + "\"to\": \"chavanas1511@gmail.com\"\r\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Notification sent successfully!"));
    }
    
    @Test
    public void verifyNotificationOperationForIncompleteDataProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification/v1/notify")
                .content("{\r\n"
                        + "\"clientId\":\"CLIENT001\",\r\n"
                        + "\"mode\": \"EMAIL\",\r\n"
                        + "\"body\": \"Hello, this is an important notification.\",\r\n"
                        + "\"footer\": \"Best regards, Your App\",\r\n"
                        + "\"from\" : \"pratikshahodade@gmail.com\",\r\n"
                        + "\"to\": \"chavanas1511@gmail.com\"\r\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NOTI0001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Incomplete data sent"));
    }
    
    @Test
    public void verifyNotificationOperationForInvalidMode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification/v1/notify")
                .content("{\r\n"
                        + "\"clientId\":\"CLIENT001\",\r\n"
                        + "\"mode\": \"MAIL\",\r\n"
                        + "\"subject\": \"Important Notification\",\r\n"
                        + "\"body\": \"Hello, this is an important notification.\",\r\n"
                        + "\"footer\": \"Best regards, Your App\",\r\n"
                        + "\"from\" : \"pratikshahodade@gmail.com\",\r\n"
                        + "\"to\": \"chavanas1511@gmail.com\"\r\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NOTI0002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid notification mode"));
    }
    
    @Test
    public void verifyNotificationOperationForToEmailIfModeIsMail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification/v1/notify")
                .content("{\r\n"
                        + "\"clientId\":\"CLIENT001\",\r\n"
                        + "\"mode\": \"EMAIL\",\r\n"
                        + "\"subject\": \"Important Notification\",\r\n"
                        + "\"body\": \"Hello, this is an important notification.\",\r\n"
                        + "\"footer\": \"Best regards, Your App\",\r\n"
                        + "\"from\" : \"pratikshahodade@gmail.com\",\r\n"
                        + "\"to\": \"chavanas1511.com\"\r\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NOTI0003"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid email address for To EMAIL"));
    }
    
    @Test
    public void verifyNotificationOperationForCatchBlockResponse() throws Exception {
        // Mock the NotificationService
        NotificationService mockedService = mock(NotificationService.class);

        // Stubbing the method call on the mock to throw an exception
        doThrow(new RuntimeException("Test exception")).when(mockedService).sendNotification(any());

        // Sending request that triggers the mocked service to throw an exception
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification/v1/notify")
                .content("{\r\n"
                        + "\"clientId\":\"CLIENT001\",\r\n"
                        + "\"mode\": \"EMAIL\",\r\n"
                        + "\"subject\": \"Important Notification\",\r\n"
                        + "\"body\": \"Hello, this is an important notification.\",\r\n"
                        + "\"footer\": \"Best regards, Your App\",\r\n"
                        + "\"from\" : \"pratikshahodade@gmail.com\",\r\n"
                        + "\"to\": \"chavanas1511@gmail.com\"\r\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Fail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NOTI0004"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error occurred while saving or sending notification"));
    }
}
