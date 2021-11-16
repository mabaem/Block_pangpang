package myutil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mymain.GameFrame;

public class BlockManager {

	// 참조변수
	GameFrame game_frame;

	// Setter
	public void setMain_frame(GameFrame game_frame) {
		this.game_frame = game_frame;
	}
	
	// 블록 List
	public List<Block> block_list = new ArrayList<Block>();

	// 체크한 블록 저장용 List
	public List<Block> check_block_list = new ArrayList<Block>();

	Random rand = new Random();

	// 블록색깔 배열 (4가지 색상)
	Color[] block_color_array = { Color.red, Color.blue, Color.green, Color.yellow };

	// 블록초기화
	Block block;

	// 체크블럭리스트에 저장된 블럭들의 컬러 blank하기
	public void blank_block(int i) {
		
		for (int a = 0; a < check_block_list.size(); a++) {
			// 체크블럭리스트의 a번째 객체의 컬러정보 초기화
			check_block_list.get(a).color_state = Constant.RGB_BLANK;
			check_block_list.get(a).block_color = null;
		}
	}

	// 블록 만들어라
	public void make_block() {

		// 블록개수 : 8행 8열
		// 블록크기 : 가로, 세로 60씩
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				block = new Block();

				// 크기 초기화
				block.pos.width = Constant.BLOCK_WIDTH;
				block.pos.height = Constant.BLOCK_HEIGHT;

				// 위치 초기화
				block.pos.x = j * Constant.BLOCK_WIDTH;
				block.pos.y = i * Constant.BLOCK_HEIGHT;

				// 블럭 색상변경
				int index = rand.nextInt(block_color_array.length); // 0~3 까지의 난수발생(index값)

				// index값에 따라 색상 상태정보 변경
				// 왼쪽 위 -> 오른쪽아래 순으로 정보 저장됨
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
				
				// System.out.printf("%d번째블록 %d\n", j , color_state);
				
				// 생성한 블럭을 List 에 저장
				block_list.add(block);
			}
		}
	}

	// 블록 그려라
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
