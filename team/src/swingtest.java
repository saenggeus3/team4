import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingContainer;

import MouseListen.Left_menu_item;
import button.Left_menu_btn;
import panel.Record_panel;
import panel.center_payment;



public class swingtest extends JFrame{
	
	
	
	JMenuBar bar = new JMenuBar();
	JMenu menu = new JMenu();
	JPanel center = new JPanel();	// 우측메인패널
	JPanel left = new JPanel();	// 좌측메뉴패널
	JPanel center_top = new JPanel();	// 메인상단패널
	JPanel center_main = new JPanel();	// 메인내부패널
	JPanel center_center = new JPanel();	// 메인내부에 들어갈 기본패널
	JPanel center_left = new JPanel();	// 메인 내부에 들어갈 기본패널위에있는 좌측패널
	JPanel center_right = new JPanel();	// 메인 내부에 들어갈 기본패널위에있는 우측패널
	JLabel left_menu = new JLabel("Menu", SwingConstants.CENTER);
	JPanel center_my = new Record_panel();
	JPanel center_payment = new center_payment();
	
	JButton left_menu_item1 = null;
	JButton left_menu_item2 = null;
	JButton left_menu_item3 = null;
	JButton left_menu_item4 = null;
	JButton left_menu_item5 = null;
	
	
	public swingtest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(150, 70);
		setSize(1600, 900);
		setLayout(new BorderLayout());
		setVisible(true);
		
		
		center.setLayout(new BorderLayout());
		
		left.setBackground(new Color(54,33,89));
		left.setPreferredSize(new Dimension(200,100));
		left.setLayout(null);
		
		
		this.add(left,BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		
		center_top.setBackground(new Color(43,45,66));
		center_top.setPreferredSize(new Dimension(100,100));
		center.add(center_top,BorderLayout.NORTH);
		
		CardLayout cards = new CardLayout();
		center_main.setPreferredSize(new Dimension(1700,800));
		center_main.setLayout(cards);
		
		
		
		
		center_center.setLayout(new BorderLayout());
		
		center_left.setBackground(new Color(217,222,224));
		center_left.setPreferredSize(new Dimension(1200,800));
		
		center_right.setBackground(new Color(237,242,244));
		center_right.setPreferredSize(new Dimension(500,800));
		center.add(center_main,BorderLayout.CENTER);
		
		center_main.add(center_center, "main");
		center_main.add(center_my, "my");
		center_main.add(center_payment, "payment");
		
		center_center.add(center_left, BorderLayout.CENTER);
		center_center.add(center_right, BorderLayout.EAST);
		
		
		left_menu.setBounds(0, 0, 100, 100);
		left_menu.setFont(new Font("굴림체", Font.ITALIC, 25));
		left_menu.setForeground(new Color(237,242,244));
		left.add(left_menu);
		
		
		left_menu_item1 = new Left_menu_btn("item1");
		left_menu_item1.setBounds(0, 100, 200, 50);
		left_menu_item1.setBackground(new Color(64,43,100));
		left_menu_item1.addMouseListener(new Left_menu_item(left_menu_item1));
		left.add(left_menu_item1);
		
		left_menu_item2 = new Left_menu_btn("item2");
		left_menu_item2.setBounds(0, 150, 200, 50);
		left_menu_item2.addMouseListener(new Left_menu_item(left_menu_item2));
		left.add(left_menu_item2);
		
		left_menu_item3 = new Left_menu_btn("결제");
		left_menu_item3.setBounds(0, 200, 200, 50);
		left_menu_item3.setBackground(new Color(64,43,100));
		left_menu_item3.addMouseListener(new Left_menu_item(left_menu_item3));
		left_menu_item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(center_main, "payment");
				
			}
		});
		left.add(left_menu_item3);
		
		
		left_menu_item4 = new Left_menu_btn("판매기록 & 환불");
		left_menu_item4.setBounds(0, 250, 200, 50);
		left_menu_item4.addMouseListener(new Left_menu_item(left_menu_item4));
		
		left_menu_item4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(center_main,"my");
				
			}
		});
		left.add(left_menu_item4);
		
		left_menu_item5 = new Left_menu_btn("메인으로");
		left_menu_item5.setBounds(0, 810, 200, 50);
		left_menu_item5.addMouseListener(new Left_menu_item(left_menu_item5));
		left_menu_item5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(center_main,"main");
				
			}
		});
		left.add(left_menu_item5);
		
		setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) {
		
		new swingtest();
		
	}
	
	

}
