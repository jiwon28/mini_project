package com.example.writeservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 도서 등록과 관련된 비즈니스 로직을 처리하는 서비스입니다.
 * DTO로 전달받은 값을 Book 엔티티로 저장한 뒤 Kafka 메시지 발행을 요청합니다.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    // 생성자 주입을 통해 도서 데이터베이스 작업을 수행합니다.
    private final BookRepository bookRepository;

    // 생성자 주입을 통해 도서 정보를 Kafka에 발행하는 서비스를 사용합니다.
    private final KafkaProducer kafkaProducer;

    /**
     * 도서 정보를 데이터베이스에 저장하고 생성된 ID를 포함하여 Kafka 발행을 요청합니다.
     *
     * @param bookDTO 저장할 도서 정보
     * @throws IllegalArgumentException 출판일이 yyyy-MM-dd 형식이 아닌 경우
     */
    @Transactional
    public void saveBook(BookDTO bookDTO) {
        try {
            // 문자열로 전달된 출판일을 Book 엔티티에서 사용하는 Date 타입으로 변환합니다.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formatter.setLenient(false);

            Date publishedDate = formatter.parse(bookDTO.getPublished_date());

            // 외부 요청 DTO를 데이터베이스에 저장할 Book 엔티티로 변환합니다.
            Book book = Book.builder()
                    .title(bookDTO.getTitle())
                    .author(bookDTO.getAuthor())
                    .category(bookDTO.getCategory())
                    .pages(bookDTO.getPages())
                    .price(bookDTO.getPrice())
                    .published_date(publishedDate)
                    .description(bookDTO.getDescription())
                    .build();

            // JpaRepository가 제공하는 save()를 사용하여 도서 정보를 저장합니다.
            Book savedBook = bookRepository.save(book);

            // JPA가 생성한 책의 PK를 Kafka 메시지에도 포함합니다.
            bookDTO.setBid(savedBook.getBid());

            // DB 트랜잭션 커밋 전 비동기 발행 요청이며, DB 저장과 Kafka 전송은 원자적으로 보장되지 않습니다.
            kafkaProducer.sendMessage(bookDTO);
        } catch (ParseException e) {
            // 오류를 숨기지 않고 호출 계층에서 잘못된 요청을 처리할 수 있도록 전달합니다.
            throw new IllegalArgumentException("출판일은 yyyy-MM-dd 형식이어야 합니다.", e);
        }
    }
}
