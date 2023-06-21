package cn.thinkinginjava.mockit.admin.controller;

import cn.thinkinginjava.mockit.admin.model.dto.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        Map<String, List<Session>> sessionMap = new HashMap<>();
        List<Session> list = new ArrayList<>();
        list.add(new Session(null,"127.0.0.1"));
        list.add(new Session(null,"127.0.0.2"));
        sessionMap.put("mockit-sample",list);
        model.addAttribute("sessionMap", sessionMap);
        return "index";
    }
}
