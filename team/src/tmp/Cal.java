package tmp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import button.Cal_btn;

public class Cal extends JPanel {
	  /** The currently-interesting year (not modulo 1900!) */
	  public int yy;

	  /** Currently-interesting month and day */
	  public int mm, dd;

	  /** The buttons to be displayed */
	  protected JButton labs[][];

	  /** The number of day squares to leave blank at the start of this month */
	  protected int leadGap = 0;

	  /** A Calendar object used throughout */
	  Calendar calendar = new GregorianCalendar();

	  /** Today's year */
	  protected final int thisYear = calendar.get(Calendar.YEAR);

	  /** Today's month */
	  protected final int thisMonth = calendar.get(Calendar.MONTH);

	  /** One of the buttons. We just keep its reference for getBackground(). */
	  private JButton b0;

	  /** The month choice */
	  private JComboBox monthChoice;

	  /** The year choice */
	  private JComboBox yearChoice;
	  
	  String today;
	  JTextArea ta;
	  JLabel jl;

	  /**
	   * Construct a Cal, starting with today.
	   */
	  public Cal() {
	    super();
	    setYYMMDD(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
	        calendar.get(Calendar.DAY_OF_MONTH));
	    
	    buildGUI();
	    recompute();
	    
	  }
	  
	  public Cal(String today, JTextArea ta, JLabel jl) {
		  
		  super();
		  this.ta = ta;
		  this.today = today;
		  this.jl = jl;
		  //this.setPreferredSize(new Dimension(650,200));
		   setYYMMDD(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		       calendar.get(Calendar.DAY_OF_MONTH));
		    
		    buildGUI();
		    recompute();
	  }

	  /**
	   * Construct a Cal, given the leading days and the total days
	   * 
	   * @exception IllegalArgumentException
	   *                If year out of range
	   */
	  public Cal(int year, int month, int today) {
	    super();
	    setYYMMDD(year, month, today);
	    
	    buildGUI();
	    recompute();
	    
	  }



	private void setYYMMDD(int year, int month, int today) {
	    yy = year;
	    mm = month;
	    dd = today;
	  }

	  String[] months = { "1월", "2월", "3월", "4월", "5월", "6월",
	      "7월", "8월", "9월", "10월", "11월", "12월" };

