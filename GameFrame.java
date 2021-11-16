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

	Font font = new Font("맑은 고딕", Font.BOLD, 20);

	boolean check_up = false; // 삭제했는지 확인하는 변수 - 상
	boolean check_down = false; // 삭제했는지 확인하는 변수 - 하
	boolean check_left = false; // 삭제했는지 확인하는 변수 - 좌
	boolean check_right = false; // 삭제했는지 확인하는 변수 - 우

	// 윈도우 패널
	JPanel gamepan;

	// 게임판 패널
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
		// 제목 설정
		super.setTitle("블럭깨기");

		blockManager.setMain_frame(this);

		// 게임판 초기화
		init_gamePan();

		// 마우스이벤트
		init_mouseEvent();

		// 화면초기화
		init_scorePan();
		init_itemPan();
		init_timerPan();

		init_blockPan();

		// 위치
		super.setLocation(200, 200);

		// 크기
		super.setSize(500, 700);
		//pack();

		// 크기조절 불가
		super.setResizable(false);

		// 출력
		super.setVisible(true);

		// 종료
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class TimmbarThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// super.run();

			System.out.printf("[%s]쓰레드 구동\n", Thread.currentThread().getName());

			int time = 60;
			for (int sec = time; sec >= 0; sec--) {
				
				jpb_time.setValue(sec);

				try {
					Thread.sleep(1000); // 1초당 1번씩 대기
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

						System.out.printf("%2d번 클릭#####\n", i);

						// 클릭한 블럭의 정보를 check_block_list에 저장
						blockManager.check_block_list.add(blockManager.block_list.get(i));

						// 1. 지워진 블럭의 위에서 새로운 블럭이 생성되어 아래로 내려옴

						// 2. 마우스클릭된 블럭의 좌,우,상,하로 같은 색상의 블럭이 있는지 검색
						// 블럭 색상 같은지 체크하는 메서드

						removeCheck_up(i);
						removeCheck_down(i);
						removeCheck_left(i);
						removeCheck_right(i);

						System.out.printf("check_block_list.size()=%d\n", blockManager.check_block_list.size());

						// check_block_list 크기가 3 이상이면 (=같은색상 블럭이 3개 이상이면)
						if (blockManager.check_block_list.size() >= 3) {
							// blank 처리
							for (int a = 0; a < blockManager.check_block_list.size(); a++) {
								blockManager.blank_block(a);

								// 블럭판 다시 그려라
								blockpan.repaint();

								if (a == blockManager.check_block_list.size() - 1) {
									// check_block_list 초기화하기
									blockManager.check_block_list.clear();
								}

							}
						} else {
							// check_block_list 초기화하기
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

	// 블럭 색상 같은지 체크하는 메서드(상,하,좌,우)
	// 오른쪽 : 다음블럭(i+1)
	// 왼쪽 : 다음블럭(i-1)
	// 위쪽 : 다음블럭(i-8)
	// 아래쪽 : 다음블럭(i+8)

	protected void removeCheck_up(int i) {
		// TODO Auto-generated method stub

		// 위쪽검사
		// 현재블럭이 위쪽 끝 블럭이 아닌지 체크(위쪽 끝블럭은 위쪽검사할 필요 없음)
		if (blockManager.block_list.get(i).pos.y > 0) {
			// 위쪽 블럭의 색상이 같으면? 제거, i제거, 재귀호출
			if (blockManager.block_list.get(i - 8).color_state == blockManager.block_list.get(i).color_state) {

				check_up = true; // 체크변수 true

				System.out.printf("(상)%2d번 또한 삭제!!\n", i - 8);

				i = i - 8;
				// check_block_list에 저장
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_up == true) { // 위쪽블럭이 같은 색상이면

					removeCheck_left(i);
					removeCheck_right(i);

				}

				removeCheck_up(i);
			}
			// 왼쪽 블럭의 색상이 다르면? 제거 STOP
			else {
				check_up = false; // 체크변수 false
				System.out.printf("(상)%2d번 다른색상이므로 제거 X\n", i - 8);

			}

		}
	}

	protected void removeCheck_down(int i) {
		// TODO Auto-generated method stub

		// 아래쪽검사
		// 현재블럭이 아래쪽 끝 블럭이 아닌지 체크(아래쪽 끝블럭은 아래쪽검사할 필요 없음)
		if (blockManager.block_list.get(i).pos.y < 420) {
			// 아래쪽 블럭의 색상이 같으면? 제거, i제거, 재귀호출
			if (blockManager.block_list.get(i + 8).color_state == blockManager.block_list.get(i).color_state) {

				check_down = true; // 체크변수 true

				System.out.printf("(하)%2d번 또한 삭제!!\n", i + 8);

				i = i + 8;
				// check_block_list에 저장
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_down == true) { // 아래쪽블럭이 같은 색상이면
					System.out.println("check_down == true");
					removeCheck_left(i);
					removeCheck_right(i);
				}
				
				removeCheck_down(i);
			}
			// 아래쪽 블럭의 색상이 다르면? 제거 STOP
			else {
				check_down = false; // 체크변수 false
				System.out.printf("(하)%2d번 다른색상이므로 제거 X\n", i + 8);

			}

		}
	}

	protected void removeCheck_left(int i) {
		// TODO Auto-generated method stub

		// 왼쪽검사
		// 현재블럭이 왼쪽 끝 블럭이 아닌지 체크(왼쪽 끝블럭은 왼쪽검사할 필요 없음)
		if (blockManager.block_list.get(i).pos.x > 0) {
			// 왼쪽 블럭의 색상이 같으면? 제거, i제거, 재귀호출
			if (blockManager.block_list.get(i - 1).color_state == blockManager.block_list.get(i).color_state) {

				check_left = true; // 체크변수 true

				System.out.printf("(좌)%2d번 또한 삭제!!\n", i - 1);

				i = i - 1;
				// check_block_list에 저장
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_left == true) { // 왼쪽블럭이 같은 색상이면
					System.out.println("check_left == true");
					removeCheck_up(i);
					removeCheck_down(i);
				}

				removeCheck_left(i);
			}
			// 왼쪽 블럭의 색상이 다르면? 제거 STOP
			else {
				check_left = false; // 체크변수 false
				System.out.printf("(좌)%2d번 다른색상이므로 제거 X\n", i - 1);

			}

		}

	}

	protected void removeCheck_right(int i) {
		// TODO Auto-generated method stub

		// 오른쪽검사
		// 현재블럭이 오른쪽 끝 블럭이 아닌지 체크(오른쪽 끝블럭은 오른쪽검사할 필요 없음)
		if (blockManager.block_list.get(i).pos.x < 420) {

			// 오른쪽 블럭의 색상이 같으면? 제거, i증가, 재귀호출
			if (blockManager.block_list.get(i + 1).color_state == blockManager.block_list.get(i).color_state) {

				check_right = true; // 체크변수 true

				System.out.printf("(우)%2d번 또한 삭제!!\n", i + 1);

				i = i + 1;
				// check_block_list에 저장
				blockManager.check_block_list.add(blockManager.block_list.get(i));

				if (check_right == true) { // 오른쪽블럭이 같은 색상이면
					System.out.println("check_right == true");
					removeCheck_up(i);
					removeCheck_down(i);
				}

				removeCheck_right(i);

			}
			// 오른쪽 블럭의 색상이 다르면? 제거 STOP
			else {

				check_right = false; // 체크변수 false
				System.out.printf("(우)%2d번 다른색상이므로 제거 X\n", i + 1);

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

		// 프로세스 메소드 실행
		process();
	}

	// 타이머 작동시 실행할 메소드
	protected void process() {

		// 블럭 만들기

		blockManager.make_block();

		// 블럭 눌렀을 때 이벤트 처리

	}

	private void init_blockPan() {
		// TODO Auto-generated method stub

		blockpan = new JPanel() {

			// 그리기도구 재정의
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				// super.paintComponent(g);

				// 블럭 : 8행 8열, 배열로 저장, block 클래스 생성하기
				// 첫블럭의 위치는 변수로 선언해서 사용하기

				// 블럭 그리기
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

		jlb_item = new JLabel("아이템");
		jlb_item.setFont(font);

		itempan.add(jlb_item);

		gamepan.add(itempan);
	}

	private void init_scorePan() {
		// TODO Auto-generated method stub
		scorepan = new JPanel();
		scorepan.setPreferredSize(new Dimension(Constant.GAMEPAN_WIDTH, 50));

		jlb_score = new JLabel("점수");
		jlb_score.setHorizontalAlignment(SwingConstants.RIGHT); // 우측정렬
		jlb_score.setFont(font);

		scorepan.add(jlb_score);

		gamepan.add(scorepan);

	}

	private void init_gamePan() {
		// TODO Auto-generated method stub
		gamepan = new JPanel() {
			// 그리기도구 재정의
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