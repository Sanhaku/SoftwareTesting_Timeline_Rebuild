package ecnu.edu.sei.timeline.service;

import ecnu.edu.sei.timeline.TimelineApplication;
import ecnu.edu.sei.timeline.dao.MessageDao;
import ecnu.edu.sei.timeline.dao.MessageRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimelineApplication.class)
class MessageServiceImplTest {
    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    @DisplayName("test getAll")
    void testGetAll() {
        LocalDateTime localDateTime=LocalDateTime.parse("2019-11-11 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", localDateTime);
        List<MessageDao> messageDaoList=new ArrayList<>();
        messageDaoList.add(messageDao);

        when(messageRepository.findAll()).thenReturn(messageDaoList);
        List<MessageDao> result=messageService.getAll();
        Assert.assertEquals(1,result.size());
        Assert.assertEquals(100,result.get(0).getId());
        Assert.assertEquals("royal",result.get(0).getName());
        Assert.assertEquals("never give up",result.get(0).getContent());
        Assert.assertEquals("",result.get(0).getPicture());
        Assert.assertEquals(localDateTime,result.get(0).getCreateTime());

    }

    @Test
    @DisplayName("test page")
    void testFindAll() {
        LocalDateTime localDateTime=LocalDateTime.parse("2019-11-11 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", localDateTime);

        Pageable pageable= PageRequest.of(0,3, Sort.by("id").descending());
        when(messageRepository.findAll(pageable)).thenReturn(null);
        Page<MessageDao> result=messageService.findAll(pageable);

        verify(messageRepository,times(1)).findAll(pageable);
        verifyNoMoreInteractions(messageRepository);
    }

    @Test
    @DisplayName("test get number")
    void testGetNumOfMessages() {
        LocalDateTime localDateTime=LocalDateTime.parse("2019-11-11 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", localDateTime);
        List<MessageDao> messageDaoList=new ArrayList<>();
        messageDaoList.add(messageDao);

        Mockito.when(messageRepository.findAll()).thenReturn(messageDaoList);

        int result=messageService.getNumOfMessages();
        Assert.assertEquals(messageDaoList.size(),result);
        verify(messageRepository,times(1)).findAll();

        messageDaoList.add(messageDao);
        result=messageService.getNumOfMessages();
        Assert.assertEquals(messageDaoList.size(),result);
        verify(messageRepository,times(2)).findAll();

    }
}