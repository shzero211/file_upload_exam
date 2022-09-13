package com.ll.exam.app10.member;

import com.ll.exam.app10.base.AppConfig;
import lombok.*;

import javax.persistence.*;
import java.io.File;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String membername;

    private String email;

    private String password;

    private String profileImg;
    public String getProfileImgPath(){

        return AppConfig.GET_FILE_DIR_PATH+"/"+profileImg;
    }
    public void removeProfileImgOnStorage(){
        if(profileImg==null||profileImg.trim().length()==0)return;
        String profileImgPath=getProfileImg();
        new File(profileImgPath).delete();
    }
}
