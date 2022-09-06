package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Value("${custom.getFileDirPath}")
    private String genFileDirPath;

    private MemberRepository memberRepository;
    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member join(String username, String password, String email, MultipartFile imgFile) {
        String profileImgPath="member/"+ UUID.randomUUID()+".png";
        File profileImgFIle =new File(genFileDirPath+"/"+profileImgPath);
        profileImgFIle.mkdirs();
        try{
            imgFile.transferTo(profileImgFIle);
        }catch (IOException e){
            throw new RuntimeException();
        }

        Member member=new Member(null,username,password,email,profileImgPath);

        return memberRepository.save(member);
    }
}
