package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;

@Controller
public class JpaBoardController {
	@Autowired
	private JpaBoardService jpaService;
	
	@RequestMapping(value="/jpa/board", method=RequestMethod.GET)
	public ModelAndView jpaOpenListBoard(ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("/jpa/jpaListBoard");
		
		List<BoardEntity> jpaList = jpaService.jpaSelectListBoard();
		mv.addObject("jpaList", jpaList);
		
		return mv;
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}",method=RequestMethod.GET)
	public ModelAndView jpaOpenDetailBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/jpa/jpaDetailBoard");
		
		//이 선언 부분 좀 연구해보기
		BoardEntity jpaBoard = jpaService.jpaSelectDetailBoard(boardIdx);
		mv.addObject("jpaBoard", jpaBoard);
		
		return mv;
	}
	
	@RequestMapping(value="/jpa/board/write",method=RequestMethod.GET)
	public String jpaWriteBoard() throws Exception{
		return "jpa/jpaWriteBoard";
	}
	
	//jpa의 save 메서드는 insert 기능과 update 기능을 동시에 수행 가능.
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.POST)
	public String jpaInsertBoard(BoardEntity jpaBoard, MultipartHttpServletRequest multiFile) throws Exception{
		jpaService.saveBoard(jpaBoard, multiFile);
		
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.PUT)
	public String jpaUpdateBoard(BoardEntity jpaBoard) throws Exception{
		jpaService.saveBoard(jpaBoard, null);
		
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.DELETE)
	public String jpaDeleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		jpaService.deleteBoard(boardIdx);
		
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
	public void downloadBoardFile(int boardIdx, int idx, HttpServletResponse response) throws Exception{
		BoardFileEntity file = jpaService.jpaSelectBoardFileInfo(boardIdx, idx);
		
		byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(files.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalFileName(), "UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		response.getOutputStream().write(files);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

}
