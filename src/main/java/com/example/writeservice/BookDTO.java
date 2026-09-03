package com.example.writeservice; // 현재 클래스가 속한 패키지

import lombok.Data;

/**
 * HTTP 요청으로 전달받은 도서 데이터를 계층 간에 운반하는 DTO(Data Transfer Object)입니다.
 * 데이터베이스 테이블과 직접 연결되는 엔티티와 요청 데이터를 분리하기 위해 사용합니다.
 */
@Data // 모든 필드의 getter, setter, toString(), equals(), hashCode()를 Lombok이 자동 생성
public class BookDTO {

    private String title; // 도서 제목
    private String author; // 저자 이름
    private String category; // 도서 분류
    private int pages; // 도서의 전체 페이지 수
    private int price; // 도서 가격
    private String published_date; // HTTP 요청에서 문자열로 전달받는 도서 출판일
    private String description; // 도서 설명
}
