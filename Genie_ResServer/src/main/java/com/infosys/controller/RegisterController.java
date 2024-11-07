package com.infosys.controller;

import com.infosys.model.Genie;
import com.infosys.model.Wish;
import com.infosys.model.Wishmaker;
import com.infosys.model.users;
import com.infosys.repository.GenieRepository;
import com.infosys.repository.UserRepository;
import com.infosys.repository.WishRepository;
import com.infosys.repository.WishmakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;
    private final WishRepository wishRepository;
    private final WishmakerRepository wishmakerRepository;
    private final GenieRepository genieRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping(value = "/registerGenie",consumes="application/json")
    public String registerGenie(@RequestBody Genie genie) {

        genie.setPwd(passwordEncoder.encode(genie.getPwd()));
        genie.setRole("ROLE_GENIE");

        // make a object of user
        users user = users.builder().name(genie.getName()).email(genie.getEmail()).role(genie.getRole()).pwd(genie.getPwd()).build();
        userRepository.save(user);
        genieRepository.save(genie);
        return "Genie Registered Successfully";
    }

    @PostMapping("/registerWishmaker")
    public String registerWishMaker(@RequestBody Wishmaker wishmaker) {

        wishmaker.setPwd(passwordEncoder.encode(wishmaker.getPwd()));
        wishmaker.setRole("ROLE_WISHMAKER");

        // make a object of user for the wishmaker also
        users user = users.builder().name(wishmaker.getName()).email(wishmaker.getEmail()).role(wishmaker.getRole()).pwd(wishmaker.getPwd()).build();
        userRepository.save(user);
        wishmakerRepository.save(wishmaker);
        return "Wishmaker Registered Successfully";
    }

    @PostMapping("/insertWish")
    public String insertWish(@RequestBody Wish wish,Authentication authentication) {
        Wishmaker wishmaker = wishmakerRepository.findByEmail(authentication.getName()).get();
        if(wishmaker != null){
            wish.setWishmaker(wishmaker);
            wishRepository.save(wish);
            return "Wish Registered Successfully";
        }else{
            return "Wishmaker not found";
        }
    }

    @PostMapping("/acceptWish")
    public ResponseEntity<Wish> acceptWish(@RequestParam Long wishId,Authentication authentication) {
        Wish wish = wishRepository.findById(wishId).get();
        Genie genie = genieRepository.findByEmail(authentication.getName()).get();
        wish.getRegisteredUsers().add(genie);
        wishRepository.save(wish);
        return ResponseEntity.ok().body( wish);
    }


}
