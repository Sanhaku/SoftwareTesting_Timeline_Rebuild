package ecnu.edu.sei.timeline.controller;

import ecnu.edu.sei.timeline.dao.MessageDao;
import ecnu.edu.sei.timeline.service.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest (MessageControl.class)
class MessageControlTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageServiceImpl messageServiceImpl;

    @Test
    void testGetAll() throws Exception{
        LocalDateTime localDateTime=LocalDateTime.parse("2019-11-11 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", localDateTime);
        List<MessageDao> messageDaoList=new ArrayList<>();
        messageDaoList.add(messageDao);

        when(messageServiceImpl.getAll())
                .thenReturn(messageDaoList);
        this.mockMvc.perform(get("/timeline"))
                .andExpect(status().isOk());
        //        .andDo(print());

        MvcResult mvcResult=mockMvc.perform(get("/timeline")).andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        ModelAndViewAssert.assertModelAttributeAvailable(mv,"messages");
        ModelAndViewAssert.assertCompareListModelAttribute(mv,"messages",messageDaoList);
        ModelAndViewAssert.assertModelAttributeAvailable(mv,"size");
        ModelAndViewAssert.assertModelAttributeValue(mv,"size",messageDaoList.size());

        verify(messageServiceImpl,times(2)).getAll();
    }

    @Test
    void findAll() throws Exception {
        when(messageServiceImpl.findAll(any())).thenReturn(null);
        String requestPage="{\"data\":{\"page\":} }";
        RequestBuilder requestBuilder = null;
        requestBuilder = get("/page").contentType(MediaType.APPLICATION_JSON).content(requestPage);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
               // .andDo(print());
        verify(messageServiceImpl,times(1)).findAll(any());
    }

    @Test
    void getUpdate() throws Exception{
        when(messageServiceImpl.getNumOfMessages()).thenReturn(5);
        this.mockMvc.perform(post("/update"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               // .andDo(print())
                .andExpect(content().string("5"));

    }
}