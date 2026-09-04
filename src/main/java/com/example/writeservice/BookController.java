package com.example.writeservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 도서 관련 HTTP 요청을 처리하는 REST 컨트롤러입니다.
 * 클라이언트가 전달한 요청 데이터를 서비스 계층으로 전달합니다.
 */
@RestController
@RequiredArgsConstructor
public class BookController {

    // 생성자 주입을 통해 도서 등록 비즈니스 로직을 호출합니다.
    private final BookService bookService;

    /**
     * 새로운 도서를 등록합니다.
     * 요청 본문의 JSON 데이터는 Spring에 의해 BookDTO 객체로 변환됩니다.
     *
     * @param bookDTO 등록할 도서 정보
     * @return 등록 성공 메시지
     */
    @PostMapping("/cqrs/book")
    public String saveBook(@RequestBody BookDTO bookDTO) {
        // 실제 데이터 변환과 저장은 서비스 계층에 위임합니다.
        bookService.saveBook(bookDTO);

        return "success";
    }
}
