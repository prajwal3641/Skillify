package com.infosys.controller;

import com.infosys.model.Genie;
import com.infosys.model.Wish;
import com.infosys.model.Wishmaker;
import com.infosys.repository.GenieRepository;
import com.infosys.repository.WishRepository;
import com.infosys.repository.WishmakerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccessWithIdController {


    private final WishRepository wishRepository;
    private final WishmakerRepository wishmakerRepository;
    private final GenieRepository genieRepository;

    @GetMapping("/getGenie")

    public ResponseEntity<Genie> getGenie(@RequestParam String email, Authentication authentication) {
        if(authentication.getName().equals(email)){
            Genie genie = genieRepository.findByEmail(email).get();
            genie.setWishes(wishRepository.getWishByGenieId(genie.getId()));
            return ResponseEntity.ok().body(genie);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getWishmaker")
    @PostAuthorize("returnObject.email == authentication.email")
    public ResponseEntity<Wishmaker> getWishmaker(@RequestParam String email) {
        return ResponseEntity.ok().body(wishmakerRepository.findByEmail(email).get());
    }

    @GetMapping("/getWishes")
    public ResponseEntity<Iterable<Wish>> getWishes() {
        List<Wish> wishes = wishRepository.findAll();
        return ResponseEntity.ok().body(wishes);
    }

    @GetMapping("/getWishesByWishMakerId")
    public ResponseEntity<Iterable<Wish>> getWishesByWishMakerEmail(@RequestParam String email) {
        Wishmaker wishmaker = wishmakerRepository.findByEmail(email).get();

        return ResponseEntity.ok().body(wishRepository.findByWishmakerId(wishmaker.getId()));
    }

    @GetMapping("/getWishByGenieId")
    public ResponseEntity<Iterable<Wish>> getWishByGenieId(@RequestParam String email) {
        Genie genie = genieRepository.findByEmail(email).get();
        return ResponseEntity.ok().body(wishRepository.getWishByGenieId(genie.getId()));
    }

    @GetMapping("/getWishmakerByWishId")
    public ResponseEntity<Wishmaker> getWishmakerByWishId(@RequestParam Long wishId) {
        Wish wish = wishRepository.findById(wishId).get();
        if(wish != null){
            return ResponseEntity.ok().body(wish.getWishmaker());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
