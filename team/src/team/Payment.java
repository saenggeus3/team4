package team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.zaxxer.hikari.HikariDataSource;

public class Payment {
	HikariDataSource ds = null;
	int point = 0;
	int sh_id = 0;
	double saving_rate = 0.02;

	public Payment() {
		ds = new Hikariconfig().config();
	}
	
	/**
	 * 구매목록을 map 으로 담는 메서드
	 */
	public Map<String, Integer[]> product_list(int[] list){
		
		Map<String, Integer[]> shose_list = new HashMap<>();
		
		try {
			Connection conn = ds.getConnection();
			
			PreparedStatement pstmt = 
					conn.prepareStatement("SELECT * FROM shoes WHERE shoes_serial_number = ?");
			ResultSet rs = null;
			
			for (int i = 0; i < list.length; i++) {
				
				pstmt.setInt(1, list[i]);
				rs = pstmt.executeQuery();
				Integer[] info = {0, 1, list[i], 0}; // 가격 수량 시리얼넘버 사이즈
				while(rs.next()) {
					String sh_name = rs.getString("sh_name") +" - "+ rs.getInt("sh_size");
					if (shose_list.containsKey(sh_name)) {
						Integer[] tmp = shose_list.get(sh_name);
						tmp[1]++;
						shose_list.put(sh_name, tmp);
					}else {
						info[0] = rs.getInt("sh_price");
						info[3] = rs.getInt("sh_size");
						shose_list.put(sh_name, info);
					}
					
				}
			}
			
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(conn != null)conn.close();

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return shose_list;
		
	}
	
	

	
	
	/**
	 * 물건을 판매하는 메서드
	 */
	public int pay(Map<String, Integer[]> productlist) {
		int check = -1;
		try {
			Connection conn = ds.getConnection();
			
			PreparedStatement pstmt = 
					conn.prepareStatement("UPDATE shoes SET st_info = st_info-?, st_sales = st_sales+? WHERE shoes_serial_number LIKE ?");
			ResultSet rs = null;
			
			for (Entry<String, Integer[]> entry : productlist.entrySet()) {
				pstmt.setInt(1, entry.getValue()[1]);
				pstmt.setInt(2, entry.getValue()[1]);
				pstmt.setInt(3, entry.getValue()[2]);
				rs = pstmt.executeQuery();
			}
			
			pstmt = 
					conn.prepareStatement("SELECT sales_number.nextval FROM ssn");
			rs = pstmt.executeQuery();
			
			String sales_number = "sales_number.currval";
			
			pstmt = 
					conn.prepareStatement("INSERT INTO product_record VALUES ("
							+ sales_number+", " // 시퀀스번호
							+ "?, "	// 시리얼넘버
							+ "?, "	// 제품이름
							+ "?, " // 제품사이즈
							+ "?, " // 제품수량
							+ "?, "	// 제품가격
							+ "sysdate,"
							+ "?)"); // 회원카드결제확인
			
			
			for (Entry<String, Integer[]> entry : productlist.entrySet()) {
				
				String shose_name = entry.getKey().split(" - ")[0];
				
				pstmt.setInt(1, entry.getValue()[2]);
				pstmt.setString(2, shose_name);
				pstmt.setInt(3, entry.getValue()[3]);
				pstmt.setInt(4, entry.getValue()[1]);
				pstmt.setInt(5, entry.getValue()[0] * entry.getValue()[1]);
				pstmt.setString(6, "x");
				rs = pstmt.executeQuery();
			}
			
			pstmt = conn.prepareStatement("SELECT sales_number FROM (SELECT * FROM product_record ORDER BY sales_number DESC) WHERE rownum = 1");
			rs = pstmt.executeQuery();
			rs.next();
			check = rs.getInt("SALES_NUMBER");
			
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(conn != null)conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	/**
	 * 회원에게 물건을 판매하는 메서드
	 */
	
	public int MemberPay(Map<String, Integer[]> productlist, String cardnumber) {
		int check = -1;
		try {
			Connection conn = ds.getConnection();
			
			PreparedStatement pstmt = 
					conn.prepareStatement("UPDATE shoes SET st_info = st_info-?, st_sales = st_sales+? WHERE shoes_serial_number = ?");
			ResultSet rs = null;
			
			for (Entry<String, Integer[]> entry : productlist.entrySet()) {
				pstmt.setInt(1, entry.getValue()[1]);
				pstmt.setInt(2, entry.getValue()[1]);
				pstmt.setInt(3, entry.getValue()[2]);
				rs = pstmt.executeQuery();
			}
			
			pstmt = 
					conn.prepareStatement("SELECT sales_number.nextval FROM ssn");
			rs = pstmt.executeQuery();
			
			String sales_number = "sales_number.currval";
			
			pstmt = 
					conn.prepareStatement("INSERT INTO product_record VALUES ("
							+ sales_number+", " // 시퀀스번호
							+ "?, "	// 시리얼넘버
							+ "?, "	// 제품이름
							+ "?, " // 제품사이즈
							+ "?, " // 제품수량
							+ "?, "	// 제품가격
							+ "sysdate,"
							+ "?)"); // 회원카드결제확인
			
			
			
			for (Entry<String, Integer[]> entry : productlist.entrySet()) {
				String shose_name = entry.getKey().split(" - ")[0];
				pstmt.setInt(1, entry.getValue()[2]);
				pstmt.setString(2, shose_name);
				pstmt.setInt(3, entry.getValue()[3]);
				pstmt.setInt(4, entry.getValue()[1]);
				pstmt.setInt(5, entry.getValue()[0] * entry.getValue()[1]);
				pstmt.setString(6, "o");
				point = (int) (Math.floor(entry.getValue()[0] * entry.getValue()[1])*0.02);
				rs = pstmt.executeQuery();
			}
			
			pstmt = conn.prepareStatement("SELECT * "
					+ "FROM (SELECT * FROM " + cardnumber + " ORDER BY sh_id DESC)"
					+ "WHERE ROWNUM = 1");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				point += rs.getInt("CURRENT_POINT");
				sh_id += rs.getInt("SH_ID");
			}else {
				point = 0;
				sh_id = 0;
			}
			
			pstmt = 
					conn.prepareStatement("INSERT INTO " + cardnumber + " VALUES ("
							+ sales_number+", " // 판매번호
							+ "?, "	// 신발이름
							+ "?, " // 신발수량
							+ "?, "	// 신발사이즈
							+ "?, " // 신발가격
							+ "sysdate, " // 날짜
							+ "?,"	// 포인트
							+ "?, " // 시리얼넘버
							+ "?)"	// sh_id
							);
			
			
			
			for (Entry<String, Integer[]> entry : productlist.entrySet()) {
				point += (entry.getValue()[0] * entry.getValue()[1]) * saving_rate;
				sh_id += 1;
				String shose_name = entry.getKey().split(" - ")[0];
				pstmt.setString(1, shose_name);
				pstmt.setInt(2, entry.getValue()[1]);
				pstmt.setInt(3, entry.getValue()[3]);
				pstmt.setInt(4, entry.getValue()[0] * entry.getValue()[1]);
				pstmt.setInt(5, point);
				pstmt.setInt(6, entry.getValue()[2]);
				pstmt.setInt(7, sh_id);
				rs = pstmt.executeQuery();
			}
			
			pstmt = conn.prepareStatement("SELECT sales_number FROM (SELECT * FROM product_record ORDER BY sales_number DESC) WHERE rownum = 1");
			rs = pstmt.executeQuery();
			rs.next();
			check = rs.getInt("SALES_NUMBER");
			
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(conn != null)conn.close();
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return check;
		
	}
	
	/***
	 * 환불메서드
	 */
	
	public int refund(String sal_num, String serial, String info) {
		int result = -1;
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM product_record "
							+ "WHERE sales_number = ? AND "
							+ "shoes_serial_number = ? AND "
							+ "sh_info >= ?");
			
			
			pstmt.setInt(1, Integer.parseInt(sal_num));
			pstmt.setInt(2, Integer.parseInt(serial));
			pstmt.setInt(3, Integer.parseInt(info));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("member_check").equals("o")) {
					result = -2;
					return result;
				}
				if(rs.getInt("sh_info")-Integer.parseInt(info)<=0) {
					pstmt = conn.prepareStatement(
							"DELETE FROM product_record "
							+ "WHERE sales_number = ? AND "
							+ "shoes_serial_number = ? ");
					pstmt.setInt(1, rs.getInt("sales_number"));
					pstmt.setInt(2, rs.getInt("shoes_serial_number"));
					ResultSet rs2 = pstmt.executeQuery();
					rs2.close();
				}else {
					pstmt = 
							conn.prepareStatement("UPDATE product_record "
									+ "set sh_price = sh_price - (sh_price / sh_info * ?), sh_info = sh_info-? "
									+ "WHERE sales_number = ? AND "
									+ "shoes_serial_number = ?" );
					pstmt.setInt(1, Integer.parseInt(info));
					pstmt.setInt(2, Integer.parseInt(info));
					pstmt.setInt(3, rs.getInt("sales_number"));
					pstmt.setInt(4, rs.getInt("shoes_serial_number"));
					ResultSet rs2 = pstmt.executeQuery();
					rs2.close();
				}
				
			}
			
			pstmt = 
					conn.prepareStatement("UPDATE shoes SET st_info = st_info+?, st_sales = st_sales-? WHERE shoes_serial_number = ?");
			pstmt.setInt(1, rs.getInt("sh_info"));
			pstmt.setInt(2, rs.getInt("sh_info"));
			pstmt.setInt(3, rs.getInt("shoes_serial_number"));
			ResultSet rs3 = pstmt.executeQuery();
			rs3.close();
			int tmp = rs.getInt("shoes_serial_number");
			result = tmp;
			
			
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(conn != null)conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	/**
	 * 
	 * 회원카드로결제한 제품 환불
	 */

	public int Member_refund(String member, String sal_num, String serial, String info) {
		int result = -1;
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM product_record "
							+ "WHERE sales_number = ? AND "
							+ "shoes_serial_number = ? AND "
							+ "sh_info >= ?");
			
			
			pstmt.setInt(1, Integer.parseInt(sal_num));
			pstmt.setInt(2, Integer.parseInt(serial));
			pstmt.setInt(3, Integer.parseInt(info));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("member_check").equals("x")) {
					result = -3;
					return result;
				}
				if(rs.getInt("sh_info")-Integer.parseInt(info)<=0) {
					pstmt = conn.prepareStatement(
							"DELETE FROM product_record "
							+ "WHERE sales_number = ? AND "
							+ "shoes_serial_number = ? ");
					pstmt.setInt(1, rs.getInt("sales_number"));
					pstmt.setInt(2, rs.getInt("shoes_serial_number"));
					ResultSet rs2 = pstmt.executeQuery();
					rs2.close();
				}else {
					pstmt = 
							conn.prepareStatement("UPDATE product_record "
									+ "set sh_price = sh_price - (sh_price / sh_info * ?), sh_info = sh_info-? "
									+ "WHERE sales_number = ? AND "
									+ "shoes_serial_number = ?" );
					pstmt.setInt(1, Integer.parseInt(info));
					pstmt.setInt(2, Integer.parseInt(info));
					pstmt.setInt(3, rs.getInt("sales_number"));
					pstmt.setInt(4, rs.getInt("shoes_serial_number"));
					ResultSet rs2 = pstmt.executeQuery();
					rs2.close();
				}
			}
				
			// 회원카드정보삭제
			
			pstmt = 
					conn.prepareStatement("SELECT * FROM "+ member + " WHERE sales_number = ? AND shoes_serial_number = ?");
			int mpoint;
			
			pstmt.setInt(1, Integer.parseInt(sal_num));
			pstmt.setInt(2, Integer.parseInt(serial));
			ResultSet rs5 = pstmt.executeQuery();
				
			if(rs5.next()) {
				mpoint = (int) Math.floor((rs5.getInt("sh_price") / rs5.getInt("sh_count")) *0.02 * Integer.parseInt(info));
				System.out.println(mpoint);
				if(rs5.getInt("sh_count")-Integer.parseInt(info)<=0) {
					pstmt = conn.prepareStatement(
							"DELETE FROM "+ member
							+ " WHERE sales_number = ? AND "
							+ "shoes_serial_number = ? ");
					pstmt.setInt(1, rs5.getInt("sales_number"));
					pstmt.setInt(2, rs5.getInt("shoes_serial_number"));
					ResultSet rs2 = pstmt.executeQuery();
					rs2.close();
				}else {
					pstmt = 
							conn.prepareStatement("UPDATE "+ member
									+ " set sh_price = sh_price - (sh_price / sh_count * ?), sh_count = sh_count-? "
									+ "WHERE sales_number = ? AND "
									+ "shoes_serial_number = ?" );
					pstmt.setInt(1, Integer.parseInt(info));
					pstmt.setInt(2, Integer.parseInt(info));
					pstmt.setInt(3, rs5.getInt("sales_number"));
					pstmt.setInt(4, rs5.getInt("shoes_serial_number"));
					ResultSet rs2 = pstmt.executeQuery();
					rs2.close();
				}
			
			pstmt = 
					conn.prepareStatement("UPDATE " + member
							+" set current_point = current_point - " + mpoint
							+" WHERE sh_id >= " + rs5.getInt("sh_id"));
			ResultSet rs2 = pstmt.executeQuery();
			rs2.close();
			rs5.close();
		}
			
				
				
			pstmt = 
					conn.prepareStatement("UPDATE shoes SET st_info = st_info+?, st_sales = st_sales-? WHERE shoes_serial_number = ?");
			pstmt.setInt(1, rs.getInt("sh_info"));
			pstmt.setInt(2, rs.getInt("sh_info"));
			pstmt.setInt(3, rs.getInt("shoes_serial_number"));
			ResultSet rs3 = pstmt.executeQuery();
			
			int tmp = rs.getInt("shoes_serial_number");
			result = tmp;
			rs3.close();
			
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(conn != null)conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
