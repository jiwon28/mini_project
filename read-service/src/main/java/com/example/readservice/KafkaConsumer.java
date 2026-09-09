package com.example.readservice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka로 받은 도서 정보를 MongoDB의 읽기 모델에 저장하는 서비스입니다.
 */
@Service
public class KafkaConsumer {

    // adamsoft 그룹으로 cqrs-topic을 구독하고 수신한 문자열을 consume()에 전달합니다.
    @KafkaListener(topics = "cqrs-topic", groupId = "adamsoft")
    public void consume(String message) {
        // 수신 로그이며, MongoDB 저장 완료를 의미하지는 않습니다.
        System.out.println("Kafka Received: " + message);

        // Producer가 구성한 JSON 문자열에서 도서 필드를 꺼낼 준비를 합니다.
        JSONObject messageObj = new JSONObject(message);

        // 강의 예제처럼 직접 연결하되, 예외가 발생해도 클라이언트가 닫히도록 합니다.
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("mymongo");
            MongoCollection<Document> mongoBooks = database.getCollection("books");

            // 문자열로 전달된 bid, pages, price는 숫자로 변환하여 저장합니다.
            Document mongoBook = new Document();
            mongoBook.append("bid", messageObj.getLong("bid"));
            mongoBook.append("title", messageObj.getString("title"));
            mongoBook.append("author", messageObj.getString("author"));
            mongoBook.append("category", messageObj.getString("category"));
            mongoBook.append("pages", messageObj.getInt("pages"));
            mongoBook.append("price", messageObj.getInt("price"));
            mongoBook.append("published_date", messageObj.getString("published_date"));
            mongoBook.append("description", messageObj.getString("description"));

            // books 컬렉션에 새 문서를 추가합니다. 동일 메시지를 다시 받으면 중복 저장될 수 있습니다.
            mongoBooks.insertOne(mongoBook);
        }
    }
}
