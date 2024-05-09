package roomescape.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dto.request.MemberCreateRequest;
import roomescape.dto.response.TokenResponse;
import roomescape.service.MemberService;

@RestController
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberCreateRequest request, HttpServletResponse response) {
        TokenResponse tokenResponse = service.createToken(request);

        Cookie cookie = new Cookie("token", tokenResponse.accessToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
