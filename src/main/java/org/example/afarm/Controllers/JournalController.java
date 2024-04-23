package org.example.afarm.Controllers;

import jakarta.websocket.server.PathParam;
import org.example.afarm.DTO.FileDto;
import org.example.afarm.DTO.JournalDto;
import org.example.afarm.DTO.UserDto;
import org.example.afarm.Repository.JournalRepository;
import org.example.afarm.Service.JournalService;
import org.example.afarm.entity.JournalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/journal")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {

        this.journalService = journalService;
    }

    public ResponseEntity<?> generateResEntity(Object msg,Integer status){
        return new ResponseEntity<>(msg, HttpStatusCode.valueOf(status));
    }

    @PostMapping("/write")
    public ResponseEntity<?> insertJournal(JournalDto journalDto, FileDto file, Authentication authentication){
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        System.out.println(journalDto);
        journalService.saveJournal(journalDto,file,userDetails.getUsername());
        return generateResEntity("J_Save",201);
    }

    @GetMapping("/delete/{num}")
    public ResponseEntity<?> deleteJournal(@RequestParam Integer num, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        journalService.deleteJournal(num,userDetails.getUsername());
        return generateResEntity("J_Del",200);
    }

    @ResponseBody
    @GetMapping("/select")
    public ResponseEntity<?> selectAllJournal(Pageable pageable, Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Page<?> page = journalService.selectAllJournal(pageable,user.getUsername());
        return new ResponseEntity<>(page,HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping("/select/{num}")
    public ResponseEntity<?> selectSingleJournal(@RequestParam Integer num, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JournalDto journal = journalService.selectOne(userDetails.getUsername(), num);
        return new ResponseEntity<>(journal,HttpStatusCode.valueOf(200));
    }

}
