package com.kosmo.k12springapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.IAndroidDAO;
import mybatis.MemberVO;

@Controller
public class AndroidController {
   
   //Mybatis 사용을 위한 자동주입
   @Autowired
   private SqlSession sqlSession;
   
   //매개변수가 필요없이 회원리스트 전체를 JSON으로 반환함
   @RequestMapping("/android/memberList.do")
   @ResponseBody
   public ArrayList<MemberVO> memberList(HttpServletRequest req){
	   
      //Map<String, Object> map = new HashMap<String, Object>();
      
      ArrayList<MemberVO> lists = sqlSession.getMapper(IAndroidDAO.class).memberList();
      //map.put("memberList", lists);
      
      return lists;
   }
   
   /* 
   	회원 아이디, 패스워드를 파라미터로 받아서 해당 회원정보를 반환하는 메소드.
   	만약 회원정보가 틀리다면 isLogin:0 반환함
   */
   @RequestMapping("/android/memberLogin.do")
   @ResponseBody
   public Map<String, Object> memberLogin(MemberVO memberVO) {
	   
	   /*
	   매개변수로 커맨드객쳬(VO)를 사용하므로 파라미터명은 VO의 필드와 동일하게 id, pass, name과 같이 사용하면 된다.
	   요청 URL : http://localhost:8282/k12springapi/android/memberLogin.do?id=kosmo&pass=1111
	    */
	   
	   Map<String, Object> returnMap = new HashMap<String, Object>();
	   
	   MemberVO memberInfo = sqlSession.getMapper(IAndroidDAO.class).memberLogin(memberVO);
	   
	   if(memberInfo == null) {
		   //회원정보가 일치하지 않는다면 0반환
		   returnMap.put("isLogin", 0);
	   }
	   else {
//		   memberMap.put("id", memberInfo.getId());
//		   memberMap.put("pass", memberInfo.getPass());
//		   memberMap.put("name", memberInfo.getNamd());
		   
		   //회원정보가 일치하면 회원정보 전체를 반환한다.
		   returnMap.put("memberInfo", memberInfo);
		   returnMap.put("isLogin", 1);
	   }
	   return returnMap;
	   
   }
   

}















