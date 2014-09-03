package com.xingyou5.model.user;

import java.util.Comparator;

import com.xingyou5.model.user.entity.Uresource;

public class CompareMenuModel  implements Comparator<Uresource>{


	public int compare(Uresource o1, Uresource o2) {
		return o1.getRank().compareTo(o2.getRank());
	}
}
