package com.example.writeservice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 도서 정보를 JSON 문자열로 구성하여 Kafka 토픽에 발행하는 서비스입니다.
 * BookService에서 DB 저장 후 생성된 bid를 DTO에 설정하고 이 서비스를 호출합니다.
 */
@Service // Spring이 관리하는 서비스 빈으로 등록
@RequiredArgsConstructor // final 필드를 받는 생성자를 Lombok이 생성하여 생성자 주입에 사용
public class KafkaProducer {

    // 도서 정보를 발행할 Kafka 토픽 이름입니다.
    private static final String TOPIC = "cqrs-topic";

    // 메시지 키와 값의 타입이 모두 String인 Kafka 전송 도구를 주입받습니다.
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 도서 정보를 cqrs-topic으로 비동기 발행 요청합니다.
     *
     * @param bookDTO DB에서 생성된 bid와 발행할 도서 정보를 담은 DTO
     */
    public void sendMessage(BookDTO bookDTO) {
        // 강의 예제에 따라 JSON 문자열을 직접 조합합니다.
        // bid, pages, price도 큰따옴표로 감싸므로 JSON에서는 문자열 값으로 전달됩니다.
        // 입력값의 따옴표, 역슬래시, 줄바꿈을 이스케이프하지 않아 해당 문자가 있으면 JSON이 깨질 수 있습니다.
        String message =
                "{"
                + "\"bid\":\"" + bookDTO.getBid() + "\","
                + "\"title\":\"" + bookDTO.getTitle() + "\","
                + "\"author\":\"" + bookDTO.getAuthor() + "\","
                + "\"category\":\"" + bookDTO.getCategory() + "\","
                + "\"pages\":\"" + bookDTO.getPages() + "\","
                + "\"price\":\"" + bookDTO.getPrice() + "\","
                + "\"published_date\":\"" + bookDTO.getPublished_date() + "\","
                + "\"description\":\"" + bookDTO.getDescription() + "\""
                + "}";

        // 키를 지정하지 않고 토픽과 메시지 값을 전달합니다.
        // 전송 결과를 기다리지 않으며, 반환되는 비동기 결과를 현재 코드에서는 확인하지 않습니다.
        kafkaTemplate.send(TOPIC, message);

        // 발행 요청한 내용을 출력합니다. 이 로그 자체가 브로커의 수신 성공을 의미하지는 않습니다.
        System.out.println("Kafka Send: " + message);
    }
}
