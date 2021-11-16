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

	// �������г�
	JPanel gamepan;

	// Ȩȭ�� �г�
	JPanel p1; // ���
	JPanel p2; // �ߴ�
	JPanel p3; // �ϴ�

	JLabel title;
	JTextArea jta_help;
	JLabel jlb_help;
	JButton btn_start;
	JButton btn_help;
	JButton btn_help_close;

	public HomeFrame() {
		// ���� ����
		super.setTitle("������");

		// ������ �ʱ�ȭ
		init_gamePan();

		// Ȩȭ�� �ʱ�ȭ
		init_home();

		// ��ư�̺�Ʈ �ʱ�ȭ
		init_buttonEvent();

		// ��ġ
		super.setLocation(200, 200);

		// ũ��
		super.setSize(500, 700);
		// pack();

		// ũ������ �Ұ�
		super.setResizable(false);

		// ���
		super.setVisible(true);

		// ����
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Ȩȭ�� �ʱ�ȭ
	private void init_home() {
		// TODO Auto-generated method stub

		// ��� - ���� ���
		p1 = new JPanel();
		p1.setPreferredSize(new Dimension(500, 100));
		
		title = new JLabel("������");
		font = new Font("���� ���", Font.BOLD, 50);
		title.setFont(font);
		p1.add(title, BorderLayout.CENTER);

		// �ߴ� - ���� ���
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(400, 400));
		
		jlb_help = new JLabel();
		jlb_help.setVisible(false);
		p2.add(jlb_help);

		jta_help = new JTextArea();
		jta_help.setEditable(false); // TextArea �����Ұ�
		font = new Font("���� ���", Font.BOLD, 13);
		jta_help.setFont(font);
		jta_help.setVisible(false);
		p2.add(jta_help);

		btn_help_close = new JButton("�ݱ�");
		btn_help_close.setFont(font);
		btn_help_close.setVisible(false);
		p2.add(btn_help_close);

		// �ϴ� - ��ư(����, ����)
		p3 = new JPanel(new GridLayout(1, 2, 30, 0)); // 1�� 2��
		p3.setPreferredSize(new Dimension(500, 100));

		btn_start = new JButton("����"); // ����
		btn_help = new JButton("����"); // ����

		font = new Font("���� ���", Font.BOLD, 20);
		btn_start.setFont(font);
		btn_help.setFont(font);
		p3.add(btn_start, BorderLayout.WEST);
		p3.add(btn_help, BorderLayout.EAST);

		// gamepan �����ӿ� �� �г� �߰�
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);

	}

	// ��ư�̺�Ʈ �ʱ�ȭ
	private void init_buttonEvent() {
		// TODO Auto-generated method stub
		// ��ư�� ������ �� - ����
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���� ��ư���� : true
				System.out.println("--���� ��ư ����");

				// ȭ����ȯ - �������� �����
				new GameFrame();
				
				setVisible(false);

			}

		});

		// ��ư�� ������ �� - ����
		btn_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���� ��ư����
				System.out.println("--���� ��ư ����");

				// ���� ���
				String str_help = String.format("[���ӹ��]\n���ѽð��� ����� ������ 3�� �̻��� ���� ���� ���� Ŭ���Ͽ� �����ϰ�\n "
						+ "�ִ��� ���� ������ ��������.\n\n" + "[������]\n5�޺����� �������� �����Ǹ� �������� Ȱ���� ���� ���� ������ �����غ�����.\n"
						+ "- 3x3��ź : ���� ��ġ�κ��� 3x3 ������ �� ����\n" + "- �� OR �� ��ź : ���� ��ġ�κ��� ���� �Ǵ� ���� 1�� ����\n"
						+ "- �� ��ź : ���� ��ġ�κ��� ���ڸ�� ����\n" + "- Reset : ��ü �� ���ġ\n" + "- 5sec : ���ѽð� 5�� �߰�\n");
				jta_help.setText(str_help);

				// JtextArea, Button ���
				jta_help.setVisible(true);
				btn_help_close.setVisible(true);

				// ��ư�� ������ �� - �ݱ�
				btn_help_close.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						System.out.println("--�ݱ� ��ư ����");
						
						// JtextArea �� �ʱ�ȭ, Button �����
						jta_help.setText("");
						btn_help_close.setVisible(false);
					}
				});
			}
		});
	}
	// ������ �ʱ�ȭ
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
	}

	public static void main(String[] args) {

		new HomeFrame();

	}

}
