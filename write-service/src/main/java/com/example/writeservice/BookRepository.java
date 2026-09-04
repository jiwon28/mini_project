/*
DB 접근을 위한 Repository 인터페이스
 */

package com.example.writeservice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Book 엔티티의 데이터베이스 작업을 담당하는 JPA 저장소입니다.
 *
 * <p>{@link JpaRepository}를 상속하므로 별도의 구현 없이 도서의 저장, 조회,
 * 수정, 삭제와 같은 기본 CRUD 기능을 사용할 수 있습니다.</p>
 *
 * <ul>
 *     <li>{@code Book}: 저장소가 관리하는 엔티티 타입</li>
 *     <li>{@code Long}: Book 엔티티의 기본 키 타입</li>
 * </ul>
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
