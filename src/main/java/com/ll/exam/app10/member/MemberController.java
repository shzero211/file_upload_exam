package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String memberJoinForm(Model model){
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String memberJoin(String membername,String password,String email,  MultipartFile profileImg,HttpSession session){
        Member findMember=memberService.getMemberBymembername(membername);
        if(findMember!=null){
            return "redirect:/?errorMsg=Already done.";
        }
        Member member=memberService.join(membername,"{noop}"+password,email, profileImg);
        session.setAttribute("loginedMemberId",member.getId());

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String memberProfile(HttpSession session,Model model){
        Long loginedMemberId=(Long)session.getAttribute("loginedMemberId");
        boolean isLogined=loginedMemberId!=null;

        if(isLogined==false){
            return "redirect:/?errorMsg=Need to login!";
        }
        Member loginedMember=memberService.getMemberById(loginedMemberId);
        model.addAttribute("loginedMember",loginedMember);
        return "/member/profile";
    }
}
