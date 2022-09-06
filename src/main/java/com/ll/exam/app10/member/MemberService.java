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
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final MemberRepository memberRepository;
    public Member getMemberBymembername(String membername) {
        return memberRepository.findByMembername(membername);
    }

    public Member join(String membername, String password, String email, MultipartFile  profileImg) {
        String profileImgPath="member/"+ UUID.randomUUID()+".png";
        File profileImgFIle =new File(genFileDirPath+"/"+profileImgPath);
        profileImgFIle.mkdirs();
        try{
            profileImg.transferTo(profileImgFIle);
        }catch (IOException e){
            throw new RuntimeException();
        }

        Member member=new Member(null,membername,email,password,profileImgPath);

        return memberRepository.save(member);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}
