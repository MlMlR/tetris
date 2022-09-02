package tetris;

import tetrisblocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameArea extends JPanel
{
    private int gridRows;
    private int gridColumns;
    private int gridCellSize;
    private Color[][] background;
    private TetrisBlock block;
    private TetrisBlock testBlock;
    private TetrisBlock [] blocks;
    public GameArea(int columns)
    {
        this.setLayout(null);
        this.setBounds(100,100,200,400);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        gridColumns= columns;
        gridCellSize = this.getBounds().width / gridColumns;
        gridRows = this.getBounds().height / gridCellSize;

        background = new Color[gridRows][gridColumns];

        blocks = new TetrisBlock[]{
            new JShape(),
            new LShape(),
            new IShape(),
            new ZShape(),
            new SShape(),
            new TShape(),
            new OShape()
        };
    }

    public void spawnBlock()
    {
        Random r = new Random();
        block = blocks[r.nextInt(blocks.length)];
        block.spawn(gridColumns);
    }

    public boolean isBlockOutOfBounds()
    {
        if (block.getY() < 0 )
        {
            block = null;
            return true;
        }
        return false;
    }

    public boolean moveBlockDown()
    {
        if(!checkBottom())
        {
            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }

    public void moveBlockRight()
    {
        if(block == null) return;
        if(!checkRight()) return;
        block.moveRight();
        repaint();
    }
    public void moveBlockLeft()
    {
        if(block == null) return;
        if(!checkLeft()) return;

        block.moveLeft();
        repaint();
    }
    public void dropBlock()
    {
        if(block == null) return;
        while(checkBottom())
        {
            block.moveDown();
        }
        repaint();
    }
    public void rotateBlock()
    {
        if(block == null) return;

        if(checkRotation()) return;
        block.rotate();

        if(block.getLeftEdge() < 0) block.setX(0);
        if(block.getRightEdge() > gridColumns) block.setX(gridColumns - block.getWidth());
        if(block.getBottomEdge() >= gridRows) block.setY(gridRows - block.getHeight());

        repaint();
    }


    private boolean checkRotation(){
        testBlock = block;

        testBlock.rotate();

        if(testBlock.getLeftEdge() < 0) testBlock.setX(0);
        if(testBlock.getRightEdge() > gridColumns) testBlock.setX(gridColumns - testBlock.getWidth());
        if(testBlock.getBottomEdge() >= gridRows) testBlock.setY(gridRows - testBlock.getHeight());

        int[][] shape = testBlock.getShape();
        int h = testBlock.getHeight();
        int w = testBlock.getWidth();

        for (int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + testBlock.getX();
                    int y = row + testBlock.getY();
                    if (y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }


    private boolean checkBottom()
    {
        if(block.getBottomEdge() == gridRows)
        {
            return false;
        }

        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int col = 0; col < w; col++)
        {
            for (int row = h - 1; row >= 0; row--)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + block.getX();
                    int y = row + block.getY() + 1;
                    if (y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    private boolean checkLeft()
    {
        if (block.getLeftEdge() == 0) {return false;}

        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + block.getX() -1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkRight()
    {
        if( block.getRightEdge() == gridColumns ){return false;}

        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int row = 0; row < h; row++)
        {
            for (int col = w - 1; col >= 0; col--)
            {
                if(shape[row][col] != 0)
                {
                    int x = col + block.getX()  +1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if(background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    public int clearLines()
    {
        boolean lineFilled;
        int linesCleared = 0;
        for (int r = gridRows - 1; r >= 0; r--)
        {
            lineFilled = true;
            for (int c = 0; c < gridColumns; c++)
            {
                if(background[r][c] == null)
                {
                    lineFilled = false;
                    break;
                }
            }
            if(lineFilled)
            {
                linesCleared ++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);

                r++;

                repaint();
            }
        }
        return linesCleared;
    }

    private void clearLine(int r)
    {
        for(int i = 0; i < gridColumns; i++)
        {
            background[r][i] = null;
        }
    }

    private void shiftDown(int r)
    {
        for (int row = r; row > 0; row--)
        {
            if (gridColumns >= 0) System.arraycopy(background[row - 1], 0, background[row], 0, gridColumns);
        }
    }

    public void moveBlockToBackground()
    {
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        Color color = block.getColor();

        for (int r = 0; r < h; r++)
        {
            for (int c = 0; c < w; c++)
            {
                if(shape[r][c] == 1)
                {
                    background[r + yPos][c + xPos] = color;
                }
            }
        }
    }

    private void drawBlock(Graphics g)
    {
        int h = block.getHeight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape = block.getShape();
        for (int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                if(shape[row][col] == 1)
                {
                    int x = (col + block.getX()) * gridCellSize;
                    int y = (row + block.getY()) * gridCellSize;

                    drawGridSquare(g, c, x, y);
                }
            }
        }
    }

    private void drawBackground(Graphics g)
    {
        Color color;

        for(int r = 0; r < gridRows; r++)
        {
            for(int c = 0; c<gridColumns; c++)
            {
                color = background[r][c];

                if(color != null)
                {
                    int x = c * gridCellSize;
                    int y = r * gridCellSize;

                    drawGridSquare(g, color, x, y);
                }
            }
        }
    }

    private void drawGridSquare(Graphics g, Color color, int x, int y)
    {
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.black);
        g.drawRect(x, y, gridCellSize, gridCellSize);
    }

    private void drawGrid(Graphics g)
    {
        for(int y = 0; y < gridRows; y++)
            for(int x = 0; x < gridColumns; x++)
            {
                g.drawRect(x*gridCellSize, y*gridCellSize, gridCellSize, gridCellSize);
            }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        drawGrid(g);
        drawBackground(g);
        drawBlock(g);
    }
}
