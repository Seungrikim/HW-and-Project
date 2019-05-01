package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class GUI {
    private int WIDTH;
    private int HEIGHT;
    private TERenderer ter;
    private TETile[][] finalWorldFrame;
    private Point avatar;

    public GUI(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ter = ter;
        this.avatar = new Point();
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
    }

    public void menu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        basicGui();
        System.out.println("menu");
        StdDraw.show();
    }

    public String input() {
        Character input = null;
        InputSource userInput = new KeyboardInputSource();
        if (userInput.possibleNextInput()) {
            input = userInput.getNextKey();
        }
        switch(input) {
            case 'N':
                basicGui();
                System.out.println("switch");
                StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Seed:");
                StdDraw.show();
                //Engine engine = new Engine();
                //finalWorldFrame = engine.interactWithInputString(newWorld());
                return newWorld();
            case 'L':
                System.out.println("Load Game!");
                break;
            case 'Q':
                System.out.println("Quit Game!");
                break;
            /*case 'n':
                System.out.println("new Game!");
                break;
            case 'l':
                System.out.println("new Game!");
                break;
            case 'q':
                System.out.println("quit Game!");
                break;*/
            default:
                System.out.println("Wrong input");
        }
        return null;
    }

    /*public void move() {
        Character input = null;
        InputSource userInput = new KeyboardInputSource();
        if (userInput.possibleNextInput()) {
            input = userInput.getNextKey();
        }
        switch(input) {
            case 'w':
                finalWorldFrame[1][1] = Tileset.GRASS;
                finalWorldFrame[2][2] = Tileset.GRASS;
                ter.renderFrame(finalWorldFrame);
        }
    }*/

    private String newWorld() {
        char input = '.';
        String result = "";
        int x = 7;
        InputSource userInput = new KeyboardInputSource();
        while (input != 'S') {
            if (userInput.possibleNextInput()) {
                input = userInput.getNextKey();
            }
            result = result + input;
            basicGui();
            System.out.println("newWorld");
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Seed: ");
            StdDraw.text(WIDTH / 2 + x / 2, HEIGHT / 2 - 6, result);
            StdDraw.show();
            x += 1;
        }
        result = 'N' + result;
        System.out.println(result);
        return result;
    }

    private void basicGui() {
        StdDraw.clear(Color.PINK);
        StdDraw.enableDoubleBuffering();
        Font font = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.MAGENTA);
        StdDraw.text(WIDTH / 2, HEIGHT - 3, "CS61B: THE GAME");
        font = new Font("Monaco", Font.ITALIC, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.green);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game: Press(N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Load Game: Press(L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "Quit: Press(Q)");
    }

    /*private boolean userChoice() {
        switch () {

        }
    }*/
}
