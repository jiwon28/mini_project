/*
DB 테이블과 연결되는 Entity
 */

package com.example.writeservice; // 현재 클래스가 속한 패키지

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity // 이 클래스를 JPA가 관리하는 데이터베이스 엔티티로 지정
@Table(name = "book") // 엔티티와 매핑할 데이터베이스 테이블 이름을 book으로 지정
@ToString // 모든 필드 값을 문자열로 표현하는 toString() 메서드를 Lombok이 생성
@Getter // 모든 필드의 getter 메서드를 Lombok이 생성
@Builder // 빌더 패턴으로 Book 객체를 생성할 수 있도록 Lombok이 빌더를 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 Lombok이 생성
@NoArgsConstructor // JPA가 엔티티 객체를 생성할 때 필요한 기본 생성자를 Lombok이 생성
public class Book {

    @Id // 이 필드를 테이블의 기본 키로 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본 키 값을 데이터베이스 환경에 맞는 방식으로 자동 생성
    private Long bid; // 도서를 식별하는 고유 번호

    @Column(length = 50, nullable = false) // 최대 50자이며 null을 허용하지 않는 열로 매핑
    private String title; // 도서 제목

    @Column(length = 50, nullable = false) // 최대 50자이며 null을 허용하지 않는 열로 매핑
    private String author; // 저자 이름

    @Column(length = 50, nullable = false) // 최대 50자이며 null을 허용하지 않는 열로 매핑
    private String category; // 도서 분류

    @Column // 별도 옵션 없이 기본 규칙으로 열에 매핑
    private int pages; // 도서의 전체 페이지 수

    @Column // 별도 옵션 없이 기본 규칙으로 열에 매핑
    private int price; // 도서 가격

    @Column // 별도 옵션 없이 기본 규칙으로 열에 매핑
    private Date published_date; // 도서 출판일

    @Column(length = 50, nullable = false) // 최대 50자이며 null을 허용하지 않는 열로 매핑
    private String description; // 도서 설명
}
