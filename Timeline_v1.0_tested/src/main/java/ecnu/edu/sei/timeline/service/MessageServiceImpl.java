package ecnu.edu.sei.timeline.service;


import ecnu.edu.sei.timeline.dao.MessageDao;
import ecnu.edu.sei.timeline.dao.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<MessageDao> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public Page<MessageDao> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public int getNumOfMessages() {
        return getAll().size();
    }

}
