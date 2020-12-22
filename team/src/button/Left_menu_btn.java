package button;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class Left_menu_btn extends JButton{
	
	
	public Left_menu_btn() {
		
		this.setFont(new Font("monospaced", Font.ITALIC | Font.BOLD, 15));
		this.setForeground(new Color(237,242,244));
		this.setOpaque(true);
		this.setBackground(new Color(80,55,120));
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		
	}
	
	public Left_menu_btn(String name) {
		super(name);
		this.setBounds(0, 250, 200, 50);
		this.setFont(new Font("monospaced", Font.ITALIC | Font.BOLD, 15));
		this.setForeground(new Color(237,242,244));
		this.setOpaque(true);
		this.setBackground(new Color(80,55,120));
		this.setFocusPainted(false);
		this.setBorderPainted(false);
	}
	
		


}
