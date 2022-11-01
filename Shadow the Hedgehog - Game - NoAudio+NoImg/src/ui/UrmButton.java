package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.Buttons.B_HEIGHT_DEFAULT;
import static utilz.Constants.UI.Buttons.B_WIDTH_DEFAULT;
import static utilz.Constants.UI.URMButtons.*;

public class UrmButton extends PauseButton {
	private BufferedImage[] imgs;
	private int index;
	private boolean mouseOver, mousePressed;

	public UrmButton(int x, int y, int width, int height, int index) {
		super(x, y, width, height);
		this.index = index;
		loadImgs();
	}

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
		for(int i = 0; i < imgs.length;i++)
			imgs[i]=temp.getSubimage(i*URM_DEFAULT_WIDTH, index * URM_DEFAULT_HEIGHT, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT);

	}

	public void update() {
		index = 1;
		if (mouseOver)
			index = 0;
		if (mousePressed)
			index = 2;

	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, null);
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}
