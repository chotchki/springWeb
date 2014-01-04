package db.migration;

import db.Migration;

public class V3__Items_Table extends Migration {

	@Override
	public String[] getSQLs() {
		return new String[] {
			"CREATE TABLE Items (" + 
				" id integer generated by default as identity primary key," + 
				" parentId integer," + 
				" defaultId integer," + 
				" name varchar(255) not null," + 
				" date TIMESTAMP with time zone not null," + 
				" mimeType varchar(255) not null," + 
				" hash binary varying(32) not null," + 
				" CONSTRAINT parent_fk foreign key (parentId) REFERENCES Items(id) ON UPDATE CASCADE ON DELETE CASCADE," + 
				" CONSTRAINT default_fk foreign key (defaultId) REFERENCES Items(id) ON UPDATE CASCADE ON DELETE CASCADE" + 
				")"};
	}
}