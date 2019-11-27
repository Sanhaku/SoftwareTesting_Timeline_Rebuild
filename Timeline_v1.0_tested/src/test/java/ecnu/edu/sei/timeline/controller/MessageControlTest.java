package ecnu.edu.sei.timeline.controller;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import ecnu.edu.sei.timeline.dao.MessageDao;
import ecnu.edu.sei.timeline.service.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDateTime;
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
    void shouldGetAllMessage() throws Exception{
        LocalDateTime yesterday=LocalDateTime.now().minusDays(1);
        MessageDao messageDao = new MessageDao(100, "royal", "never give up", "", yesterday);
        List<MessageDao> messageDaoList=new ArrayList<>();
        messageDaoList.add(messageDao);

        when(messageServiceImpl.getAll())
                .thenReturn(messageDaoList);
        this.mockMvc.perform(get("/timeline"))
                .andExpect(status().isOk());
        //        .andDo(print());

        MvcResult mvcResult=mockMvc.perform(get("/timeline")).andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        assertAll("",
                ()->ModelAndViewAssert.assertModelAttributeAvailable(mv,"messages"),
                ()->ModelAndViewAssert.assertCompareListModelAttribute(mv,"messages",messageDaoList),
                ()->ModelAndViewAssert.assertModelAttributeAvailable(mv,"size"),
                ()->ModelAndViewAssert.assertModelAttributeValue(mv,"size",messageDaoList.size()));

        verify(messageServiceImpl,times(2)).getAll();
    }

    @Test
    void shouldGetPageMessage_whenGetPage() throws Exception {
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
    void shouldGetUpdateNumbers() throws Exception{
        when(messageServiceImpl.getNumOfMessages()).thenReturn(5);
        this.mockMvc.perform(post("/update"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               // .andDo(print())
                .andExpect(content().string("5"));

    }
}