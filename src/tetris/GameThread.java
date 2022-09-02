package tetris;


public class GameThread extends Thread
{
    private GameArea ga;
    private GameForm gf;
    private  int score;
    private int level = 1;
    private int scorePerLevel = 5;

    private int pause = 1000;
    private int speedUpPerLevel = 50;
    public GameThread(GameArea ga, GameForm gf)
    {
        this.ga = ga;
        this.gf = gf;

    }

    @Override
    public void run()
    {
        while (true)
        {
            ga.spawnBlock();

            while(ga.moveBlockDown())
            {
                try {
                    ga.moveBlockDown();
                    Thread.sleep(pause);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ga.isBlockOutOfBounds())
            {
                System.out.println("GAME OVER");
                break;
            }
            ga.moveBlockToBackground();
            score += ga.clearLines();
            gf.updateScore(score);

            int lvl = score / scorePerLevel + 1;
            if( lvl > level)
            {
                level = lvl;
                gf.updateLevel(level);
                pause -= speedUpPerLevel;
            }
        }
    }
}
