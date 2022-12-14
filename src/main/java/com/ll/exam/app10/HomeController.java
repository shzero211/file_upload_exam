package com.ll.exam.app10;

import com.ll.exam.app10.member.Member;
import com.ll.exam.app10.member.MemberContext;
import com.ll.exam.app10.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;
    @GetMapping("/")
    public String main(Principal principal, Model model){

        return "home/main";
    }
    @GetMapping("/about")
    public String showAbout(Principal principal,Model model){

        return "home/about";
    }
    @GetMapping("/currentUserOrigin")
    @ResponseBody
    public Principal currentUserOrigin(Principal principal) {
        return principal;
    }

    @GetMapping("/currentUser")
    @ResponseBody
    public MemberContext currentUser(@AuthenticationPrincipal MemberContext memberContext) {
        return memberContext;
    }
}
