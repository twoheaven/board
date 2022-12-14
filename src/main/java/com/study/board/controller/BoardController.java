package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    //기본 화면, http://localhost:8080/board/main
    @GetMapping("/board/main")
    public String boardMain() {

        return "boardmain";
    }


    //게시물 작성, http://localhost:8080/board/write
    @GetMapping("/board/write")
    public String boardWriteForm(){

        return "boardWrite";
    }

    //임시 페이지
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{

        boardService.boardWrite(board,file);

        model.addAttribute("massage","글 작성이 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");
        return "massagewrite";
    }

    //게시물 리스트
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){

        Page<Board> list = null;

        if(searchKeyword==null){
            list = boardService.boardList(pageable);
        }
        else {
            list = boardService.boardSearchList(searchKeyword,pageable);
        }


        int nowPage= list.getPageable().getPageNumber() + 1;
        int startPage=Math.max(1,nowPage-4);
        int endPage=Math.min(nowPage+5,list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "boardlist";
    }


    //게시물 상세 페이지
    @GetMapping("/board/view")//localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id){

        model.addAttribute("board",boardService.boardView(id));

        return "boardview";
    }


    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board",boardService.boardView(id));


        return "boardmodify";
    }


    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception{


        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.boardWrite(boardTemp, file);
        model.addAttribute("massage","글 수정이 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");

        return "massageupdate";
    }

}