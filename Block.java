package myutil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Block {

	// 블록 색상
	public Color block_color = Color.red;

	// 색상 상태정보 변수 초기화
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
