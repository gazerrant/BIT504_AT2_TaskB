import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Font;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
		
		private final static Color BACKGROUND_COLOR = Color.WHITE;
		private final static Color GRAPHICS_COLOR = Color.BLACK;
		private final static int TIMER_DELAY = 5;
		private final static int BALL_SPEED = 2;
		private final static int POINTS_TO_WIN = 3;
		private final static int SCORE_TEXT_X = 100;
		private final static int SCORE_TEXT_Y = 100;
		private final static int SCORE_FONT_SIZE = 50;
		private final static String SCORE_FONT_FAMILY = "Serif";
		int player1Score = 0, player2Score = 0;
		Player gameWinner;
		Ball ball;
		GameState gameState = GameState.Initializing;
		Paddle paddle1, paddle2;
	
		public PongPanel() {
			setBackground(BACKGROUND_COLOR);
			Timer timer = new Timer(TIMER_DELAY, this);
			timer.start();
			addKeyListener(this);
			setFocusable(true);
		}
		
		private void paintDottedLine(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
			g2d.setStroke(dashed);
			g2d.setPaint(GRAPHICS_COLOR);
			g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
			g2d.dispose();
		}
		
		public void createObjects() {
			ball = new Ball(getWidth(), getHeight());
			paddle1 = new Paddle(Player.One, getWidth(), getHeight());
			paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
		}
		
		private void update() {
			switch(gameState) {
				case Initializing: {
					createObjects();
					gameState = GameState.Playing;
					ball.setxVelocity(BALL_SPEED);
					ball.setyVelocity(BALL_SPEED);
					break;
				}
				case Playing: {
					moveObject(paddle1);
					moveObject(paddle2);
					moveObject(ball);
					checkWallBounce();
					checkPaddleBounce();
					checkWin();
					break;
				}
				case GameOver: {
					break;
				}
			}
		}
		
		private void paintSprite(Graphics g, Sprite sprite) {
			g.setColor(sprite.getColor());
			g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
		}
		
		private void paintScores(Graphics g) {
			Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
			String leftScore = Integer.toString(player1Score);
			String rightScore = Integer.toString(player2Score);
			g.setFont(scoreFont);
			g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
			g.drawString(rightScore, getWidth() - SCORE_TEXT_X, SCORE_TEXT_Y);
		}
		
		private void paintWinner(Graphics g) {
			if (gameWinner == Player.One) {
				Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
				g.setFont(scoreFont);
				g.drawString("Player 1 Wins!", (getWidth() / 2) - 150, SCORE_TEXT_Y);
			}
			else if (gameWinner == Player.Two) {
				Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
				g.setFont(scoreFont);
				g.drawString("Player 2 Wins!", (getWidth() / 2) - 150, SCORE_TEXT_Y);
			}
		}
		
		@Override
		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub test
			
		}

		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_W) {
				paddle1.setyVelocity(-1);
			}
			else if (event.getKeyCode() == KeyEvent.VK_S) {
				paddle1.setyVelocity(1);
			}
			if (event.getKeyCode() == KeyEvent.VK_UP) {
				paddle2.setyVelocity(-1);
			}
			else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
				paddle2.setyVelocity(1);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
				paddle1.setyVelocity(0);
			}
			if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
				paddle2.setyVelocity(0);
			}
			
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			update();
			repaint();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			paintDottedLine(g);
			if(gameState != GameState.Initializing) {
				paintSprite(g, ball);
				paintSprite(g, paddle1);
				paintSprite(g, paddle2);
				paintScores(g);
				paintWinner(g);
			}
		}
		
		public void moveObject(Sprite sprite) {
			sprite.setxPosition(sprite.getxPosition() + sprite.getxVelocity(), getWidth());
			sprite.setyPosition(sprite.getyPosition() + sprite.getyVelocity(), getHeight());
		}
		
		public void checkWallBounce() {
			if (ball.getxPosition() <= 0) {
				ball.setxVelocity(-ball.getxVelocity());
				addScore(Player.Two);
				resetBall();
			}
			else if (ball.getxPosition() >= getWidth() - ball.getWidth()) {
				ball.setxVelocity(-ball.getxVelocity());
				addScore(Player.One);
				resetBall();
			}
			if (ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight()) {
				ball.setyVelocity(-ball.getyVelocity());
			}
		}
		
		private void checkPaddleBounce() {
			if (ball.getxVelocity() < 0 && ball.getRect().intersects(paddle1.getRect())) {
				ball.setxVelocity(BALL_SPEED);
			}
			if (ball.getxVelocity() > 0 && ball.getRect().intersects(paddle2.getRect())) {
				ball.setxVelocity(-BALL_SPEED);
			}
		}
		
		public void resetBall() {
			ball.resetToInitialPosition();
		}
		
		private void addScore(Player player) {
			if (player == Player.One) {
				player1Score++;
			}
			if (player == Player.Two) {
				player2Score++;
			}
		}
		
		private void checkWin() {
			if (player1Score >= POINTS_TO_WIN) {
				gameWinner = Player.One;
				gameState = GameState.GameOver;
			}
			else if (player2Score >= POINTS_TO_WIN) {
				gameWinner = Player.Two;
				gameState = GameState.GameOver;
			}
		}
}
