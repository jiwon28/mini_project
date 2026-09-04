package com.example.readservice;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @GetMapping("/cqrs/book")
    public ResponseEntity<List<Document>> getBooks() {

        MongoClient mongoClient =
                MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database =
                mongoClient.getDatabase("mymongo");

        MongoCollection<Document> mongoBooks =
                database.getCollection("books");

        List<Document> list = new ArrayList<>();

        FindIterable<Document> documents = mongoBooks.find();

        for (Document document : documents) {
            list.add(document);
        }

        mongoClient.close();

        return ResponseEntity.ok(list);
    }
}
