package myutil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Block {

	// ��� ����
	public Color block_color = Color.red;

	// ���� �������� ���� �ʱ�ȭ
	public int color_state = 0;

	public Rectangle pos = new Rectangle();

	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		g.setColor(block_color);
		g.fillRect(pos.x, pos.y, pos.width, pos.height);
		g.setColor(Color.black);
		g.drawRect(pos.x, pos.y, pos.width, pos.height);

	}
}