	  /** Build the GUI. Assumes that setYYMMDD has been called. */
	  private void buildGUI() {
	    getAccessibleContext().setAccessibleDescription(
	        "Calendar not accessible yet. Sorry!");
	    setBorder(BorderFactory.createEtchedBorder());

	    setLayout(new BorderLayout());
	    
	    JPanel tp = new JPanel();
	    tp.setBounds(100, 100, 800, 700);

	    tp.add(yearChoice = new JComboBox());
	    //yearChoice.setEditable(true);
	    for (int i = yy - 5; i <= yy; i++)
	    	yearChoice.addItem(Integer.toString(i));
	    	yearChoice.setSelectedItem(Integer.toString(yy));
	    	yearChoice.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        int i = yearChoice.getSelectedIndex();
	        if (i >= 0) {
	          yy = Integer.parseInt(yearChoice.getSelectedItem()
	              .toString());
	          // System.out.println("Year=" + yy);
	          recompute();
	        }
	        
	      }
	    });
	    

	    tp.add(monthChoice = new JComboBox());
	    for (int i = 0; i < months.length; i++)
	    	monthChoice.addItem(months[i]);
	    	monthChoice.setSelectedItem(months[mm]);
	    	monthChoice.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        int i = monthChoice.getSelectedIndex();
	        if (i >= 0) {
	          mm = i;
	          // System.out.println("Month=" + mm);
	          recompute();
	          today = String.format("%d%d%02d", yy,mm+1,dd);
		        String title = String.format("%d년 %d월", yy,mm+1);
		        jl.setText(title);
		        new team.Record(today).Month_view(ta);
	        }
	      }
	    });
	    monthChoice.getAccessibleContext().setAccessibleName("Months");
	    monthChoice.getAccessibleContext().setAccessibleDescription(
	        "Choose a month of the year");

	    
	    add(BorderLayout.CENTER, tp);

	    JPanel bp = new JPanel();
	    bp.setLayout(new GridLayout(7, 7));
	    labs = new JButton[6][7]; // first row is days

	    bp.add(b0 = new Cal_btn("일"));
	    bp.add(new Cal_btn("월"));
	    bp.add(new Cal_btn("화"));
	    bp.add(new Cal_btn("수"));
	    bp.add(new Cal_btn("목"));
	    bp.add(new Cal_btn("금"));
	    bp.add(new Cal_btn("토"));

	    ActionListener dateSetter = new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        String num = e.getActionCommand();
	        if (!num.equals("")) {
	          // set the current day highlighted
	          setDayActive(Integer.parseInt(num));
	          // When this becomes a Bean, you can
	          // fire some kind of DateChanged event here.
	          // Also, build a similar daySetter for day-of-week btns.
	          today = String.format("%d%d%02d", yy,mm+1,dd);
	          String title = String.format("%d년 %d월 %d일", yy,mm+1,dd);
	          jl.setText(title);
	          new team.Record(today).view(ta);
	        }
	      }
	    };

	    // Construct all the buttons, and add them.
	    for (int i = 0; i < 6; i++)
	      for (int j = 0; j < 7; j++) {
	        bp.add(labs[i][j] = new Cal_btn(""));
	        labs[i][j].addActionListener(dateSetter);
	      }

	    add(BorderLayout.SOUTH, bp);
	  }

	  public final static int dom[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	  /** Compute which days to put where, in the Cal panel */
	  protected void recompute() {
	    // System.out.println("Cal::recompute: " + yy + ":" + mm + ":" + dd);
	    if (mm < 0 || mm > 11)
	      throw new IllegalArgumentException("Month " + mm
	          + " bad, must be 0-11");
	    clearDayActive();
	    calendar = new GregorianCalendar(yy, mm, dd);

	    // Compute how much to leave before the first.
	    // getDay() returns 0 for Sunday, which is just right.
	    leadGap = new GregorianCalendar(yy, mm, 1).get(Calendar.DAY_OF_WEEK) - 1;
	    // System.out.println("leadGap = " + leadGap);

	    int daysInMonth = dom[mm];
	    if (isLeap(calendar.get(Calendar.YEAR)) && mm == 1) ++daysInMonth;

	    // 첫번째 일이 들어가기전 칸들의 텍스트를 비움
	    for (int i = 0; i < leadGap; i++) {
	      labs[0][i].setText("");
	    }

	    // 날짜가 들어가야하는 칸의 날짜들을 넣음
	    for (int i = 1; i <= daysInMonth; i++) {
	      JButton b = labs[(leadGap + i - 1) / 7][(leadGap + i - 1) % 7];
	      //System.out.print(i+"일때" + (leadGap + i - 1)/7 +"-" + (leadGap + i - 1) % 7+"\n");
	      b.setText(Integer.toString(i));
	    }

	    // 넣은 날짜들이후 버튼들의 텍스트를 비움
	    for (int i = leadGap + daysInMonth; i < 6 * 7; i++) {
	      labs[(i) / 7][(i) % 7].setText("");
	    }

	    // 현재 날짜에 음영처리
	    if (thisYear == yy && mm == thisMonth) setDayActive(dd); // shade the box for today

	    // 화면에 그림
	    repaint();
	  }

	  /**
	   * isLeap() returns true if the given year is a Leap Year.
	   * 
	   * "a year is a leap year if it is divisible by 4 but not by 100, except
	   * that years divisible by 400 *are* leap years." -- Kernighan & Ritchie,
	   * _The C Programming Language_, p 37.
	   */
	  public boolean isLeap(int year) {
	    if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) return true;
	    return false;
	  }

	  /** Set the year, month, and day */
	  public void setDate(int yy, int mm, int dd) {
	    // System.out.println("Cal::setDate");
	    this.yy = yy;
	    this.mm = mm; // starts at 0, like Date
	    this.dd = dd;
	    recompute();
	  }

	  /** Unset any previously highlighted day */
	  private void clearDayActive() {
	    JButton b;

	    // First un-shade the previously-selected square, if any
	    if (activeDay > 0) {
	      b = labs[(leadGap + activeDay - 1) / 7][(leadGap + activeDay - 1) % 7];
	      b.setBackground(b0.getBackground());
	      b.repaint();
	      activeDay = -1;
	    }
	  }

	  private int activeDay = -1;

	  /** Set just the day, on the current month */
	  public void setDayActive(int newDay) {

	    clearDayActive();

	    // Set the new one
	    if (newDay <= 0)
	      dd = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
	    else
	      dd = newDay;
	    // Now shade the correct square
	    Component square = labs[(leadGap + newDay - 1) / 7][(leadGap + newDay - 1) % 7];
	    square.setBackground(Color.red);
	    square.repaint();
	    activeDay = newDay;
	  }
}
