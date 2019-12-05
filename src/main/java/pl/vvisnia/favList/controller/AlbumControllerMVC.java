package pl.vvisnia.favList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AlbumControllerMVC {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/list")
    public String list(){
        return "list";
    }

}
