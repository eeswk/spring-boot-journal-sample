package com.apress.spring.web;

import com.apress.spring.domain.Journal;
import com.apress.spring.repository.JournalRepository;
import com.apress.spring.websocket.WebsocketProducer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class JournalController {
    private static Logger log = LoggerFactory.getLogger(JournalController.class);
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    @Autowired
    private JournalRepository repository;
//
    @Autowired
    SimpMessagingTemplate template;

    public JournalController(SimpMessagingTemplate template) {
        this.template = template;
    }
//
//    @Autowired
//    private WebsocketProducer producer;

    @Autowired
    private MeterRegistry meterRegistry;


    @GetMapping("/")
    public String index(Model model) {
        meterRegistry.counter("counter.index.invoked").increment();
        //counter.increment();
        model.addAttribute("journal", repository.findAll());
        System.out.println(repository.findAll());
        return "index";
    }

    @RequestMapping(value = "/journal", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody List<Journal> getJournal() {
        return repository.findAll();
    }

    @RequestMapping("/send/{topic}")
    public @ResponseBody String sender(@PathVariable String topic, @RequestParam String message) {

        log.info("topic: " + topic + " / message: " + message);
        StringBuilder builder = new StringBuilder();
        builder.append("[ ")
                .append(dateFormatter.format(new Date()))
                .append("] ")
                .append(message);
        log.info(topic + " / " + builder.toString());
        template.convertAndSend("/topic/" + topic, builder.toString());
        //producer.sendMessageTo("/topic/"+topic, message);
        return "Ok-전송됨";
    }

}
