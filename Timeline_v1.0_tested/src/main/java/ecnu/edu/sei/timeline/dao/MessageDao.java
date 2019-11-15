package ecnu.edu.sei.timeline.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
@Table(name="message")
public class MessageDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String content;

    private String picture;

    private LocalDateTime createTime;
    public String getTimeDifference() {

        Duration duration = Duration.between(createTime, LocalDateTime.now());
        if (duration.toDays() >= 365)
            return duration.toDays() / 365 + "年之前";
        else if (duration.toDays() >= 30)
            return duration.toDays() / 30 + "个月之前";
        else if (duration.toDays() > 0)
            return duration.toDays() + "天之前";
        else if (duration.toHours() > 0)
            return duration.toHours() + "小时之前";
        else if (duration.toMinutes() > 0)
            return duration.toMinutes() + "分钟之前";
        else
            return "刚刚";
    }

}
