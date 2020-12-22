package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.zaxxer.hikari.HikariDataSource;

import team.Hikariconfig;
import team.Payment;
import team.Record;
import tmp.Cal;

public class Record_panel extends JPanel{
	
	
	
	JPanel record = new JPanel();
	JTextArea ta;
	Calendar cal = new GregorianCalendar();
	Date date = new Date(cal.getTimeInMillis());
	String today = date.toString();
	JLabel title = new JLabel(String.format("%s�� %s�� %s��", today.split("-")[0],today.split("-")[1],today.split("-")[2]));
	
	
	JPanel rightmenu = new JPanel();
	JPanel calpane = new JPanel();
	
	JLabel text1 = new JLabel("Ȯ���� ��¥ Ŭ��!");
	JButton button1 = new JButton("�� ������ ����");
	
	boolean input1_check = false;
	JLabel input1_title = new JLabel("�ǸŹ�ȣ�˻�");
	JTextField input1 = new JTextField("�ǸŹ�ȣ�Է�");
	JButton input1_btn = new JButton("ã��");
	
	boolean input2_check = false;
	JLabel input2_title = new JLabel("ȸ��ī��˻�");
	JTextField input2 = new JTextField("ȸ����ȣ�Է�");
	JButton input2_btn = new JButton("ã��");

	JLabel refund_title = new JLabel("ȯ��");
	boolean member_check = false;
	JTextField refund_member = new JTextField("ī���ȣ�Է�");
	JLabel refund_member_text = new JLabel("ȸ��ī��ΰ����� ��ǰ�̶�� �ݵ�� ī���ȣ�� �Է����ּ���!");
	boolean sal_check = false;
	JTextField refund_sal_num = new JTextField("�ǸŹ�ȣ�Է�");
	boolean serial_check = false;
	JTextField refund_serial = new JTextField("�ø����ȣ�Է�");
	boolean info_check = false;
	JTextField refund_info = new JTextField("�����Է�");
	JButton refund_btn = new JButton("Ȯ��");
	
