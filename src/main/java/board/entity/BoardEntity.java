package board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

// entity란? 저장되고, 관리되어야하는 데이터의 집합을 의미

@Entity // 아래의 클래스가 entity임을 표시함
@Table(name="t_jpa_board") // 실제 데이터 베이스에서 t_jpa_board 테이블과 매칭됨
@NoArgsConstructor
@Data
public class BoardEntity {
	@Id // PK, 기본키임을 표시
	@GeneratedValue(strategy=GenerationType.AUTO) // 데이터 베이스의 기본키 전략을 사용하여 키를 생성함, mysql은 자동증가를 지원하기 때문에 현재 자동증가 옵션을 사용함
	private int boardIdx;
	
	@Column(nullable=false) // 데이터 베이스의 컬럼을 뜻함 / nullable=false 는 not null을 의미함
	private String title;
	
	@Column(nullable=false)
	private String contents;
	
	@Column(nullable=false)
	private int hitCnt = 0;
	
	@Column(nullable=false)
	private String CreatedId;
	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now(); // 작성 시간의 초기값을 설정, 
	
	@Column(nullable=true)
	private String updatedId;
	
	private LocalDateTime updateDatetime;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL) // DB의 관계 설정 중 1:N 관계를 표현, 
	@JoinColumn(name="board_idx") // 해당 컬럼의 board_idx와 연관이 있음
	private Collection<BoardFileEntity> fileList;
}












