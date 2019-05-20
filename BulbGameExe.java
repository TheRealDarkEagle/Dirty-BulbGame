package bulbgame_dirty;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BulbGameExe {

	public static void main(String[] args) {
		Frame.getInstance();
	}
}

class Frame extends JFrame {

	private static Frame frame_instance;
	private ArrayList<BulbButton> bulbList;
	private int counter;
	private JButton exit;
	private JButton restart;
	private boolean isCounting;

	private Frame() {
		this.init();
		this.config();
		this.build();
	}

	private void resetInstance() {
		frame_instance = new Frame();
	}

	private void init() {
		isCounting = false;
		exit = new JButton("EXIT GAME");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
		restart = new JButton("RESTART");
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetInstance();
			}

		});
		this.setUndecorated(true);
		this.setSize(600, 600);
		this.setTitle("Dity BulbGame dude");
		this.bulbList = new ArrayList<>();
	}

	protected void clickBulbs(Point point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		int index = x + y * 5;
		this.bulbList.get(index).changeState();
		if (x != 0) {
			this.bulbList.get(index - 1).changeState();
		}
		if (x != 4) {
			this.bulbList.get(index + 1).changeState();
		}
		if (y != 0) {
			this.bulbList.get(index - 5).changeState();
		}
		if (y != 4) {
			this.bulbList.get(index + 5).changeState();
		}
		if (isCounting) {
			System.out.println(++counter);
		}
	}

	private void config() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int x, y;
		x = y = 0;
		for (int i = 0; i < 25; i++) {
			c.gridx = x;
			c.gridy = y;
			this.bulbList.add(new BulbButton(x, y));
			this.getContentPane().add(this.bulbList.get(i), c);
			if (x / 4 == 1) {
				y++;
				x = -1;
			}
			x++;
		}
		c.anchor = GridBagConstraints.SOUTH;
		for (int i = 0; i < 25; i++) {
			if (new Random().nextBoolean()) {
				this.clickBulbs(this.bulbList.get(i).getPoint());
			}
		}
	}

	private void build() {

		this.getContentPane().add(restart);
		this.getContentPane().add(exit);
		isCounting = true;
		counter = 0;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static Frame getInstance() {
		if (frame_instance == null) {
			frame_instance = new Frame();
		}
		return frame_instance;
	}
}

class BulbButton extends JButton implements ActionListener {

	private boolean on;

	private Point point;

	public BulbButton(int number, int row) {
		this.setActionCommand(String.valueOf(number));
		this.point = new Point(number, row);
		this.on = false;
		this.setBackground(Color.pink);
		this.addActionListener(this);
		this.setPreferredSize(new Dimension(100, 100));
		this.setMinimumSize(new Dimension(75, 75));
		this.setMaximumSize(new Dimension(120, 120));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Frame.getInstance().clickBulbs(this.point);
	}

	public Point getPoint() {
		return this.point;
	}

	public void changeState() {

		if (this.on) {
			this.setBackground(Color.pink);
			this.on = false;
		} else {
			this.setBackground(Color.black);
			this.on = true;
		}

	}

}
