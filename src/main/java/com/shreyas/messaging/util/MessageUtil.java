package com.shreyas.messaging.util;

import com.shreyas.messaging.data.MessageDTO;
import com.shreyas.messaging.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageUtil {

    public MessageDTO modelToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(Long.toString(message.getId()));
        messageDTO.setSenderId(Long.toString(message.getSenderId()));
        messageDTO.setReceiverId(Long.toString(message.getReceiverId()));
        messageDTO.setMessage(message.getMessage());
        return messageDTO;
    }

    public List<MessageDTO> modelToDTO(List<Message> messages) {
        return messages.stream().map(this::modelToDTO).collect(Collectors.toList());
    }

    public Message dtoToModel(MessageDTO msgDto) {
        Message message = new Message();
        message.setId((msgDto.getId() == null || msgDto.getId().isEmpty()) ? 0 : Long.parseLong(msgDto.getId()));
        message.setSenderId((msgDto.getSenderId() == null || msgDto.getSenderId().isEmpty()) ? 0 : Long.parseLong(msgDto.getSenderId()));
        message.setReceiverId((msgDto.getReceiverId() == null || msgDto.getReceiverId().isEmpty()) ? 0 : Long.parseLong(msgDto.getReceiverId()));
        message.setMessage(msgDto.getMessage());
        return message;
    }

    public List<Message> dtoToModel(List<MessageDTO> messages) {
        return messages.stream().map(this::dtoToModel).collect(Collectors.toList());
    }
}
