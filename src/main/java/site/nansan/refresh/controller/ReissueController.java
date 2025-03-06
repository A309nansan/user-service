package site.nansan.refresh.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.nansan.refresh.service.ReissueService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/api/v1/reissue")
    public ResponseEntity<Map<String, String>> reissueAccessToken(@RequestHeader("refresh") String refreshToken, HttpServletResponse response) {

        String newAccessToken = reissueService.reissueToken(refreshToken, response);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("access", newAccessToken);

        return ResponseEntity.ok(responseBody);
    }
}
