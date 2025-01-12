import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeSet;
import java.util.Random;
import java.lang.Thread;



public class Game
{
    final int height = 15;
    final int lenght =80;
    final int birdX=20;
    int birdY=height/2;
    final String bird = "@";
    TreeSet<pipe> pipes= new TreeSet<>();
    Random rand = new Random();
    int passed=0;
    int difficulty = 400;


    public void handleInput()
    {
        while(true) {
            try {
                System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            birdY-=1;

        }
    }

    public void play()
    {
        initPipes();
        Thread inputThread = new Thread(this::handleInput);
        inputThread.setDaemon(true);  // Make this a daemon thread so it exits when the main game loop ends.
        inputThread.start();
        while(!crashed())
        {
            try {
                Thread.sleep(difficulty);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            draw();
            update();
        }
        end();
    }
    public void draw()
    {
        String[][] screen = new String[15][80];
        if(birdY<0)
            return;
        screen[birdY][birdX] = bird;
        for(var pipe : pipes)
        {
            if(pipe.floor)
            {
                int coll=pipe.x;
                if(coll>=80)
                    continue;
                int row = 14;
                for(int l = pipe.lenght;l>0;--l)
                {
                        screen[row--][coll]="█";
                }
            }
            else
            {
                int coll=pipe.x;
                if(coll>=80)
                    continue;
                int row=0;
                for(int l = pipe.lenght; l>0; --l)
                {
                        screen[row++][coll]="█";
                }
            }
        }
        for(int i=0; i<18; ++i) {
            System.out.print("\033[F\033[K");
        }

        for(int i=0; i<80; ++i)
            System.out.print("_");
        System.out.println();
        for(int i=0 ;i<15; ++i)
        {
            for(int j=0; j<80; ++j)
            {
                    System.out.print(screen[i][j]==null?" ": screen[i][j]);
            }
            System.out.println("|");
        }
        for(int i=0; i<80; ++i)
            System.out.print("-");
        System.out.println();
    }
    public void update()
    {
        ++birdY;
        pipe toRemove=null;
        for(var pipe : pipes)
        {
            if(pipe.x<15)
            {
                toRemove=pipe;
                continue;
            }
            --pipe.x;
        }
        if(toRemove!=null)
        {
            pipes.remove(toRemove);
            ++passed;
        }
        if(pipes.last().x < 68)
            pipes.add(new pipe(79, rand.nextInt(3,12), rand.nextBoolean()));
        if(passed>0 && passed%5==0)
            difficulty-=10;
    }
    public boolean crashed()
    {
        if (birdY>=height || birdY<0)
            return true;
        for(var pipe : pipes)
        {
            if(pipe.x==20)
            {
                if((pipe.floor && height-pipe.lenght<=birdY) || (!pipe.floor && pipe.lenght>birdY))
                    return true;
            }
        }
        return false;
    }
    public void initPipes()
    {
        int currX=40;
        for(int i=0; i<10; ++i,currX+=12)
        {
            int lenght = rand.nextInt(3,13);
            boolean side = rand.nextBoolean();
            pipes.add(new pipe(currX,lenght,side));
        }
    }

    static class pipe implements Comparable<pipe>
    {
        int x;
        int lenght;
        boolean floor;

        public pipe(int x, int lenght, boolean floor) {
            this.x = x;
            this.lenght = lenght;
            this.floor = floor;
        }

        @Override
        public int compareTo(pipe obj) {
            return this.x-obj.x;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            pipe pipe = (pipe) o;
            return x == pipe.x && lenght == pipe.lenght && floor == pipe.floor;
        }

    }
    public void end()
    {
        if(passed<10)
            System.out.println("😔Noob! You passed only "+ passed +  " pipes😔");
        else if(passed < 30)
            System.out.println("😊Not so bad! You passed " + passed + " pipes😊");
        else if(passed < 50)
            System.out.println("😎You are really good at it! You passed "+ passed +" pipes😎");
        else
            System.out.println("😮Legend! You passed " + passed+" pipes😮");
    }
}
