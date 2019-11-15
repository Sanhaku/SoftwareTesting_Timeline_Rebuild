package ecnu.edu.sei.timeline.service;


import ecnu.edu.sei.timeline.dao.MessageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    List<MessageDao> getAll();
    Page<MessageDao> findAll(Pageable pageable);
    int getNumOfMessages();
}
