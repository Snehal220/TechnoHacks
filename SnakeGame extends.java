package Snehal.Project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakeGame extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 20;
    private static final int TILE_SIZE = 20;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;

    private int[][] grid;
    private int snakeLength;
    private int[] snakeX, snakeY;
    private int appleX, appleY;
    private boolean isGameOver;
    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        grid = new int[GRID_WIDTH][GRID_HEIGHT];
        snakeX = new int[GRID_SIZE * GRID_SIZE];
        snakeY = new int[GRID_SIZE * GRID_SIZE];
        snakeLength = 1;
        isGameOver = false;

        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(150, this);
        timer.start();

        initializeGame();
    }

    private void initializeGame() {
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                grid[i][j] = 0;
            }
        }

        snakeX[0] = GRID_WIDTH / 2;
        snakeY[0] = GRID_HEIGHT / 2;

        spawnApple();
    }

    private void spawnApple() {
        Random random = new Random();
        appleX = random.nextInt(GRID_WIDTH);
        appleY = random.nextInt(GRID_HEIGHT);

        while (grid[appleX][appleY] != 0) {
            appleX = random.nextInt(GRID_WIDTH);
            appleY = random.nextInt(GRID_HEIGHT);
        }

        grid[appleX][appleY] = 2; // 2 represents the apple in the grid
    }

    private void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        if (direction == Direction.UP) {
            snakeY[0]--;
        } else if (direction == Direction.DOWN) {
            snakeY[0]++;
        } else if (direction == Direction.LEFT) {
            snakeX[0]--;
        } else if (direction == Direction.RIGHT) {
            snakeX[0]++;
        }

        checkCollision();
        checkApple();
    }

    private void checkCollision() {
        if (snakeX[0] < 0 || snakeX[0] >= GRID_WIDTH || snakeY[0] < 0 || snakeY[0] >= GRID_HEIGHT) {
            isGameOver = true;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isGameOver = true;
            }
        }

        if (isGameOver) {
            timer.stop();
        }
    }

    private void checkApple() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            snakeLength++;
            spawnApple();
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction direction = Direction.RIGHT;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                if (grid[i][j] == 2) {
                    g.setColor(Color.RED);
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (i == appleX && j == appleY) {
                    g.setColor(Color.RED);
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (i == snakeX[0] && j == snakeY[0]) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && direction != Direction.DOWN) {
            direction = Direction.UP;
        } else if (key == KeyEvent.VK_DOWN && direction != Direction.UP) {
            direction = Direction.DOWN;
        } else if (key == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        } else if (key == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}
