package com.example.board.controller;

import com.example.board.beans.vo.BoardVO;
import com.example.board.services.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

//    프레젠테이션 계층의 구현

//    Task      URL             Method      Parameter   Form    URL 이동
//    전체 목록 /board/list      GET         없음         없음    없음
//    등록 처리 /board/register  POST        모든 항목     필요    이동
//    조회 처리 /board/read      GET         bno          필요    없음
//    삭제 처리 /board/remove    POST        bno          필요    이동
//    수정 처리 /board/modify    POST        모든 항목     필요    이동

@Controller
@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("list")
    public String list(Model model){
        log.info("-------------------------------");
        log.info("list");
        log.info("-------------------------------");
        model.addAttribute("list", boardService.getList());
        return "board/list";
    }

    @PostMapping("register")
    public RedirectView register(BoardVO boardVO, RedirectAttributes rttr){
        log.info("-------------------------------");
        log.info("register : " + boardVO.toString());
        log.info("-------------------------------");
        /*redirect: 문자열이 아닌 명령어로 처리된다. 단, 명령어로 주는경우는 '/' 슬래시를 포함한다.*/
        //return "redirect:/board/list";

        boardService.register(boardVO);

        //get방식 : 쿼리스트링으로 전달
        rttr.addAttribute("bno", boardVO.getBno());
        //flash 담기 : 세션의 flash영역을 이용하여 전달
        rttr.addFlashAttribute("bno", boardVO.getBno());

        //RedirectView : redierect 방식으로 전송이 가능하다.
        return new RedirectView("list");
    }

    @GetMapping("read")
    public void read(@RequestParam("bno") Long bno, Model model){
        log.info("-------------------------------");
        log.info("read : " + bno);
        log.info("-------------------------------");

        model.addAttribute("board", boardService.get(bno));
    }

    // /modify 요청을 처리할 수 있는 비지니스 로직 작성
    // 수정 성공시 result에 "success"를 담아서 전달한다.
    // 단위 테스트로 View에 전달할 파라미터를 조회한다.

    @PostMapping("modify")
    public void modify(@RequestParam("bno") Long bno, Model model){

    }
}


