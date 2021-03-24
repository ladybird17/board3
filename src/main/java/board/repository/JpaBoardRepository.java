package board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

/*
mybatis의 mapper 파일 및 xml파일이 jpa에서는 Repository 파일
스프링 데이터의 jpa repository 인터페이스.
Repository -> CrucRepository -> PagingAndSortingRepository -> JpaRepository 순서대로 상속받아 사용함

-Repository 인터페이스는 아무런 기능이 없음(잘 사용하지 않음)
-CrudRepository 인터페이스는 기본적인 CRUD(create, read, update, delete) 기능을 제공함.
-PagingAndSortingRepository 인터페이스는 CrudRepository 인터페이스의 기능에 페이징 및 정렬 기능이 추가된 인터페이스
-JpaRepository 인터페이스는 PagingAndSortingRepository의 기능에 jpa에 특화된 기능까지 추가된 인터페이스

CrudRepository에서 지원하는 메서드
<S extends T> save(s entity) : 주어진 엔티티를 저장
<S extends T> Iterable<S> saveAll(Iterable<S> entities) : 주어진 엔티티 목록을 저장
Option<T> findById(Id id) : 주어진 아이디로 식별된 엔티티를 반환
boolean exitsById(Id id) : 주어진 아이디로 식별된 엔티티가 존재하는지를 반환
Iterable<T> findAll() : 모든 엔티티를 반환
Iterable<T> findAllById(Iterable<ID> ids) : 주어진 아이디 목록에 맞는 모든 엔티티 목록을 반환
long count() : 사용가능한 엔티티의 개수를 반환
void deleteById(Id id)  : 주어진 아이디로 식별된 엔티티를 삭제함
void delete(T entity) : 주어진 엔티티를 삭제함
void deleteAll(Iterable<? extends T> entities : 주어진 엔티티 목록으로 식별된 엔티티를 모두 삭제
void deleteAll : 모든 엔티티를 삭제

스프링 데이터 JPA는 쿼리메서드를 지원하여 원하는 형태의 쿼리를 생성하는 기능이 존재함.
- find...By, read...By, query...By, count...By와 같은 형태로 사용함
- 첫번째 By의 뒷쪽은 컬럼명, 앞쪽은 crud 중 실행할 형태를 선택
- JPA의 쿼리메서드를 통해서 제목 검색 시 findyByTitle(String title) 형태로 메서드를 작성
- 2개 이상의 속성을 조합하여 검색시 AND 키워드를 사용
- findByTitleAndContent(String title, String contents)

<JPA에서 사용하는 연산자 목록>
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

ex)AND 사용시 실제 쿼리로 변환 
-> findByLastnameAndFirstname 
=> where x.lastname = ?1 and x.firstname = ?2

ex)제목(안녕하세요), 글쓴이(test)를 가지고 검색
-> findByTitleAndCreatedId(String title, String userId)
=> select * from t_jpa_board where title = '안녕하세요' and created_id = 'test'

<@Query 어노테이션 사용>
메서드의 이름이 복잡하거나 쿼리메서드로 표현하기 힘들 경우
@Query 어노테이션을 사용하여 쿼리를 직접 작성 가능.

첫번째 방식)
@Query("SELECT file FROM BoardFileEntity file WHERE board_idx = ?! AND idx = ?2")
BoardFileEntity findBoardFile(int boardIdx, int idx);
- [?숫자] 형식으로 파라미터를 지정, 메서드의 매개변수 순서대로  ?1, ?2에 할당됨
- boardIdx는 ?1에 할당, idx는 ?2에 할당.

두번째 방식)
@Query("SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
- :[변수명] 형식으로 파라미터를 지정, 변수명은 @Param 어노테이션에 대응함.
- :boardIdx는 @Param("boardIdx")에 할당, :idx는 @Param("idx")에 할당

*/
public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer> {
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	@Query("SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
}
