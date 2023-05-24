package com.example.springGilman.controller;

/*
 * MemberController는 무엇이고 어떤 기능을 하는가?
 * Spring에는 Model-View-Controller MVC 패턴이 있다고 한다.
 * 멤버컨트롤러는, /members 로 시작하는 URL 경로에 대한 HTTP 요청을 처리하고, 이에 대한 응답을 반환하는 역할을 한다
 * 밑에도 설명되어 있는데, 컨트롤러는 직접 비즈니스 로직을 처리하는 대신 해당 로직을 서비스 계층에 위임한다.
 * 밑에 세가지 (회원 등록, 정보 저장, 모든 정보 조회) 로직을 수행하고, 결과를 사용자에게 전달
 **/

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Member;
import java.util.List;

@Controller
/*@Controller 이거는 해당 클라스가 스프링의 컨트롤러 역할을 한다는 것을 나타냄
컨트롤러는 사용자의 HTTP 요청을 받아 처리하고, 그에 대한 응답 생성*/

@RequestMapping("members")
/*
 * 컨트롤러가 처리하는 요청의 URL이 members로 시작한다, 즉 members로 시작하는 경로는 이 컨트롤러에서 받는다는 뜻
 **/
public class MemberController {
    /*
     * MS 타입의 타입의 멤버 변수로, 멤버 관련 동작(ex. 등록, 조회) 에 대한 비즈니스 로직 처리. 한번만 초기화 가능
     *  **/
    private final MemberService memberService;

    /*
     * 이 것은 생성자입니다.
     * @Autowired라는 어노테이션은 MemberController 객체를 실행해야 할 때 필요한 의존성을 주입해달라고
     * 선언하기 위해 명시하는 어노테이션이며, 생성자 주입 방식을 선언하고 있습니다.
     * MemberController의 필드를 MemberService 타입으로 선언하였지만,
     * 생성자 parameter에는 MemberServiceImpl이 주입되게 함으로써
     *  느슨한 결합(Loosen Coupling)을 구현하였습니다.
     * @참고 : 실제로는 MemberController 생성자의 파라미터에 MemberServiceImpl이 아니라 MemberService로 쓰여있어도
     * 스프링이 알아서 구현체 클래스의 인스턴스 (MemberServiceImpl memberserviceimpl)를 넣어주게 됩니다.
     *       즉, public MemberController(MemberService memberService) {this.memberService = memberService;}
     * 와 같이 작성해도 에러가 없고, 이게 사실 정석입니다.
     *       아래처럼 작성해 둔 이유는, 실제로는 아래와 같이 동작한다는 것을 여러분 눈으로 먼저 보길 바랐던 제 마음이었습니다.
     *       지금, MemberController의 필드가 MemberService 타입의 데이터인데, 생성자로 주입되는 것은
     * MemberServiceImpl 타입이라는 것을 충분히 음미하시길 바랍니다.
     **/
    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }

    /* html 파일 랜더링, createForm. 새로운 회원을 등록
    /members/new method=GET.
     * http 요청 정보 가져온다,  httpGET 요청 왔을때 html 파일 렌더링 해준다 templates에 찾아가서
     * 이 메서드는 GET요청이 오면 호출되고, 새로운 멤버를 생성한다. 새로운 Member 객체를 memberForm이라는 이름으로 모델에 추가하고
     * members/createMemberForm 이라는 view 를 반환한다.
     **/
    @GetMapping("new")
    public String createForm(Model model) {
        model.addText("memberForm", new Member());
        return "members/createMemberForm";
    }

    /* httppost 요청오면 정보 저장, 경로 지정, create. 실제로 회원 정보를 받아 저장
     /members/new method 여기에 POST 요청 오면 호출됨
     httppost 요청이 왔을 때 Member 정보 memberService 통해 저장, 경로 어디론가 가라 지정 (여기서는 메인 페이지)
     **/
    @PostMapping("new")
    public String create(Member member) {
        memberService.save(member);
        return "redirect:/";
    }

    /* 모든 유저의 정보를 가져오라, findall. 모든 회원 정보 조회
     * 겟매핑이 또 나왔는데, 이녀석은 /members 으로 GET 요청이 오면
     * memberService를 통해 모든 유저의 정보를 조회하고,
     * memberList에 추가한다
     *
     **/
    @GetMapping("")
    public String findAll(Model model) {
        List<Member> memberList = memberService.findAll();
        model.addText("memberList", memberList);
        return "members/memberList";
    }

/*
    컨트롤러가 직접 비즈니스 로직(핵심 기능)을 처리하지 않고 그 처리를 서비스 계층에 맡긴다
    예를 들어, 사용자가 상품을 구매하는 요청을 보냈을 때, 컨트롤러는 이 요청을 받아 서비스 계층의 상품 구매 메서드를 호출한다.
    그런 다음 서비스 계층이 상품 구매를 처리하고 그 결과를 컨트롤러에게 전달하면, 컨트롤러는 이 결과를 사용자에게 보여준다.

    이렇게 하면 비즈니스 로직 처리와 요청 처리를 분리할 수 있어 코드가 깔끔해지고 유지보수가 쉬워진다고 한다
     또한 재사용성이 높아지고 코드의 테스트도 용이해진다.

     */