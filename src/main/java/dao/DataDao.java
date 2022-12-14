package dao;

import java.sql.Connection;


import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Book;

public class DataDao{
	
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;


	public void connect() throws Exception {
		String db_url = "jdbc:oracle:thin:@localhost:1521:orcl"; // 접속 DB정보
		String db_id = "scott"; // 접속 아이디
		String db_pw = "tiger"; // 접속 아이디의 비밀번호

		// 연결 부분

		Class.forName("oracle.jdbc.driver.OracleDriver");
		if (conn != null) {
			conn.close();
		}
		conn = DriverManager.getConnection(db_url, db_id, db_pw);
	}

	public void closeConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
//	public void insert_book_info(ArrayList<Book> bookList) {
//		
//		String sql = " INSERT INTO book VALUES(?,?,?,?,?,?,?,?,?) " ;		
//
//			try {
//				connect();
//				psmt = conn.prepareStatement(sql);
//				
//				
//					for(int i=0; i<bookList.size(); i++) {
//						
//					//북리스트에서 하나씩 받아와서  
//						//FOR문을 돌리면서 arrayList에 있는 객체들을 하나씩 빼서 book에 저장해줘야 함. 
//						Book book = new Book();
//						
//						book = bookList.get(i);
//						
//						
//						psmt.setString(1, book.getIsbn());
//				
//						psmt.setString(2, book.getTitle());
//						//System.out.println(book.getTitle());
//						psmt.setString(3, book.getAuthors());
//						psmt.setString(4, book.getContent());
//						psmt.setString(5, book.getThumbnail());
//						psmt.setInt(6, book.getPrice());
//						psmt.setString(7, book.getDate_time());
//						psmt.setString(8, book.getStatus());
//						//카테고리네임 
//						psmt.setInt(9,book.getCategory_());
//						int resultCnt = psmt.executeUpdate();
//						System.out.println(resultCnt);
//					}
//					
//			
//		
//					
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}finally{
//				closeConnect();
//			}
//			
//		
//		
//	}
//	

//	public book selectBuyBookInfo(String id){
//		String sql = " select * "
//				   + " from book "
//				   + " where isbn = "
//				   + " (select isbn from shop_bskt where id = ? ) ";
//		book bk = null;
//		
//		try {
//			connect();
//			
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, id);
//			
//			rs = psmt.executeQuery();
//			
//			bk = new book();
//			if(rs.next()) {
//				bk.setTitle(rs.getString("title"));
//				bk.setThumbnail(rs.getString("thumbnail"));
//				bk.setPrice(rs.getInt("price"));
//			}
//			System.out.println(bk.toString());
//			return bk;
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeConnect();
//		}
//		
//		return bk;
//	}
	
	public cart selectBook(String isbn) {
		String sql = " select * from shop_bskt ";
		cart ct = null ; 
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, isbn);
			
			rs = psmt.executeQuery();
			
			ct = new cart();
			if(rs.next()) {
				ct.setIsbn(rs.getString("isbn"));
//				bk.setThumbnail(rs.getString("thumbnail"));
//				bk.setPrice(rs.getInt("price"));
			}
			System.out.println(ct.toString());
			return ct;
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnect();
		}
		
		return ct;
	}
	public List<Book> selectBookByCategory(String name)  {

		try {
			connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlQuery = null;
		if(name.equals("전체")) {
			sqlQuery = "select * from book order by date_time desc";
		}else {
			sqlQuery = "select * from book where category_name like '국내도서>'||?||'>%' order by date_time desc";
		}
		System.out.println(sqlQuery);
		List<Book> bookList = null;
		
		try {
			psmt = conn.prepareStatement(sqlQuery);
			
			if(name.equals("전체")) {
				rs = psmt.executeQuery();
			}else {
				psmt.setString(1,name);
				rs = psmt.executeQuery();

			}
			
			
			
			
			

			
			bookList = new ArrayList<Book>();
			while (rs.next()) {
				Book ai = new Book();

				ai.price = rs.getInt("price");
				ai.title = rs.getString("Title");
				ai.thumbnail = rs.getString("thumbnail");
				ai.isbn = rs.getString("isbn");

				bookList.add(ai);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnect();
		}

		return bookList;
	}
	
	public List<Book> selectBuyBookList(String id)  {

		try {
			connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Book> bookList = null;
	String sqlQuery = "select title from book where isbn = (select book_num from buy_book where id = ? )";
		try {
			psmt = conn.prepareStatement(sqlQuery);
			psmt.setString(1,id);
			rs = psmt.executeQuery();
			bookList = new ArrayList<Book>();
			while (rs.next()) {
				Book ai = new Book();

				ai.title = rs.getString("Title");
	

				bookList.add(ai);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnect();
		}

		return bookList;
	}
	
	public List<Book> selectBookforSearch(String value)  {

		try {
			connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Book> bookList = null;
	String sqlQuery = "select * from book where title like '%'||?||'%'  order by date_time desc";
		try {
			psmt = conn.prepareStatement(sqlQuery);
			psmt.setString(1,value);
			rs = psmt.executeQuery();

			

			
			bookList = new ArrayList<Book>();
			while (rs.next()) {
				Book ai = new Book();
				ai.isbn = rs.getString("isbn");
				ai.price = rs.getInt("price");
				ai.title = rs.getString("Title");
				ai.thumbnail = rs.getString("thumbnail");

				bookList.add(ai);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnect();
		}

		return bookList;
	}
	
	

	
	public List<Book> selectBuyBookInfo(String id){
		String sql = " select b.thumbnail, b.title, b.price ,b.isbn from shop_bskt s , book b  where id = ?  and s.isbn = b.isbn ";
//					+ "order by customer_no ";
		List<Book> bk = null;
		
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			rs = psmt.executeQuery();
			
			bk = new ArrayList<Book>();
			
			while(rs.next()) {
				Book bk2 = new Book();
				bk2.setTitle(rs.getString("title"));
				bk2.setThumbnail(rs.getString("thumbnail"));
				bk2.setPrice(rs.getInt("price"));
				bk2.setIsbn(rs.getString("isbn"));
				
				bk.add(bk2);
			}
			return bk;
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnect();
		}
		
		return bk;
	}
	
//	public int deleteBookInCart(String isbn) {
//		String sql = " DELETE FROM shop_bskt WHERE isbn = ()? ";
//		int result = 0; 
//		try {
//			connect();
//			
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, isbn);
//			
//			result = psmt.executeUpdate();
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			closeConnect();
//		}
//		return result;
//	}
	
	
	public int deleteBookInCart(String id, String isbn) {
		String sql = " delete from shop_bskt "
				   + " where isbn = ? and id = ? ";
		int result = 0; 
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, isbn);
			psmt.setString(2, id);
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnect();
		}
		return result;
	}
	
	
	
	


	
	
	
//	public book selectBuyBookCnt(String id){
//		String sql = " select shop_cnt "
//				   + " from shop_bskt "
//				   + " where id = ? ";
//			
//		cart ct = null;
//		
//		try {
//			connect();
//			
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, id);
//			
//			rs = psmt.executeQuery();
//			
//			ct = new cart();
//			if(rs.next()) {
//				ct.setShop_cnt(rs.getInt("shop_cnt"));
//				
//				
//				return ;
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeConnect();
//		}
//		
//		return bk;
//	}
	
	public Customer selectCustomerInfoInMypage(String id){
		String sql = " select * "
				   + " from customer "
				   + " where id = ? ";
				 
		Customer cus = null;
		
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			rs = psmt.executeQuery();
			
			cus = new Customer();
			if(rs.next()) {
				cus.setId(rs.getString("id"));
				cus.setEmail(rs.getString("email"));
				cus.setPassword(rs.getString("password"));
				cus.setCustomer_name(rs.getString("customer_name"));
				cus.setPhone_num(rs.getString("phone_num"));
				cus.setAddress(rs.getString("address"));
				
				
			}
			System.out.println(cus.toString());
			return cus;
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnect();
		}
		
		return cus;
	}
	
	

	public int updateBookInBskt(int cnt, String isbn,String id) {
		String sql = " update shop_bskt set shop_cnt = ? where isbn = ? and id = ?";
		int result = 0; 
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cnt);
			psmt.setString(2, isbn);
			psmt.setString(3, id);
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnect();
		}
		return result;
	}
	
	
	
	public int updateCustomerInfo(Customer customer) {
		String sql = " UPDATE customer "
					 + " SET password = ? , email = ? , phone_num = ? , address = ? "
					 + "where id = ? "; 
		int result = 0; 
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, customer.getPassword());
			psmt.setString(2, customer.getEmail());
			psmt.setString(3, customer.getPhone_num());
			psmt.setString(4, customer.getAddress());
			psmt.setString(5, customer.getId());
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnect();
		}
		return result;
	}
	
	//회원 탈퇴 
	public int deleteCustomer(String id) {
		String sql = " delete from customer "
				   + " where id = ? ";
		int result = 0; 
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnect();
		}
		return result;
	}
	
	//회원 탈퇴되면 주문 내역 지워지게 
	public int deleteBuyListById(String id) {
		String sql = " delete from buy_book "
				   + " where id = ? ";
		int result = 0; 
		try {
			connect();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnect();
		}
		return result;
	}
	
	
	//회원 탈퇴하면 그 회원이 장바구니에 담았던 데이터도 같이 삭제
	//장바구니 똑같은 책 중복으로 담겼을때 삭제하면 다 같이 지워지는 delete 메소드 (삭제 ㄴㄴ)
