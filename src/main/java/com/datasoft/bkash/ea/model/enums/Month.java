package com.datasoft.bkash.ea.model.enums;

public enum Month {
	JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

	private Integer month;

	Month(Integer month) {
		this.month = month;
	}

	public Month getByMonthNumber(Integer month) {
		this.setMonth(month);
		return this;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