	public Record_panel() {
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1700,800));
		
		
		title.setBounds(50, 50, 500, 50);
		record.add(title);
		
		ta = new JTextArea(50,50);
		
		record.setPreferredSize(new Dimension(1000,800));
		record.setLayout(null);
		record.setBounds(0, 0, 900, 800);
		ta.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ta);
		scrollPane.setBounds(50,100,700,600);
		record.add(scrollPane);
		new team.Record(today.replaceAll("-", "")).view(ta);
		
		
		
		
		
		rightmenu.setPreferredSize(new Dimension(700,800));
		rightmenu.setLayout(null);
		rightmenu.setBounds(900, 0, 700, 800);
		rightmenu.setBackground(new Color(237,242,244));
		rightmenu.add(text1);
		text1.setBounds(60, 10, 400, 50);
		rightmenu.add(calpane);
		calpane.setBounds(30, 70, 400, 250);
		calpane.setBackground(new Color(237,242,244));
		calpane.setLayout(new FlowLayout());
		rightmenu.add(calpane);
		Cal cal = new Cal(today, ta, title);
		calpane.add(cal);
		cal.setBounds(50, 100, 600, 200);
		rightmenu.add(button1);
		button1.setBounds(60, 350, 175, 30);
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new team.Record(String.format("%d%d", cal.yy,cal.mm+1)).Month_view(ta);
				title.setText(String.format("%d�� %d��", cal.yy,cal.mm+1));
				
			}
		});
		
		rightmenu.add(input1_title);
		input1_title.setBounds(60, 420, 170, 30);
		rightmenu.add(input1);
		input1.setBounds(60, 450, 175, 30);
		
		MouseAdapter textlistener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!input1_check) {
					input1.setText("");
					input1_check = !input1_check;
				}
				
			}
		};
		
		input1.addMouseListener(textlistener);
		rightmenu.add(input1_btn);
		input1_btn.setBounds(250, 450, 70, 30);
		input1_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (input1_check && !input1.getText().equals("")) {
					new team.Record().seach_sales_number(ta, Integer.parseInt(input1.getText()));
					input1.setText("�ǸŹ�ȣ�Է�");
					input1_check = !input1_check;
				}else if(input1_check) {
					input1.setText("�ǸŹ�ȣ�Է�");
					input1_check = !input1_check;
				}
				
			}
		});
		
		
		
		rightmenu.add(input2_title);
		input2_title.setBounds(60, 500, 170, 30);
		rightmenu.add(input2);
		input2.setBounds(60, 530, 175, 30);
		
		MouseAdapter memberlistener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!input2_check) {
					input2.setText("");
					input2_check = !input2_check;
				}
				
			}
		};
		
		input2.addMouseListener(memberlistener);
		rightmenu.add(input2_btn);
		input2_btn.setBounds(250, 530, 70, 30);
		input2_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (input2_check && !input2.getText().equals("")) {
					title.setText(input2.getText());
					new team.Record().seach_member(ta, input2.getText());
					input2.setText("ȸ����ȣ�Է�");
					input2_check = !input2_check;
				}else if(input2_check) {
					input2.setText("ȸ����ȣ�Է�");
					input2_check = !input2_check;
				}
				
			}
		});
		
		
		
		
		rightmenu.add(refund_title);
		refund_title.setBounds(60, 580, 170, 30);
		
		rightmenu.add(refund_member);
		refund_member.setBounds(60, 640, 100, 30);
		refund_member.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!member_check) {
					refund_member.setText("");
					member_check = !member_check;
				}
				
			}
		});
		refund_member_text.setFont(new Font("����",Font.ITALIC, 11));
		rightmenu.add(refund_member_text);
		refund_member_text.setBounds(60, 610, 350, 30);
		
		
		rightmenu.add(refund_sal_num);
		refund_sal_num.setBounds(60, 680, 100, 30);
		refund_sal_num.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!sal_check) {
					refund_sal_num.setText("");
					sal_check = !sal_check;
				}
				
			}
		});
		rightmenu.add(refund_serial);
		refund_serial.setBounds(170, 680, 100, 30);
		refund_serial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!serial_check) {
					refund_serial.setText("");
					serial_check = !serial_check;
				}
				
			}
		});
		rightmenu.add(refund_info);
		refund_info.setBounds(280, 680, 60, 30);
		refund_info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!info_check) {
					refund_info.setText("");
					info_check = !info_check;
				}
				
			}
		});
		rightmenu.add(refund_btn);
		refund_btn.setBounds(350, 680, 60, 30);
		refund_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = -1;
				if (!refund_sal_num.getText().equals("") && sal_check &&
						!refund_serial.getText().equals("") && serial_check &&
						!refund_info.getText().equals("") && info_check) {
					if ((member_check && refund_member.getText().equals("")) ||
							!member_check) {
						check = new Payment().refund(refund_sal_num.getText(), refund_serial.getText(), refund_info.getText());
					}else {
						check = new Payment().Member_refund(refund_member.getText(),refund_sal_num.getText(), refund_serial.getText(), refund_info.getText());
					}
					
				}
				

				if(check != -1 && check != -2 && check != -3) {
					JOptionPane.showMessageDialog(null, "ȯ�ҿϷ�");
					new team.Record(today.replaceAll("-", "")).view(ta);
				}else if(check == -2) {
					JOptionPane.showMessageDialog(null, "ȸ��ī���ȣ�� �Է����ּ���");
				}else if(check == -3){
					JOptionPane.showMessageDialog(null, "ȸ��ī��� ������ ��ǰ�� �ƴմϴ�");
				}else {
					JOptionPane.showMessageDialog(null, "ȯ�ҽ���");
				}
				
				refund_sal_num.setText("�ǸŹ�ȣ�Է�");
				if(sal_check) sal_check = !sal_check;
				refund_serial.setText("�ø����ȣ�Է�");
				if(serial_check) serial_check = !serial_check;
				refund_info.setText("�����Է�");
				if(info_check) info_check = !info_check;
				refund_member.setText("ī���ȣ�Է�");
				if(member_check) member_check = !member_check; 
				
				
				
			}
		});
		
		
		
		
		
		
		
		this.add(record);
		this.add(rightmenu);
		
	}

	
	
}
