package com.ll.exam.app10.base;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

}
