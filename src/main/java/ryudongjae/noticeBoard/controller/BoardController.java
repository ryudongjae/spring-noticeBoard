package ryudongjae.noticeBoard.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ryudongjae.noticeBoard.board.dto.BoardDto;
import ryudongjae.noticeBoard.board.dto.FileDto;
import ryudongjae.noticeBoard.board.service.BoardService;
import ryudongjae.noticeBoard.board.service.FileService;
import ryudongjae.noticeBoard.board.util.MD5Generator;
import ryudongjae.noticeBoard.domain.entity.Board;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//http요청이 진입하는 지점이며,사용자에게 서버에서 처리된 데이터를 View와 함께 응답하게 해줍니다.

@Controller
public class BoardController {
    private BoardService boardService;
    private FileService fileService;

    public BoardController(BoardService boardService,FileService fileService) {
        this.boardService = boardService;
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }


    @PostMapping("/post")
    public String write(@RequestParam("file") MultipartFile files, BoardDto boardDto) {
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new java.io.File(savePath).exists()) {
                try{
                    new java.io.File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDto);
            boardDto.setFileId(fileId);
            boardService.savePost(boardDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    //각 게시글을 클릭하면, /post/{id}으로 Get 요청을 합니다. (만약 1번 글을 클릭하면 /post/1로 접속됩니다.)
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id,Model model){
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("post",boardDto);
        return "board/detail.html";
    }

    //글을 조회하는 페이지에서 ‘수정’ 버튼을 누르면, /post/edit/{id}으로 Get 요청을 합니다.
    // (만약 1번 글에서 ‘수정’ 버튼을 클릭하면 /post/edit/1로 접속됩니다.)
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        BoardDto boardDto =boardService.getPost(id);
        model.addAttribute("post",boardDto);
        return "board/edit.html";
    }
    //위의 화면에서 변경한 후, ‘수정’ 버튼을 누르면 Put 형식으로 /post/edit/{id}로 서버에게 요청이 가게 됩니다.
    //서버에게 Put 요청이 오게되면, 데이터베이스에 변경된 데이터를 저장해야함

    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

}