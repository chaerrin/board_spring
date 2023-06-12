package org.kosta.boardproject.model.vo;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO implements Serializable{	
	private static final long serialVersionUID = 6440047762418162093L;
	private String id;
	private String password;
	private String name;
	private String address;
	private String birth;
	private String enabled;
	private ArrayList<Integer> list;
	
}
