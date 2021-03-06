package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Category extends Model {
	@Id
	@Column(columnDefinition = "char(2)")
	public String num;
	@Column(columnDefinition = "char(32)")
	public String name;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<Book> books = new ArrayList<Book>();

	public static Finder<String, Category> find = new Finder<String, Category>(
			String.class, Category.class);

	public static List<Category> findAll() {
		return find.all();
	}

	public static List<Book> showBook(String sign, int pagesize, int page) { // 显示导航
		Category c = find.where().eq("num", sign).findUnique();
		int BookSum = find.where().eq("num", sign).findUnique().books.size();
		if (pagesize > BookSum)
			pagesize = BookSum;
		int endBook = pagesize * (page + 1);
		if (BookSum < endBook)
			endBook = BookSum;
		return c.books.subList(pagesize * page, endBook);
	}

	public static String getTitle(String sign) { // 导航标题
		return find.where().eq("num", sign).findUnique().name;
	}

	public static int getPageNum(String sign) { // 总页数
		int num = find.where().eq("num", sign).findUnique().books.size();
		if (num % 21 == 0)
			num /= 21;
		else
			num = num / 21 + 1;
		return num;
	}

	public static List<Integer> getPages(int page_now, String sign) { // 页码list
		int page_num = getPageNum(sign);
		int start = page_now - 2;
		int end = page_now + 2;
		if (start < 0)
			start = 0;
		if (end > page_num - 1)
			end = page_num - 1;
		List<Integer> list = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			list.add(new Integer(i));
		}
		return list;
	}
	
	public static Map<String, String> options() {
		LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
		for (Category c : Category.find.orderBy("name").findList()) {
			options.put(c.num.toString(), c.name);
		}
		return options;
	}
}
