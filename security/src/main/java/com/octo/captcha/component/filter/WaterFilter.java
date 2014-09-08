package com.octo.captcha.component.filter;

import java.awt.Rectangle;

import com.xingyou5.module.base.util.RandomUtils;


public class WaterFilter extends com.jhlabs.image.WaterFilter {
	private static final long serialVersionUID = 1L;

	@Override
	protected void transformSpace(Rectangle arg0) {
		double phase = RandomUtils.nextDouble()*1000;
		this.setPhase(phase);
		super.transformSpace(arg0);
	}
}
