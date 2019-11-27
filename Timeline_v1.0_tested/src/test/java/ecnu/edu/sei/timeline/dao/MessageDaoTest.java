package ecnu.edu.sei.timeline.dao;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
class MessageDaoTest {

    @Test
    void shouldGetTimeDifference() {
        LocalDateTime time1=LocalDateTime.now().minusDays(1);
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", time1);
       // messageDao.setCreateTime(localDateTime);
        assertEquals("1天之前",messageDao.getTimeDifference());
        LocalDateTime time2 = LocalDateTime.now().minusMonths(2);
        messageDao.setCreateTime(time2);
        assertEquals("2个月之前", messageDao.getTimeDifference());
        LocalDateTime time3 = LocalDateTime.now().minusHours(2);
        messageDao.setCreateTime(time3);
        assertEquals("2小时之前", messageDao.getTimeDifference());
        LocalDateTime time4 = LocalDateTime.now().minusMinutes(5);
        messageDao.setCreateTime(time4);
        assertEquals("5分钟之前", messageDao.getTimeDifference());
        LocalDateTime time5 = LocalDateTime.now().minusSeconds(30);
        messageDao.setCreateTime(time5);
        assertEquals("刚刚", messageDao.getTimeDifference());
        LocalDateTime time6 = LocalDateTime.now().minusYears(1);
        messageDao.setCreateTime(time6);
        assertEquals("1年之前", messageDao.getTimeDifference());
    }
}