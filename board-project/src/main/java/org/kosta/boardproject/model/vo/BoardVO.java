package org.kosta.boardproject.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardTime;
	private int hits;
	private String csId;
}
