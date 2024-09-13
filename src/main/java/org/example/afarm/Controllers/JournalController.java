package org.example.afarm.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import jdk.jfr.ContentType;
import org.example.afarm.DTO.FileDto;
import org.example.afarm.DTO.JournalDto;
import org.example.afarm.DTO.JournalGetDto;
import org.example.afarm.DTO.UserDto;
import org.example.afarm.Repository.JournalRepository;
import org.example.afarm.Service.JournalService;
import org.example.afarm.entity.JournalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/journal")
@CrossOrigin(origins = "http://localhost:3000")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {

        this.journalService = journalService;
    }

    public ResponseEntity<?> generateResEntity(Object msg,Integer status){
        return new ResponseEntity<>(msg, HttpStatusCode.valueOf(status));
    }

    @PostMapping("/write")
    public ResponseEntity<?> insertJournal(JournalGetDto journalDto, Authentication authentication, HttpServletRequest request){
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        System.out.println(request.getHeader("content-type"));
        System.out.println(request.getAttribute("title"));
        System.out.println(journalDto.getTitle());
        System.out.println(journalDto.getContent());
        System.out.println(journalDto.getFiles());
        journalService.saveJournal(journalDto,userDetails.getUsername());
        return generateResEntity("J_Save",201);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteJournal(Integer num, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        journalService.deleteJournal(num,userDetails.getUsername());
        return generateResEntity("J_Del",200);
    }

//    @ResponseBody
//    @GetMapping("/select")
//    public ResponseEntity<?> selectAllJournal(Pageable pageable, Authentication authentication){
//        UserDetails user = (UserDetails) authentication.getPrincipal();
//        Page<?> page = journalService.selectAllJournal(pageable,user.getUsername());
//        return new ResponseEntity<>(page,HttpStatusCode.valueOf(200));
//    }

    @ResponseBody
    @GetMapping("/select")
    public ResponseEntity<?> selectAllJournal(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        List<?> list = journalService.selectAllJournal(user.getUsername());
        return new ResponseEntity<>(list,HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping("/page")
    public ResponseEntity<?> selectSingleJournal(@RequestParam Integer num, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JournalDto journal = journalService.selectOne(userDetails.getUsername(), num);
        return new ResponseEntity<>(journal,HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/image")
    public ResponseEntity<?> getImage(Integer num,Integer filenum, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JournalDto journal = journalService.selectOne(userDetails.getUsername(), num);
        if(journal.getImageUrls().isEmpty()){
            return new ResponseEntity<>("no Exist File",HttpStatusCode.valueOf(400));
        }

        List<byte[]> images =  journalService.getFile(journal);


        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(images.get(filenum-1));

    }

    @ResponseBody
    @PostMapping("/update")
    public ResponseEntity<?> updateJournal(JournalDto journalDto, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        journalService.updateOne(userDetails.getUsername(), journalDto.getId(),journalDto);
        return new ResponseEntity<>("update_ok",HttpStatusCode.valueOf(200));
    }



}
