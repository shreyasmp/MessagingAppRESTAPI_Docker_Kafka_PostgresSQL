package com.shreyas.messaging.controller;

import com.shreyas.messaging.data.MessageDTO;
import com.shreyas.messaging.model.Message;
import com.shreyas.messaging.service.MessageProducer;
import com.shreyas.messaging.service.MessageService;
import com.shreyas.messaging.util.MessageUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MessageController.class)
@WebAppConfiguration
public class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private MessageService messageService;

    @Mock
    private MessageUtil messageUtil;

    private MockMvc mockMvc;

    private final Message mockMessage = Mockito.mock(Message.class);
    private final List<Message> mockListMessage = Mockito.mock(List.class);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    public void testSendMessage_HTTP_SUCCESS() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId("123");
        messageDTO.setSenderId("homie1");
        messageDTO.setReceiverId("cholo1");
        messageDTO.setMessage("Hey Wazzaa");

        when(messageUtil.dtoToModel(messageDTO)).thenReturn(mockMessage);

        messageController.sendMessage(123L, messageDTO);

        String createNewMessageJson = "{\n" +
                "    \"receiverId\": \"111\",\n" +
                "    \"message\": Wassaaa\n" +
                "}";

        MvcResult result = mockMvc.perform(
                post("/messaging/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createNewMessageJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void testGetAllSentMessages_HTTP_SUCCESS() throws Exception {
        when(messageService.getAllSentMessages(anyLong())).thenReturn(mockListMessage);

        messageController.getAllSentMessages(111L);

        MvcResult result = mockMvc.perform(
                get("/messaging/sent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetAllReceivedMessages_HTTP_SUCCESS() throws Exception {
        when(messageService.getAllReceivedMessage(anyLong())).thenReturn(mockListMessage);

        messageController.getAllReceivedMessages(111L);

        MvcResult result = mockMvc.perform(
                get("/messaging/received")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetAllReceivedMessagesFromSender_HTTP_SUCCESS() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId("123");
        messageDTO.setSenderId("homie1");
        messageDTO.setReceiverId("cholo1");
        messageDTO.setMessage("Hey Wazzaa");

        when(messageService.getAllMessages(anyLong(), anyLong())).thenReturn(mockListMessage);

        messageController.getAllReceivedMessagesFromSender(111L, 222L);

        MvcResult result = mockMvc.perform(
                get("/messaging/sender/{sender_id}", messageDTO.getSenderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetAllMessages_HTTP_SUCCESS() throws Exception {
        when(messageService.getAllMessage()).thenReturn(mockListMessage);

        messageController.getAllMessages();

        MvcResult result = mockMvc.perform(
                get("/messaging/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetMessage_HTTP_SUCCESS() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId("123");
        messageDTO.setSenderId("homie1");
        messageDTO.setReceiverId("cholo1");
        messageDTO.setMessage("Hey Wazzaa");

        when(messageService.getMessage(anyLong())).thenReturn(mockMessage);

        messageController.getMessage(111L);

        MvcResult result = mockMvc.perform(
                get("/messaging/messages/{id}", messageDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
