package mymain;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import myutil.Constant;

public class HomeFrame extends JFrame {

	Font font;

	// 윈도우패널
	JPanel gamepan;

	// 홈화면 패널
	JPanel p1; // 상단
	JPanel p2; // 중단
	JPanel p3; // 하단

	JLabel title;
	JTextArea jta_help;
	JLabel jlb_help;
	JButton btn_start;
	JButton btn_help;
	JButton btn_help_close;

	public HomeFrame() {
		// 제목 설정
		super.setTitle("블럭깨기");

		// 게임판 초기화
		init_gamePan();

		// 홈화면 초기화
		init_home();

		// 버튼이벤트 초기화
		init_buttonEvent();

		// 위치
		super.setLocation(200, 200);

		// 크기
		super.setSize(500, 700);
		// pack();

		// 크기조절 불가
		super.setResizable(false);

		// 출력
		super.setVisible(true);

		// 종료
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// 홈화면 초기화
	private void init_home() {
		// TODO Auto-generated method stub

		// 상단 - 제목 출력
		p1 = new JPanel();
		p1.setPreferredSize(new Dimension(500, 100));
		
		title = new JLabel("블럭깨기");
		font = new Font("맑은 고딕", Font.BOLD, 50);
		title.setFont(font);
		p1.add(title, BorderLayout.CENTER);

		// 중단 - 도움말 출력
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(400, 400));
		
		jlb_help = new JLabel();
		jlb_help.setVisible(false);
		p2.add(jlb_help);

		jta_help = new JTextArea();
		jta_help.setEditable(false); // TextArea 수정불가
		font = new Font("맑은 고딕", Font.BOLD, 13);
		jta_help.setFont(font);
		jta_help.setVisible(false);
		p2.add(jta_help);

		btn_help_close = new JButton("닫기");
		btn_help_close.setFont(font);
		btn_help_close.setVisible(false);
		p2.add(btn_help_close);

		// 하단 - 버튼(시작, 도움말)
		p3 = new JPanel(new GridLayout(1, 2, 30, 0)); // 1행 2열
		p3.setPreferredSize(new Dimension(500, 100));

		btn_start = new JButton("시작"); // 시작
		btn_help = new JButton("도움말"); // 도움말

		font = new Font("맑은 고딕", Font.BOLD, 20);
		btn_start.setFont(font);
		btn_help.setFont(font);
		p3.add(btn_start, BorderLayout.WEST);
		p3.add(btn_help, BorderLayout.EAST);

		// gamepan 프레임에 각 패널 추가
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);

	}

	// 버튼이벤트 초기화
	private void init_buttonEvent() {
		// TODO Auto-generated method stub
		// 버튼이 눌렸을 때 - 시작
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 시작 버튼상태 : true
				System.out.println("--시작 버튼 눌림");

				// 화면전환 - 새프레임 만들기
				new GameFrame();
				
				setVisible(false);

			}

		});

		// 버튼이 눌렸을 때 - 도움말
		btn_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 도움말 버튼상태
				System.out.println("--도움말 버튼 눌림");

				// 도움말 출력
				String str_help = String.format("[게임방법]\n제한시간이 종료될 때까지 3개 이상의 같은 색깔 블럭을 클릭하여 제거하고\n "
						+ "최대한 많은 점수를 얻으세요.\n\n" + "[아이템]\n5콤보마다 아이템이 생성되며 아이템을 활용해 더욱 높은 점수에 도전해보세요.\n"
						+ "- 3x3폭탄 : 누른 위치로부터 3x3 사이즈 블럭 제거\n" + "- ─ OR │ 폭탄 : 누른 위치로부터 가로 또는 세로 1줄 제거\n"
						+ "- ┼ 폭탄 : 누른 위치로부터 십자모양 제거\n" + "- Reset : 전체 블럭 재배치\n" + "- 5sec : 제한시간 5초 추가\n");
				jta_help.setText(str_help);

				// JtextArea, Button 출력
				jta_help.setVisible(true);
				btn_help_close.setVisible(true);

				// 버튼이 눌렸을 때 - 닫기
				btn_help_close.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						System.out.println("--닫기 버튼 눌림");
						
						// JtextArea 값 초기화, Button 숨기기
						jta_help.setText("");
						btn_help_close.setVisible(false);
					}
				});
			}
		});
	}
	// 게임판 초기화
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
	}

	public static void main(String[] args) {

		new HomeFrame();

	}

}
