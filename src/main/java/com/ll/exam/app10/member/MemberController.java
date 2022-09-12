package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/join")
    public String memberJoinForm(Model model){
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String memberJoin(HttpServletRequest req, String membername, String password, String email, MultipartFile profileImg, HttpSession session){
        Member findMember=memberService.getMemberBymembername(membername);
        String passwordClearText=password;
        password=passwordEncoder.encode(password);
        if(findMember!=null){
            return "redirect:/?errorMsg=Already done.";
        }
        Member member=memberService.join(membername,password,email, profileImg);
        try {
            req.login(membername,passwordClearText);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String memberProfile(Principal principal,Model model){
        Member loginedMember=memberService.getMemberBymembername(principal.getName());
        model.addAttribute("loginedMember",loginedMember);
        return "/member/profile";
    }
}
