package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;


    @GetMapping("/")
    @ResponseBody
    public String main() {

        return "Khuromise board project";
    }

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){

        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){

        boardService.write(board);


        return "";
    }

    @GetMapping("/board/list")
    public String boardList(){


        return "boardlist";
    }


}