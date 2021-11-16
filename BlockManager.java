package myutil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mymain.GameFrame;

public class BlockManager {

	// ��������
	GameFrame game_frame;

	// Setter
	public void setMain_frame(GameFrame game_frame) {
		this.game_frame = game_frame;
	}
	
	// ��� List
	public List<Block> block_list = new ArrayList<Block>();

	// üũ�� ��� ����� List
	public List<Block> check_block_list = new ArrayList<Block>();

	Random rand = new Random();

	// ��ϻ��� �迭 (4���� ����)
	Color[] block_color_array = { Color.red, Color.blue, Color.green, Color.yellow };

	// ����ʱ�ȭ
	Block block;

	// üũ������Ʈ�� ����� ������ �÷� blank�ϱ�
	public void blank_block(int i) {
		
		for (int a = 0; a < check_block_list.size(); a++) {
			// üũ������Ʈ�� a��° ��ü�� �÷����� �ʱ�ȭ
			check_block_list.get(a).color_state = Constant.RGB_BLANK;
			check_block_list.get(a).block_color = null;
		}
	}

	// ��� ������
	public void make_block() {

		// ��ϰ��� : 8�� 8��
		// ���ũ�� : ����, ���� 60��
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				block = new Block();

				// ũ�� �ʱ�ȭ
				block.pos.width = Constant.BLOCK_WIDTH;
				block.pos.height = Constant.BLOCK_HEIGHT;

				// ��ġ �ʱ�ȭ
				block.pos.x = j * Constant.BLOCK_WIDTH;
				block.pos.y = i * Constant.BLOCK_HEIGHT;

				// �� ���󺯰�
				int index = rand.nextInt(block_color_array.length); // 0~3 ������ �����߻�(index��)

				// index���� ���� ���� �������� ����
				// ���� �� -> �����ʾƷ� ������ ���� �����
				block.block_color = block_color_array[index];

				if (index == 0) {
					block.color_state = Constant.RGB_RED;

				} else if (index == 1) {
					block.color_state = Constant.RGB_BLUE;

				} else if (index == 2) {
					block.color_state = Constant.RGB_GREEN;

				} else if (index == 3) {
					block.color_state = Constant.RGB_YELLOW;

				}
				
				// System.out.printf("%d��°��� %d\n", j , color_state);
				
				// ������ ���� List �� ����
				block_list.add(block);
			}
		}
	}

	// ��� �׷���
	public void draw_all(Graphics g) {

		for (Block block : block_list) {

			block.draw(g);
		}
	}

	// setter
	public void setBlock_list(List<Block> block_list) {
		this.block_list = block_list;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

}
