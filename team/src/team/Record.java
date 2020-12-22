package team;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextArea;

import com.zaxxer.hikari.HikariDataSource;

public class Record {
	
	HikariDataSource ds = null;
	Connection conn;
	PreparedStatement pstmt;
	
	ResultSet rs;
	
	Calendar cal = new GregorianCalendar();
	Date date = new Date(cal.getTimeInMillis());
	String today = date.toString().replaceAll("-", "");
	
	public Record() {
		ds = new Hikariconfig().config();
	}
	
	public Record(String today) {
		ds = new Hikariconfig().config();
		this.today = today;
	}
	
	public void view(JTextArea ta) {
		int total = 0;
		
		try {
			ta.setText("  판매번호\t시리얼넘버\t신발이름\t\t사이즈\t판매개수\t가격\n\n");
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM product_record "
							+ "WHERE TO_CHAR(sales_time,'YYYYMMDD') = " + today
							+ " ORDER BY sales_number DESC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String tmp = "";
				if (rs.getString("sh_name").length()<10) {
					tmp = rs.getInt("sales_number") 
							+ "\t" + rs.getInt("shoes_serial_number")
							+ "\t" + rs.getString("sh_name")
							+ "\t\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_info")
							+ "\t" + rs.getInt("sh_price")+"\n";
				}else {
					tmp = rs.getInt("sales_number") 
							+ "\t" + rs.getInt("shoes_serial_number")
							+ "\t" + rs.getString("sh_name")
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_info")
							+ "\t" + rs.getInt("sh_price")+"\n";
				}
				ta.append("  " + tmp);
				total+=rs.getInt("sh_price");
				
			}
			ta.append("\n\n\t\t\t\t\t총판매액\t" + NumberFormat.getInstance().format(total) + "￦");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)rs.close();
					if(pstmt != null)pstmt.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
	}
	
	public void Month_view(JTextArea ta) {
		int total = 0;
		try {
			ta.setText("  판매번호\t시리얼넘버\t신발이름\t\t사이즈\t판매개수\t가격\n\n");
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM product_record "
							+ "WHERE TO_CHAR(sales_time,'YYYYMM') = " + today.substring(0, 6)
							+ " ORDER BY sales_number DESC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String tmp = "";
				if (rs.getString("sh_name").length()<10) {
					tmp = rs.getInt("sales_number") 
							+ "\t" + rs.getInt("shoes_serial_number")
							+ "\t" + rs.getString("sh_name")
							+ "\t\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_info")
							+ "\t" + rs.getInt("sh_price")+"\n";
				}else {
					tmp = rs.getInt("sales_number") 
							+ "\t" + rs.getInt("shoes_serial_number")
							+ "\t" + rs.getString("sh_name")
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_info")
							+ "\t" + rs.getInt("sh_price")+"\n";
				}
				ta.append("  " + tmp);
				total+=rs.getInt("sh_price");
				
			}
			ta.append("\n\n\t\t\t\t\t총판매액\t" + NumberFormat.getInstance().format(total) + "￦");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)rs.close();
					if(pstmt != null)pstmt.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
	}
	
	
	public void seach_sales_number(JTextArea ta, int number) {
		int total = 0;
		try {
			ta.setText("  판매번호\t시리얼넘버\t신발이름\t\t사이즈\t판매개수\t가격\n\n");
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM product_record "
							+ "WHERE sales_number = " + number
							+ " ORDER BY sales_number DESC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String tmp = "";
				if (rs.getString("sh_name").length()<10) {
					tmp = rs.getInt("sales_number") 
							+ "\t" + rs.getInt("shoes_serial_number")
							+ "\t" + rs.getString("sh_name")
							+ "\t\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_info")
							+ "\t" + rs.getInt("sh_price")+"\n";
				}else {
					tmp = rs.getInt("sales_number") 
							+ "\t" + rs.getInt("shoes_serial_number")
							+ "\t" + rs.getString("sh_name")
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_info")
							+ "\t" + rs.getInt("sh_price")+"\n";
				}
				ta.append("  " + tmp);
				total+=rs.getInt("sh_price");
				
			}
			ta.append("\n\n\t\t\t\t\t총판매액\t" + NumberFormat.getInstance().format(total) + "￦");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)rs.close();
					if(pstmt != null)pstmt.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
	}
	
	public void seach_member(JTextArea ta, String member) {
		int total = 0;
		try {
			ta.setText("  판매번호\t신발이름\t\t판매개수\t사이즈\t가격\t날짜\t포인트\n\n");
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM " + member
							+ " ORDER BY sh_id DESC");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String tmp = "";
				if (rs.getString("sh_name").length()<10) {
					tmp = rs.getInt("sales_number")
							+ "\t" + rs.getString("sh_name") 
							+ "\t\t" + rs.getInt("sh_count")
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_price")
							+ "\t" + rs.getDate("purchase_day")
							+ "\t" + rs.getInt("current_point")+"\n";
				}else {
					tmp = rs.getInt("sales_number")
							+ "\t" +rs.getString("sh_name") 
							+ "\t" + rs.getInt("sh_count")
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_price")
							+ "\t" + rs.getDate("purchase_day")
							+ "\t" + rs.getInt("current_point")+"\n";
				}
				ta.append("  " + tmp);
				total+=rs.getInt("sh_price");
				
			}
			ta.append("\n\n\t\t\t\t\t총판매액\t" + NumberFormat.getInstance().format(total) + "￦");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)rs.close();
					if(pstmt != null)pstmt.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
	}
	
	
	
	public String pay_check(JTextArea ta, String serial, String log) {
		try {
			
			conn = ds.getConnection();
			pstmt = 
					conn.prepareStatement(
							"SELECT * FROM shoes WHERE shoes_serial_number = ?");
			int ser = Integer.parseInt(serial);
			pstmt.setInt(1, ser);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String tmp = "";
//				if (rs.getString("sh_name").length()<10) {
//					tmp ="\t" +rs.getString("sh_name")
//							+ "\t\t\t" + rs.getInt("sh_size")
//							+ "\t\t" + rs.getInt("sh_price")+"\n";
//				}else {
//					tmp = "\t" +rs.getString("sh_name")
//							+ "\t\t" + rs.getInt("sh_size")
//							+ "\t\t" + rs.getInt("sh_price")+"\n";
//				}
//				ta.append(log + "  " + tmp);
				return log + "   " + tmp;
			}else {
//				ta.append(log);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)rs.close();
					if(pstmt != null)pstmt.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return null;
		
	}
	
	
	
	public int pay_view(JTextArea ta, Map<String, Integer[]> shoes_list) {
		StringBuilder taText = new StringBuilder();
		String tmp;
		ta.setText("\n\n");
		try {
			conn = ds.getConnection();
			// 가격 수량 시리얼넘버 사이즈
			for (Entry<String, Integer[]> entry : shoes_list.entrySet()) {
				pstmt = 
						conn.prepareStatement("SELECT * FROM shoes WHERE shoes_serial_number = ?");
				pstmt.setInt(1, entry.getValue()[2]);
				rs = pstmt.executeQuery();
				rs.next();
				if (rs.getString("sh_name").length()<10) {
					tmp = "\t" + rs.getString("sh_name") 
							+ "\t\t\t" + entry.getValue()[1]
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_price") + "\n";
				}else {
					tmp = "\t" + rs.getString("sh_name") 
							+ "\t\t" + entry.getValue()[1]
							+ "\t" + rs.getInt("sh_size")
							+ "\t" + rs.getInt("sh_price") + "\n";
				}
				taText.append(tmp);
				
			}
			
			ta.append(taText.toString());
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return -1;
	}
	
	
	
	

}
