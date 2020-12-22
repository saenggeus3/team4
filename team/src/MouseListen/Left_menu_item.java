package MouseListen;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Left_menu_item implements MouseListener{
	
	JButton item;
	Color c;
	
	public Left_menu_item(JButton button) {
		item = button;
		c = item.getBackground();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		item.setBackground(new Color(100,83,148));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		item.setBackground(c);
		
	}

	
	
}
