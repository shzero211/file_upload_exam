package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
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
import java.security.Principal;

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
    public String showProfileImg(@PathVariable Long id) {
        return "redirect:" + memberService.getMemberById(id).getProfileImgUrl();
    }
}
