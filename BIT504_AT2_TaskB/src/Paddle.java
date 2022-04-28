import java.awt.Color;

public class Paddle extends Sprite{
	private final static int PADDLE_WIDTH = 10;
	private final static int PADDLE_HEIGHT = 100;
	private final static Color PADDLE_COLOR = Color.BLACK;
	private final static int EDGE_DISTANCE = 40;
	
	public Paddle(Player player, int panelWidth, int panelHeight) {
		setWidth(PADDLE_WIDTH);
		setHeight(PADDLE_HEIGHT);
		setColor(PADDLE_COLOR);
		int xPos;
		
		if (player == Player.One) {
			xPos = EDGE_DISTANCE;
		}
		else {
			xPos = panelWidth - EDGE_DISTANCE - getWidth();
		}
		setInitialPosition(xPos, panelHeight / 2 - (getHeight() /2));
		resetToInitialPosition();
	}
}
