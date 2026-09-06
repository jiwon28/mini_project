package com.example.readservice;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 조회 모델인 MongoDB에서 도서 정보를 읽어 오는 REST 컨트롤러입니다.
 */
@RestController
public class BookController {

    private static final String BOOK_COLLECTION = "books";

    private final MongoTemplate mongoTemplate;

    /**
     * Spring이 관리하는 {@link MongoTemplate}을 주입받습니다.
     * 애플리케이션의 MongoDB 연결 정보는 application.yaml의 설정을 사용합니다.
     */
    public BookController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * books 컬렉션에 저장된 모든 도서를 조회합니다.
     *
     * @return 조회된 도서 목록과 HTTP 200 응답
     */
    @GetMapping("/cqrs/book")
    public ResponseEntity<List<Document>> getBooks() {
        // MongoTemplate이 MongoClient의 생성과 종료를 관리하므로 요청마다 연결을 만들 필요가 없습니다.
        List<Document> books = mongoTemplate.findAll(Document.class, BOOK_COLLECTION);

        return ResponseEntity.ok(books);
    }
}
