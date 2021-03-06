package db.migration;

import db.Migration;

public class V1__Blog_Table extends Migration {
	@Override
	public String[] getSQLs() {
		return new String[] {"CREATE TABLE Posts ("
				+ "id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, "
				+ "published TIMESTAMP WITH TIME ZONE NOT NULL, "
				+ "title VARCHAR(255) NOT NULL, "
				+ "content VARCHAR(100000) NOT NULL)"};
	}
}