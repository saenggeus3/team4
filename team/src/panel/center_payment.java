package panel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import team.Payment;
import team.Record;


public class center_payment extends JPanel{
	
	Payment pay = new Payment();
	StringBuilder list = new StringBuilder("");
	String serialnumbers;
	String taText = "";
	int[] arr;
	Map<String, Integer[]> shoes_list;
	
	JPanel left_panel = new JPanel();
	JTextArea ta = new JTextArea();
	int total = 0;
	JLabel total_view = new JLabel("결제금액 : 0");
	
	
	JPanel right_panel = new JPanel();
	static BufferedImage source = null;
	
	static {
		try {
			source = ImageIO.read(
					new File("img\\abc.png")
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	JLabel logo = new JLabel(new ImageIcon(source));
	JLabel input_text = new JLabel("제품 시리얼 번호 입력");
	JTextField input_serial = new JTextField("");
	JButton input_btn = new JButton("담기");
	JLabel remove_text = new JLabel("취소할 제품 시리얼 번호 입력");
	JTextField remove_serial = new JTextField("");
	JButton remove_btn = new JButton("빼기");
	JButton reset = new JButton("모두 지우기");
	JButton payment_btn = new JButton("비회원결제");
	JButton member_payment_btn = new JButton("회원카드결제");
	
	
	public center_payment() {
		
		
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1700,800));
		
		
		
		left_panel.setBounds(0, 0, 900, 800);
		left_panel.setPreferredSize(new Dimension(900,800));
		left_panel.setLayout(null);
		left_panel.setBackground(new Color(255,255,255));
		ta.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ta);
		scrollPane.setBounds(80,60,700,600);
		ta.setBackground(new Color(255,255,227));
		left_panel.add(scrollPane);
		left_panel.add(total_view);
		total_view.setBounds(610, 670, 200, 50);
		total_view.setFont(new Font("돋움", Font.BOLD, 17));
		
		
		
		
		
		
		
		right_panel.setPreferredSize(new Dimension(800,800));
		right_panel.setBounds(900,0,800,800);
		right_panel.setLayout(null);
		right_panel.add(logo);
		
		right_panel.add(input_text);
		input_text.setBounds(60, 210, 300, 30);
		
		right_panel.add(input_serial);
		input_serial.setBounds(60, 250, 300, 40);
		
		right_panel.add(input_btn);
		input_btn.setBounds(370, 250, 60, 40);
		input_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (!input_serial.getText().equals("")) {
					String tmp = new Record().pay_check(ta, input_serial.getText(), taText);
					if (tmp != null) {
						list.append(input_serial.getText()+",");
						taText = tmp;
						serialnumbers = list.toString();
						arr = new int[serialnumbers.split(",").length];
						for (int i = 0; i < serialnumbers.split(",").length; i++) {
							arr[i] = Integer.parseInt(serialnumbers.split(",")[i]);
						}
					}
					
					
					
					shoes_list = pay.product_list(arr);
					new Record().pay_view(ta, shoes_list);
					
					for(Entry<String, Integer[]> entry : shoes_list.entrySet()) {
						// 가격 수량 시리얼넘버 사이즈
						if (entry.getValue()[2] == Integer.parseInt(input_serial.getText())) {
							total += entry.getValue()[0];
						}
					
					
					
					}
					input_serial.setText("");
					total_view.setText("결제금액 : " + total);
				}
				
				
			}
		});
		
		
		right_panel.add(remove_text);
		remove_text.setBounds(60, 310, 300, 30);
		
		right_panel.add(remove_serial);
		remove_serial.setBounds(60, 350, 300, 40);
		
		right_panel.add(remove_btn);
		remove_btn.setBounds(370, 350, 60, 40);
		remove_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!remove_serial.getText().equals("")) {
					int serial = Integer.parseInt(remove_serial.getText());
					// 가격 수량 시리얼넘버 사이즈
					
					serialnumbers = serialnumbers.replaceFirst(serial+",", "");
					list.setLength(0);
					list.append(serialnumbers);
					arr = new int[serialnumbers.split(",").length];
					
					for (int i = 0; i < serialnumbers.split(",").length; i++) {
						if (serialnumbers.split(",")[i].equals("") && i == 0) {
							arr = new int[0];
						}else {
							arr[i] = Integer.parseInt(serialnumbers.split(",")[i]);
						}
						
					}
					
					for(Entry<String, Integer[]> entry : shoes_list.entrySet()) {
						// 가격 수량 시리얼넘버 사이즈
						if(Integer.parseInt(remove_serial.getText()) == entry.getValue()[2]) {
							total -= entry.getValue()[0];
						}
						
					}
					
					remove_serial.setText("");
					ta.setText("");
					shoes_list = pay.product_list(arr);
					new Record().pay_view(ta, shoes_list);
					
					
					
					
					total_view.setText("결제금액 : " + total);
				}
				
				
			}
		});
		
		
		right_panel.add(reset);
		reset.setBounds(45, 430, 400, 40);
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				list.setLength(0);
				arr = null;
				shoes_list = null;
				total = 0;
				serialnumbers = "";
				ta.setText("");
				total_view.setText("결제금액 : " + total);
			}
		});
		
		
		
		logo.setBounds(0, 30, 500, 100);
		
		right_panel.add(member_payment_btn);
		member_payment_btn.setBounds(35, 500, 200, 200);
		member_payment_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (shoes_list != null) {
					String resultStr = null;
					resultStr = JOptionPane.showInputDialog("회원카드번호입력");
					
					if (pay.MemberPay(shoes_list, resultStr) != -1) {
						JOptionPane.showMessageDialog(null, "결제완료");
						ta.setText("");
						total_view.setText("");
					}else {
						JOptionPane.showMessageDialog(null, "결제에 실패했습니다.");
					}
				}

				
				
				
				
			}
		});
		
		right_panel.add(payment_btn);
		payment_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (shoes_list != null) {
					if (pay.pay(shoes_list) != -1) {
						JOptionPane.showMessageDialog(null, "결제완료");
						ta.setText("");
						total_view.setText("");
					}else {
						JOptionPane.showMessageDialog(null, "결제에 실패했습니다.");
					}
				}
				
				
				
			}
		});
		
		payment_btn.setBounds(255, 500, 200, 200);
		
		
		
		add(left_panel);
		add(right_panel);
		
		
		
	}

}
