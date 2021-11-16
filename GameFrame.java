package mymain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import myutil.Block;
import myutil.BlockManager;
import myutil.Constant;

public class GameFrame extends JFrame {

	Font font = new Font("���� ���", Font.BOLD, 20);

	boolean check_up = false; // �����ߴ��� Ȯ���ϴ� ���� - ��
	boolean check_down = false; // �����ߴ��� Ȯ���ϴ� ���� - ��
	boolean check_left = false; // �����ߴ��� Ȯ���ϴ� ���� - ��
	boolean check_right = false; // �����ߴ��� Ȯ���ϴ� ���� - ��

	// ������ �г�
	JPanel gamepan;

	// ������ �г�
	JPanel scorepan;
	JPanel itempan;
	JPanel blockpan;
	JPanel timerpan;

	JLabel jlb_score;
	JLabel jlb_item;

	Timer timer;

	JProgressBar jpb_time;

	TimerTask timer_task;

	BlockManager blockManager = new BlockManager();

	public GameFrame() {
		// ���� ����
		super.setTitle("������");

		blockManager.setMain_frame(this);

		// ������ �ʱ�ȭ
		init_gamePan();

		// ���콺�̺�Ʈ
		init_mouseEvent();

		// ȭ���ʱ�ȭ
		init_scorePan();
		init_itemPan();
		init_timerPan();

		init_blockPan();

		// ��ġ
		super.setLocation(200, 200);

		// ũ��
		super.setSize(500, 700);
		//pack();

		// ũ������ �Ұ�
		super.setResizable(false);

		// ���
		super.setVisible(true);

		// ����
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class TimmbarThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// super.run();

			System.out.printf("[%s]������ ����\n", Thread.currentThread().getName());

			int time = 60;
			for (int sec = time; sec >= 0; sec--) {
				
				jpb_time.setValue(sec);

				try {
					Thread.sleep(1000); // 1�ʴ� 1���� ���
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // end-while

		}// end-run

	}// end-TimebarThread

	Thread timebar_thread;

	private void init_mouseEvent() {
		// TODO Auto-generated method stub

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);

				for (int i = 0; i < blockManager.block_list.size(); i++) {

					// System.out.printf("%d,%d\n",blockManager.block_list.get(i).pos.x, blockManager.block_list.get(i).pos.y);

					Rectangle rect = new Rectangle(blockManager.block_list.get(i).pos.x,
							blockManager.block_list.get(i).pos.y + 145, Constant.BLOCK_WIDTH, Constant.BLOCK_HEIGHT);

					Point pt = e.getPoint();

					if (rect.contains(pt.x, pt.y)) {

						System.out.printf("#####(%3d,%3d)", blockManager.block_list.get(i).pos.x,
								blockManager.block_list.get(i).pos.y);

						System.out.printf("%2d�� Ŭ��#####\n", i);

						// Ŭ���� ���� ������ check_block_list�� ����
						blockManager.check_block_list.add(blockManager.block_list.get(i));

						// 1. ������ ���� ������ ���ο� ���� �����Ǿ� �Ʒ��� ������

						// 2. ���콺Ŭ���� ���� ��,��,��,�Ϸ� ���� ������ ���� �ִ��� �˻�
						// �� ���� ������ üũ�ϴ� �޼���

						removeCheck_up(i);
						removeCheck_down(i);
						removeCheck_left(i);
						removeCheck_right(i);

						System.out.printf("check_block_list.size()=%d\n", blockManager.check_block_list.size());

						// check_block_list ũ�Ⱑ 3 �̻��̸� (=�������� ���� 3�� �̻��̸�)
						if (blockManager.check_block_list.size() >= 3) {
							// blank ó��
							for (int a = 0; a < blockManager.check_block_list.size(); a++) {
								blockManager.blank_block(a);

								// ���� �ٽ� �׷���
								blockpan.repaint();

								if (a == blockManager.check_block_list.size() - 1) {
									// check_block_list �ʱ�ȭ�ϱ�
									blockManager.check_block_list.clear();
								}

							}
						} else {
							// check_block_list �ʱ�ȭ�ϱ�
							blockManager.check_block_list.clear();
						}
					}

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseReleased(e);
			}

		});

	}

	// �� ���� ������ üũ�ϴ� �޼���(��,��,��,��)
	// ������ : ������(i+1)
	// ���� : ������(i-1)
	// ���� : ������(i-8)
	// �Ʒ��� : ������(i+8)

	protected void removeCheck_up(int i) {
		// TODO Auto-generated method stub

		// ���ʰ˻�
		// ������� ���� �� ���� �ƴ��� üũ(���� ������ ���ʰ˻��� �ʿ� ����)
		if (blockManager.block_list.get(i).pos.y > 0) {
			// ���� ���� ������ ������? ����, i����, ���ȣ��
			if (blockManager.block_list.get(i - 8).color_state == blockManager.block_list.get(i).color_state) {

				check_up = true; // üũ���� true

				System.out.printf("(��)%2d�� ���� ����!!\n", i - 8);

				i = i - 8;
				// check_block_list�� ����
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_up == true) { // ���ʺ��� ���� �����̸�

					removeCheck_left(i);
					removeCheck_right(i);

				}

				removeCheck_up(i);
			}
			// ���� ���� ������ �ٸ���? ���� STOP
			else {
				check_up = false; // üũ���� false
				System.out.printf("(��)%2d�� �ٸ������̹Ƿ� ���� X\n", i - 8);

			}

		}
	}

	protected void removeCheck_down(int i) {
		// TODO Auto-generated method stub

		// �Ʒ��ʰ˻�
		// ������� �Ʒ��� �� ���� �ƴ��� üũ(�Ʒ��� ������ �Ʒ��ʰ˻��� �ʿ� ����)
		if (blockManager.block_list.get(i).pos.y < 420) {
			// �Ʒ��� ���� ������ ������? ����, i����, ���ȣ��
			if (blockManager.block_list.get(i + 8).color_state == blockManager.block_list.get(i).color_state) {

				check_down = true; // üũ���� true

				System.out.printf("(��)%2d�� ���� ����!!\n", i + 8);

				i = i + 8;
				// check_block_list�� ����
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_down == true) { // �Ʒ��ʺ��� ���� �����̸�
					System.out.println("check_down == true");
					removeCheck_left(i);
					removeCheck_right(i);
				}
				
				removeCheck_down(i);
			}
			// �Ʒ��� ���� ������ �ٸ���? ���� STOP
			else {
				check_down = false; // üũ���� false
				System.out.printf("(��)%2d�� �ٸ������̹Ƿ� ���� X\n", i + 8);

			}

		}
	}

	protected void removeCheck_left(int i) {
		// TODO Auto-generated method stub

		// ���ʰ˻�
		// ������� ���� �� ���� �ƴ��� üũ(���� ������ ���ʰ˻��� �ʿ� ����)
		if (blockManager.block_list.get(i).pos.x > 0) {
			// ���� ���� ������ ������? ����, i����, ���ȣ��
			if (blockManager.block_list.get(i - 1).color_state == blockManager.block_list.get(i).color_state) {

				check_left = true; // üũ���� true

				System.out.printf("(��)%2d�� ���� ����!!\n", i - 1);

				i = i - 1;
				// check_block_list�� ����
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_left == true) { // ���ʺ��� ���� �����̸�
					System.out.println("check_left == true");
					removeCheck_up(i);
					removeCheck_down(i);
				}

				removeCheck_left(i);
			}
			// ���� ���� ������ �ٸ���? ���� STOP
			else {
				check_left = false; // üũ���� false
				System.out.printf("(��)%2d�� �ٸ������̹Ƿ� ���� X\n", i - 1);

			}

		}

	}

	protected void removeCheck_right(int i) {
		// TODO Auto-generated method stub

		// �����ʰ˻�
		// ������� ������ �� ���� �ƴ��� üũ(������ ������ �����ʰ˻��� �ʿ� ����)
		if (blockManager.block_list.get(i).pos.x < 420) {

			// ������ ���� ������ ������? ����, i����, ���ȣ��
			if (blockManager.block_list.get(i + 1).color_state == blockManager.block_list.get(i).color_state) {

				check_right = true; // üũ���� true

				System.out.printf("(��)%2d�� ���� ����!!\n", i + 1);

				i = i + 1;
				// check_block_list�� ����
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_right == true) { // �����ʺ��� ���� �����̸�
					System.out.println("check_right == true");
					removeCheck_up(i);
					removeCheck_down(i);
				}

				removeCheck_right(i);

			}
			// ������ ���� ������ �ٸ���? ���� STOP
			else {

				check_right = false; // üũ���� false
				System.out.printf("(��)%2d�� �ٸ������̹Ƿ� ���� X\n", i + 1);

			}

		}

	}

	private void init_timerPan() {
		// TODO Auto-generated method stub
		timerpan = new JPanel();
		timerpan.setPreferredSize(new Dimension(Constant.GAMEPAN_WIDTH, 119));

		jpb_time = new JProgressBar(JProgressBar.HORIZONTAL, 0, 60);

		jpb_time.setForeground(Color.BLUE);
		jpb_time.setBackground(Color.WHITE);
		
		jpb_time.setValue(jpb_time.getMaximum());

		this.add(jpb_time, BorderLayout.SOUTH);

		timebar_thread = new TimmbarThread();
		timebar_thread.start();

		// ���μ��� �޼ҵ� ����
		process();
	}

	// Ÿ�̸� �۵��� ������ �޼ҵ�
	protected void process() {

		// �� �����

		blockManager.make_block();

		// �� ������ �� �̺�Ʈ ó��

	}

	private void init_blockPan() {
		// TODO Auto-generated method stub

		blockpan = new JPanel() {

			// �׸��⵵�� ������
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				// super.paintComponent(g);

				// �� : 8�� 8��, �迭�� ����, block Ŭ���� �����ϱ�
				// ù���� ��ġ�� ������ �����ؼ� ����ϱ�

				// �� �׸���
				blockManager.draw_all(g);

			}

		};
		blockpan.setPreferredSize(new Dimension(Constant.GAMEPAN_WIDTH, 481));
		gamepan.add(blockpan);

	}

	private void init_itemPan() {
		// TODO Auto-generated method stub
		itempan = new JPanel();
		itempan.setPreferredSize(new Dimension(Constant.GAMEPAN_WIDTH, 50));

		jlb_item = new JLabel("������");
		jlb_item.setFont(font);

		itempan.add(jlb_item);

		gamepan.add(itempan);
	}

	private void init_scorePan() {
		// TODO Auto-generated method stub
		scorepan = new JPanel();
		scorepan.setPreferredSize(new Dimension(Constant.GAMEPAN_WIDTH, 50));

		jlb_score = new JLabel("����");
		jlb_score.setHorizontalAlignment(SwingConstants.RIGHT); // ��������
		jlb_score.setFont(font);

		scorepan.add(jlb_score);

		gamepan.add(scorepan);

	}

	private void init_gamePan() {
		// TODO Auto-generated method stub
		gamepan = new JPanel() {
			// �׸��⵵�� ������
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				// super.paintComponent(g);

				g.clearRect(0, 0, Constant.GAMEPAN_WIDTH, Constant.GAMEPAN_HEIGHT);

			}
		};
		gamepan.setPreferredSize(new Dimension(Constant.GAMEPAN_WIDTH, Constant.GAMEPAN_HEIGHT));

		this.add(gamepan);

	}

	public static void main(String[] args) {

		new GameFrame();

	}

	// getter
	public BlockManager getBlockManager() {
		return blockManager;
	}

}