package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface IGestionMessage {

    public Message sendMessage (int senderUser, Long discussion, String message);

    public Message modifyMessage (Long id, String message);

    public boolean deleteMessage (Long id);

    public Message replyMessage(int userSender, Long discussion, Long message, String reply);

    public List<Message> retrieveAllMessages(Long id);

    public List<Message> retrieveMessages(Long id, int page, int size);

    public List<Message> retrieveRecentMessages(Long id, String recentDate);

    public Message pinMessage(Long id);

}
