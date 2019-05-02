package byow.Core;

import byow.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;

public class GUI {
    private int WIDTH;
    private int HEIGHT;
    private TERenderer ter;

    public GUI(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ter = ter;
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
        Character input;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                input = Character.toUpperCase(StdDraw.nextKeyTyped());
                System.out.println(input);
                switch (input) {
                    case 'N':
                        basicGui();
                        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Seed:");
                        StdDraw.show();
                        return newWorld();
                    case 'L':
                        return loadFile();
                    case 'Q':
                        System.exit(0);
                        break;
                    default:
                        basicGui();
                        StdDraw.setPenColor(Color.RED);
                        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 8, "Wrong Input");
                        StdDraw.show();
                        StdDraw.clear(Color.RED);
                }
            }
        }
    }
    private String newWorld() {
        char input = '.';
        String result = "";
        int x = 7;
        while (input != 'S') {
            if (StdDraw.hasNextKeyTyped()) {
                input = Character.toUpperCase(StdDraw.nextKeyTyped());
                result = result + input;
                basicGui();
                StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Seed: ");
                StdDraw.text(WIDTH / 2 + x / 2, HEIGHT / 2 - 6, result);
                StdDraw.show();
                x += 1;
            }
        }
        result = 'N' + result;
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

    //@Source from saveDemo
    private static String loadFile() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return null;
    }
}
