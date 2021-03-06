package us.henge.db.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import us.henge.db.pojo.Item;

public interface ItemsDao {
	public final static int ALBUMS_PER_PAGE = 1;
	
	public final String selectList = "id, parentId, defaultId, name, date, mimeType, hash";
	public final String typeCase = ", rtrim(CASE WHEN parentId is null THEN 'ALBUM' WHEN (SELECT i.parentId from Items i where i.id = parentId) is null THEN 'PHOTO' ELSE 'VERSION' END) itemType";
	
	@Select({"select", selectList, typeCase, "from items where id = #{id}"})
	public Item getItemById(int id);
	
	@Select({"select", selectList, typeCase, "from items where parentId = #{id}"})
	public List<Item> getItemsByParentId(int id);
	
	@Select({"select", selectList, typeCase, "from items where parentId is null order by date desc OFFSET #{offset} LIMIT " + ALBUMS_PER_PAGE})
	public List<Item> getAlbumsByPage(int offset);
	
	@Select("select GREATEST(CEILING(count(*) / " + ALBUMS_PER_PAGE + ".0),1) from items where parentId is null")
	public int getAlbumCount();
	
	@Select({"select", selectList, typeCase, "from Items where hash = #{hash}"})
	public List<Item> getItemsByHash(@Param("hash") byte[] hash);
	
	@Insert({"insert into items (", selectList, ") values (#{id}, #{parentId}, #{defaultId}, #{name}, #{date}, #{mimeType}, #{hash})"})
	@SelectKey(statement = "CALL IDENTITY()", before = false, keyProperty = "id", resultType = BigDecimal.class)
	public int create(Item item);
	
	@Update({"update items",
		 "set parentId = #{parentId},",
		 "defaultId = #{defaultId},",
		 "name = #{name},",
		 "date = #{date},",
		 "mimeType = #{mimeType},",
		 "hash = #{hash}",
		 "where id = #{id}"})
	public void update(Item item);
	
	@Delete("delete from items where id = #{id}")
	public void delete(int id);
}