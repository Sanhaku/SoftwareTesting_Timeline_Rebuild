package ecnu.edu.sei.timeline.service;

import ecnu.edu.sei.timeline.TimelineApplication;
import ecnu.edu.sei.timeline.dao.MessageDao;
import ecnu.edu.sei.timeline.dao.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimelineApplication.class)
class MessageServiceImplTest {
    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    void shouldListMessages() {
        LocalDateTime yesterday=LocalDateTime.now().minusDays(1);
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", yesterday);
        List<MessageDao> messageDaoList=new ArrayList<>();
        messageDaoList.add(messageDao);

        when(messageRepository.findAll()).thenReturn(messageDaoList);
        List<MessageDao> result=messageService.getAll();
        assertAll("test resultList",()->assertEquals(1,result.size()),
                                 ()->assertEquals(100,result.get(0).getId()),
                                 ()-> assertEquals("royal",result.get(0).getName()),
                                 ()->assertEquals("never give up",result.get(0).getContent()),
                                    ()->assertEquals("",result.get(0).getPicture()),
                                    ()->assertEquals(yesterday,result.get(0).getCreateTime()));

//        assertEquals(1,result.size());
//        assertEquals(100,result.get(0).getId());
//        assertEquals("royal",result.get(0).getName());
//        assertEquals("never give up",result.get(0).getContent());
//        assertEquals("",result.get(0).getPicture());
//        assertEquals(yesterday,result.get(0).getCreateTime());

    }

    @Test
    void shouldPageMessages() {
        Pageable pageable= PageRequest.of(0,3, Sort.by("id").descending());
        when(messageRepository.findAll(pageable)).thenReturn(null);
        Page<MessageDao> result=messageService.findAll(pageable);

        verify(messageRepository,times(1)).findAll(pageable);
        verifyNoMoreInteractions(messageRepository);
    }

    @Test
    void shouldGetNumOfMessages() {
        LocalDateTime yesterday=LocalDateTime.now().minusDays(1);
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", yesterday);
        List<MessageDao> messageDaoList=new ArrayList<>();
        messageDaoList.add(messageDao);

        Mockito.when(messageRepository.findAll()).thenReturn(messageDaoList);

        int result=messageService.getNumOfMessages();
        assertEquals(messageDaoList.size(),result);
        verify(messageRepository,times(1)).findAll();

        messageDaoList.add(messageDao);
        result=messageService.getNumOfMessages();
        assertEquals(messageDaoList.size(),result);
        verify(messageRepository,times(2)).findAll();

    }
}