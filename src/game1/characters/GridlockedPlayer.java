package game1.characters;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import game1.world.Cell;
import game1.world.World1;

public class GridlockedPlayer {
	private double x,y;
	private boolean up,down,right,left,updown,rightLeft;
	private int cellSize=64;
	private Cell cell;
	private int moveTimer = 250;
	private int moving; //0:no move,1:moving to right,2:moving down,3:moving to left,4:moving up
	private Image img,right1,right2,left1,left2,up1,up2,down1,down2;

	public GridlockedPlayer(){
		x = 100;
		y = 100;
		this.getCell();
	}

	public GridlockedPlayer(int i, int j){
		x = i*cellSize+10;
		y = j*cellSize+10;
		this.getCell();
		moving =0;
		try{
		right1=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"droite1.png").getScaledCopy(39,50);
		right2=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"droite2.png").getScaledCopy(39,50);
		left1=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"gauche1.png").getScaledCopy(39,50);
		left2=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"gauche2.png").getScaledCopy(39,50);
		up1=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"haut1.png").getScaledCopy(39,50);
		up2=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"haut2.png").getScaledCopy(39,50);
		down1=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"bas1.png").getScaledCopy(39,50);
		down2=new Image(World1.DIRECTORY_IMAGES+"Player"+File.separator+"bas2.png");
		img=right1;
		} catch( Exception e){
			System.out.println("image du player-labyrinth non chargée");
		}

	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		arg2.setColor(Color.magenta);
		//arg2.fillRect((float)x,(float) y, (float)50, (float)50);
		arg2.drawImage(img, (float)x+5, (float)y);

	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		moveTimer = moveTimer - arg2;
		if(moveTimer <=0){
			moveTimer = 150;
			if(moving==0){
				this.getCell();
				move();
			}else{
				switch (moving){
				case 1:
					this.x+=cellSize/2;
					img=right2;
					break;
				case 2:
					y+=cellSize/2;
					img=down2;;
					break;
				case 3:
					this.x-=cellSize/2;
					img=left2;
					break;
				case 4:
					y-=cellSize/2;
					img=up2;
					break;
				}
				moving=0;
			}
		}
	}


	public void keyPressed(int key, char c) {
		switch (key){

		case Input.KEY_UP:
			up=true;
			updown=false;
			break;

		case Input.KEY_DOWN:
			down=true;
			updown=true;
			break;

		case Input.KEY_LEFT:
			left=true;
			rightLeft=false;
			break;
		case Input.KEY_RIGHT:
			right=true;
			rightLeft=true;
			break;
		case Input.KEY_ESCAPE:
			System.exit(0);
			break;
		}

	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			up=false;
			break;
		case Input.KEY_RIGHT:
			right=false;
			break;
		case Input.KEY_LEFT:
			left=false;
			break;
		case Input.KEY_DOWN:
			down=false;
			break;
		}
	}

	private void move() {
		if(((up && !down) || (up && down && !updown)) )
		{
			if(!this.cell.isNorthWall() && y > cellSize) {
				moving=4;
				y = y - cellSize/2;
				img=up1;
			}
		}
		if(((down && !up) || (up && down && updown))){
			if(!this.cell.isSouthWall() && y < game1.world.World1.getLabyrinth().getLines()*cellSize - cellSize) {
				moving=2;
				y = y + cellSize/2;
				img=down1;
			}
		}
		if(((left && !right)|| (left && right && !rightLeft)))
		{
			if(!this.cell.isWestWall() && x > cellSize){
				moving=3;
				x = x - cellSize/2;
				img=left1;
			}
		}
		if(((!left && right)|| (left && right && rightLeft)))
		{
			if(!this.cell.isEastWall() && x < game1.world.World1.getLabyrinth().getRows()*cellSize - cellSize ){
				moving=1;
				x = x + cellSize/2;
				img=right1;
			}
		}
	}

	public void getCell(){
		int j=(int) Math.floor((x-10)/cellSize);
		int i=(int) Math.floor((y-10)/cellSize);
		this.cell=game1.world.World1.getLabyrinth().getCell(i,j);
	}

	public int getI()
	{
		return this.cell.getI();
	}

	public int getJ()
	{
		return this.cell.getJ();
	}
}