//	public int deleteDataInCart(String id) {
//		String sql = " delete from shop_bskt "
//				   + " where id = ? ";
//		int result = 0; 
//		try {
//			connect();
//			
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, id);
//			
//			
//			result = psmt.executeUpdate();
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			closeConnect();
//		}
//		return result;
//	}
//	public int deleteDataInCart(String id) {
//		String sql = " delete from shop_bskt "
//				   + " where id = ? ";
//		int result = 0; 
//		try {
//			connect();
//			
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, id);
//			
//			
//			result = psmt.executeUpdate();
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			closeConnect();
//		}
//		return result;
//	}
	public int insertBookInPurchase(Customer cs,String isbn,int price,String method,String title) { //구매 테이블에 테이터 넣기 지우지마세요

		   
		String sql = "insert into buy_book values(?,?,?,?,?,?,?,1,?,sysdate,?)";
				
		int result = 0;
		

		try {
			connect();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cs.getCustomer_no());
			psmt.setString(2, cs.getId());
			psmt.setString(3, cs.getEmail());
			psmt.setString(4, cs.getAddress());
			psmt.setString(5, isbn);
			psmt.setInt(6, price);
			psmt.setString(7, method);
			psmt.setString(8, "예약주문" );
			psmt.setString(9, title);
			
			result = psmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeConnect();
		}
		return result;
	}
	
	public int deleteDataInCart(String id, String isbn) {
	String sql = "delete from shop_bskt where isbn = ? and rownum =1 and id = ?";
	int result = 0; 
	try {
		connect();
		
		psmt = conn.prepareStatement(sql);
		psmt.setString(1, isbn);
		psmt.setString(2, id);
	
		
		
		
		result = psmt.executeUpdate();
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		closeConnect();
	}
	return result;
}
	
	
}
	
	
