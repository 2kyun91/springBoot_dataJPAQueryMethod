package com.example.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
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
	
	// 페이징 처리 조건(정렬은 위와 같이 쿼리에 포함시키지 않고 기동 메소드에서 파라미터로 전달)
	public List<Board> findByBnoGreaterThan(Long bno, Pageable pageable);
	
	// 결과 데이터가 여러 개인 경우 List<T> 타입을 이용하기도 하지만 Page<T> 타입을 이용하면 단순 데이터 외의 웹에서 필요한 여러 데이터를 리턴한다.(총 페이지, 총 개수, 페이지 사이즈 등)
	public Page<Board> findByBnoGreaterThanOrderByTitleDesc(Long bno, Pageable pageable);
}
