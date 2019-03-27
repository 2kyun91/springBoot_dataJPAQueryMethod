package com.example;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dto.Board;
import com.example.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSpringDataJpaProccesApplicationTests {

	@Autowired
	private BoardRepository boardRepo;
	
	@Test // 임의 데이터 생성
	public void testInsert200() {
		for (int i = 0; i <= 200; i++) {
			Board board = new Board();
			
			board.setTitle(i + "번 글의 제목입니다.");
			board.setContent(i + "번 글의 내용입니다.");
			board.setWriter("user0" + (i % 10));
			
			boardRepo.save(board);
		}
	}
	
	@Test
	public void testByTitle() {
		boardRepo.findBoardByTitle("111번 글의 제목입니다.").forEach(board -> System.out.println("쿼리 메소드 실행 / 제목 : " + board));
	}
	
	@Test
	public void testByWriter() {
		Collection<Board> results = boardRepo.findByWriter("user03");
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / 작성자 : " + board));
	}
	
	@Test
	public void testByWriterContaining() {
		Collection<Board> results = boardRepo.findByWriterContaining("05");
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / %작성자% : " + board));
	}
	
	@Test
	public void testByTitleOrContent() {
		Collection<Board> results = boardRepo.findByTitleContainingOrContentContaining("15번", "15번");
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / 제목 or 내용 : " + board));
	}
	
	@Test
	public void testByTitleAndBno() {
		Collection<Board> results = boardRepo.findByTitleContainingAndBnoGreaterThan("7번", 50L);
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / 제목 and 글번호 보다 큰 : " + board));
	}
	
	@Test
	public void testBnoOrderBy() {
		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(40L);
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / 글번호 보다 크고 역순으로 정렬 : " + board));
	}
	
	@Test
	public void testBnoOrderByPaging() {
		/*
		 * PageRequest(int, int)는 스프링 2.0 이후 부터 사용되지 않는다.
		 * Pageable pageable = new PageRequest(0, 10);
		 * Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(150L, pageable);
		 */
		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(150L, PageRequest.of(0, 10));
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / 글번호 보다 큰 +역순으로 정렬+페이징처리 : " + board));
	}
	
	@Test
	public void testBnoPagingSort() {
		Collection<Board> results = boardRepo.findByBnoGreaterThan(130L, PageRequest.of(0, 10, Sort.Direction.ASC, "bno"));
		results.forEach(board -> System.out.println("쿼리 메소드 실행 / 글번호 보다 큰 +순서대로 정렬+페이징처리 : " + board));
	}
	
	@Test
	public void testBnoPagingSort2() {
		Page<Board> result = boardRepo.findByBnoGreaterThanOrderByTitleDesc(110L, PageRequest.of(0, 10, Sort.Direction.ASC, "bno"));
		System.out.println("페이지 사이즈 : " + result.getSize());
		System.out.println("총 페이지 : " + result.getTotalPages());
		System.out.println("총 개수 : " + result.getTotalElements());
		System.out.println("다음 페이지 : " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(board -> System.out.println(board));
	}

}
