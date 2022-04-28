import java.awt.Color;
import java.awt.Rectangle;


public class Sprite {
	
	private int xPosition;
	private int yPosition;
	private int xVelocity;
	private int yVelocity;
	private int width;
	private int height;
	private int initialxPosition;
	private int initialyPosition;
	private Color color;
	
	public Color getColor() {
		return color;
	}
	
	public int getxPosition() {
		return xPosition;
	}
	
	public int getyPosition() {
		return yPosition;
	}
	
	public int getxVelocity() {
		return xVelocity;
	}
	
	public int getyVelocity() {
		return yVelocity;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setInitialPosition(int initialx, int initialy) {
		initialxPosition = initialx;
		initialyPosition = initialy;
	}
	
	public void resetToInitialPosition() {
		setxPosition(initialxPosition);
		setyPosition(initialyPosition);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	public void setxPosition(int newX, int panelWidth) {
		xPosition = newX;
		if (xPosition < 0) {
			xPosition = 0;
		}
		else if (xPosition + width > panelWidth) {
			xPosition = panelWidth - width;
		}
	}
	
	public void setyPosition(int newY, int panelHeight) {
		yPosition = newY;
		if (yPosition < 0) {
			yPosition = 0;
		}
		else if (yPosition + height > panelHeight) {
			yPosition = panelHeight - height;
		}
	}
	
	public void setxVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public void setyVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Rectangle getRect() {
		return new Rectangle(getxPosition(), getyPosition(), getWidth(), getHeight());
	}
}
