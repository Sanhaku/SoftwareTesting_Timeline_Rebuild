package ecnu.edu.sei.timeline.controller;

import ecnu.edu.sei.timeline.dao.MessageDao;
import ecnu.edu.sei.timeline.service.MessageServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MessageControl {

    @Resource
    private MessageServiceImpl messageServiceImpl;

    @RequestMapping("/timeline")
    public String getAll(Model model) {

        List<MessageDao> messages = messageServiceImpl.getAll();
        model.addAttribute("messages", messages);
        model.addAttribute("size", messages.size());
        return "timeline";
    }


    @RequestMapping("/page")
    @ResponseBody
    public Page<MessageDao> findAll(@RequestParam(value="page",defaultValue="0")int page){
        Pageable pageable= PageRequest.of(page,3, Sort.by("id").descending());

        return messageServiceImpl.findAll(pageable);
    }

    @PostMapping("/update")
    @ResponseBody
    public int getUpdate() {
        return messageServiceImpl.getNumOfMessages();
    }
}
