package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardService {
List<BoardEntity> jpaSelectListBoard() throws Exception;
	
	BoardEntity jpaSelectDetailBoard(int boardIdx) throws Exception;
	
	void saveBoard(BoardEntity jpaBoard, MultipartHttpServletRequest multiFile) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;
	
	BoardFileEntity jpaSelectBoardFileInfo(int boardIdx, int idx) throws Exception;
	
}
