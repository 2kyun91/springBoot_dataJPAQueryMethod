package com.example.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.dao.Board;

/*
 * spring data JPA는 메소드의 이름으로 원하는 질의를 실행할 수 있다.
 * 
 * find...By...
 * read...By...
 * query...By...
 * get...By...
 * count...By...
 * 
 * 위와 같은 형식으로 메소드 명을 지정해 질의를 할 수 있는데 find 뒤에 엔티티 타입을 지정하고(findBoardBy...) By 뒤에는 칼럼명을 지정(findBoardByTitle)한다.
 * 쿼리 메소드의 리턴 타입은 Page<T>, Slice<T>, List<T>와 같은 Collection<T> 형태이다.
 * 
 * 특정 칼럼의 값을 조회할 때는 쿼리 메소드의 이름을 findBy로 시작하는 방식을 사용한다.
 * Collection<T> findBy + 속성이름(속성타입)
 * 
 * 쿼리 메소드들에는 모든 쿼리 메소드의 마지막 파라미터로 페이지 처리를 할 수 있는 Pageable 인터페이스와 정렬을 처리하는 Sort 인터페이스를 사용할 수 있다.
 * pageable 인터페이스를 적용하는 경우 리턴 타입은 Collection<T>가 될 수 없고 List<T>, Page<T>, Slice<T> 타입을 사용해야 한다.
 * */
public interface BoardRepository extends CrudRepository<Board, Long>{
	
	public List<Board> findBoardByTitle(String title);
	
	public Collection<Board> findByWriter(String writer);
 	
	// Containing(%조건%) / Like(조건) / StartingWith(조건%) / EndingWith(%조건)
	public Collection<Board> findByWriterContaining(String writer);
	
	// and, or 조건
	public  Collection<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	// GreaterThan, LessThan 조건
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String title, Long bno);
	
	// sort 조건
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	// 페이징 처리, 정렬 조건
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable pageable);
}
