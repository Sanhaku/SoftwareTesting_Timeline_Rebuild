package ecnu.edu.sei.timeline.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RunWith(SpringRunner.class)
@SpringBootTest
class MessageDaoTest {

    @Before
    public void init(){
    }

    @Test
    void testGetTimeDifference() {//测试与当前时间的间隔
        LocalDateTime localDateTime=LocalDateTime.parse("2019-11-11 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao1 = new MessageDao(100, "royal", "never give up", "", localDateTime);
        Assert.assertEquals("4天之前", messageDao1.getTimeDifference());

        localDateTime=LocalDateTime.parse("2019-11-15 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao2 = new MessageDao(100, "royal", "never give up", "", localDateTime);
        Assert.assertEquals("8小时之前", messageDao2.getTimeDifference());

        localDateTime=LocalDateTime.parse("2019-11-15 16:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao3 = new MessageDao(100, "royal", "never give up", "", localDateTime);
        Assert.assertEquals("30分钟之前", messageDao3.getTimeDifference());

    }
}