package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String memberJoinForm(Model model){
        return "member/joinForm";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String memberJoin(HttpServletRequest req, String username, String password, String email, MultipartFile profileImg, HttpSession session){
        Member findMember=memberService.getMemberByUsername(username);
        String passwordClearText=password;
        password=passwordEncoder.encode(password);
        if(findMember!=null){
            return "redirect:/?errorMsg=Already done.";
        }
        Member member=memberService.join(username,password,email, profileImg);
        try {
            req.login(username,passwordClearText);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String showProfile() {
        return "member/profile";
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(){
        return "member/login";
    }

    @GetMapping("/profile/img/{id}")
    public ResponseEntity<Object> showProfileImg(@PathVariable Long id) throws URISyntaxException {
        URI redirectUri = new URI(memberService.getMemberById(id).getProfileImgUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        httpHeaders.setCacheControl(CacheControl.maxAge(60 * 60 * 1, TimeUnit.SECONDS));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }
}
