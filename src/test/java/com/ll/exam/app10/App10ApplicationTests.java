package com.ll.exam.app10;

import com.ll.exam.app10.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles({"base-addi", "test"})
class App10ApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("메인화면에서는 안녕이 나와야한다")
	void t1() throws Exception {
		ResultActions resultActions= mvc.perform(get("/")).andDo(print());
		resultActions.andExpect(status().is2xxSuccessful())
				.andExpect(handler().handlerType(HomeController.class))
				.andExpect(handler().methodName("main"))
				.andExpect(content().string(containsString("안녕")));
	}
	@Test
	@DisplayName("회원의수")
	@Rollback(value = false)
	void t2(){
		long count= memberService.count();
		assertThat(count).isGreaterThan(0);
	}

}
