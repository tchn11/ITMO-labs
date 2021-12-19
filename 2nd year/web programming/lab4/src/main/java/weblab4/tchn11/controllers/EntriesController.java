package weblab4.tchn11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weblab4.tchn11.data.EntryData;
import weblab4.tchn11.entities.Entry;
import weblab4.tchn11.entities.User;
import weblab4.tchn11.repositories.EntryRepository;
import weblab4.tchn11.security.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/entries")
public class EntriesController {
    @Autowired
    private UserService userService;

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping
    ResponseEntity<?> getUserEntries(Principal principal){
        System.out.println("Get_entry");
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.findByUser(user));
    }

    @PostMapping
    ResponseEntity<?> addEntry(@Validated @RequestBody EntryData entryData, Principal principal){
        System.out.println("Post_entry");
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.save(new Entry(
                entryData.getX(),
                entryData.getY(),
                entryData.getR(),
                user)));
    }

    @DeleteMapping
    ResponseEntity<?> deleteUserEntries(Principal principal) {
        System.out.println("Delete_entry");
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.deleteByUser(user));
    }
}
