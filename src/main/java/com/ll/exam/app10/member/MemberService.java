package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final MemberRepository memberRepository;
    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member join(String username, String password, String email, MultipartFile  profileImg) {
        String profileImgDirName = "member";
        String fileName = UUID.randomUUID().toString() + ".png";
        String profileImgDirPath = genFileDirPath + "/" + profileImgDirName;
        String profileImgFilePath = profileImgDirPath + "/" + fileName;

       new File( profileImgDirPath).mkdirs();
        try{
            profileImg.transferTo(new File(profileImgFilePath));
        }catch (IOException e){
            throw new RuntimeException();
        }
        String profileImgRelPath = profileImgDirName + "/" + fileName;

        Member member=new Member(null,username,email,password,profileImgRelPath);

        return memberRepository.save(member);
    }
    public Member join(String username,String password,String email){
        Member member=Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        memberRepository.save(member);
        return member;

    }
    public long count(){
        return memberRepository.count();
    }
    public void removeProfileImg(Member member){
        member.removeProfileImgOnStorage();
        member.setProfileImg(null);
        memberRepository.save(member);
    }
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberRepository.findByUsername(username);
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("member"));
        return new User(member.getUsername(),member.getPassword(),authorities);
    }
}
