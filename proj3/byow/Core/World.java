package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.SaveDemo.Editor;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class World {
    private int WIDTH;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;
    private String save;
    public Point avatar;
    private String beforeLoad = "";
    private String afterLoad = "";
    private TERenderer ter;
    private TETile[][] TABLE;
    private HashMap<Integer, Room> roomContainer = new HashMap<>();
    private LinkedList<Room> treeList = new LinkedList<>();

    public World(TETile[][] table, int width, int height, String seed, TERenderer ter) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.TABLE = table;
        this.ter = ter;
        makeSeed(seed);
        this.RANDOM = new Random(SEED);
        //this.SEED = makeSeed(seed);
        //this.save = makeSave(seed);
        ter.initialize(WIDTH, HEIGHT);
        fillNothing();
        generatWorld();
        generateAvatar();
        generateLockdoor();
        if (!beforeLoad.equals("")) {
            moveLoad(beforeLoad);
        }
        if (!afterLoad.equals("")) {
            moveLoad(afterLoad);
        }
        ter.renderFrame(TABLE);
    }

    private void makeSeed(String input) {
        String seed = "";
        String load = "";
        int index = 0;
        if (input.charAt(0) == 'N') {
            while (input.charAt(index) != 'S') {
                index += 1;
            }
            save = input.substring(0,index) + 'S';
            seed = input.substring(1,index);
            SEED = Long.parseLong(seed);
            //System.out.println(SEED);
            //System.out.println(save);
            seed = "";
            index += 1;
            for (int i = index; i < input.length(); i++) {
                seed = seed + input.charAt(i);
            }
            beforeLoad = seed;
        } else {
            load = loadFile();
            while (load.charAt(index) != 'S') {
                index += 1;
            }
            save = load.substring(0, index) + 'S';
            seed = load.substring(1,index);
            SEED = Long.parseLong(seed);
            //System.out.println(SEED);
            //System.out.println(save);
            seed = "";
            index += 1;
            for (int i = index; i < load.length(); i++) {
                seed = seed + load.charAt(i);
            }
            beforeLoad = seed;
            afterLoad = input.substring(1, input.length());
        }
    }

    public void fillNothing() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                TABLE[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void generatWorld() {
        int count = 0;
        for (int i = 5; i < WIDTH - 5; i += 1) {
            int numberOfrectangle = RANDOM.nextInt(2);
            for (int j = 0; j < numberOfrectangle; j++) {
                int y = RANDOM.nextInt(HEIGHT - 7) + 5;
                int yEnd = y + 3 + RANDOM.nextInt(6);
                int xEnd = i + 3 + RANDOM.nextInt(6);
                if (boundary(TABLE, i, y, xEnd, yEnd)) {
                    count += 1;
                    Point newPoint =  new Point(i, y);
                    Room newRoom = new Room(newPoint, xEnd - i, yEnd - y);
                    roomContainer.put(count, newRoom);
                    treeList.addLast(newRoom);
                    generateRoom(i, y, xEnd, yEnd);
                    if (treeList.size() > 1) {
                        hallwayMaker(treeList.removeFirst(), treeList.get(0));
                    }
                }
            }
        }
    }

    private void generateRoom(int xStart, int yStart, int xEnd, int yEnd) {
        for (int i = yStart; i <= yEnd; i++) {
            for (int j = xStart; j <= xEnd; j++) {
                if (i == yStart || i == yEnd || j == xStart || j == xEnd) {
                    TABLE[j][i] = Tileset.WALL;
                } else {
                    TABLE[j][i] = Tileset.FLOOR;
                }
            }
        }
    }

    private boolean boundary(TETile[][] tile, int xStart, int yStart, int xEnd, int yEnd) {
        if (!(xEnd < WIDTH && yEnd < HEIGHT)) {
            return false;
        }
        return (tile[xStart][yStart].equals(Tileset.NOTHING) && tile[xEnd][yEnd].equals(Tileset.NOTHING)
                && tile[xEnd][yStart].equals(Tileset.NOTHING) && tile[xStart][yEnd].equals(Tileset.NOTHING)
                && tile[(xStart + xEnd) / 2][yEnd].equals(Tileset.NOTHING)
                && tile[(xStart + xEnd) / 2][yStart].equals(Tileset.NOTHING)
                && tile[xEnd][(yStart + yEnd) / 2].equals(Tileset.NOTHING)
                && tile[xStart][(yStart + yEnd) / 2].equals(Tileset.NOTHING));
    }

    private void hallwayMaker(Room r1, Room r2) {
        int x = r1.center().x;
        int y = r1.center().y;
        if (y > r2.center().y) {
            while (y >= r2.center().y) {
                TABLE[r1.center().x][y] = Tileset.FLOOR;
                if (TABLE[r1.center().x + 1][y].equals(Tileset.NOTHING)) {
                    TABLE[r1.center().x + 1][y] = Tileset.WALL;
                }
                if (TABLE[r1.center().x - 1][y].equals(Tileset.NOTHING)) {
                    TABLE[r1.center().x - 1][y] = Tileset.WALL;
                }
                y -= 1;
            }
            if (TABLE[r1.center().x - 1][y].equals(Tileset.NOTHING)) {
                TABLE[r1.center().x - 1][y] = Tileset.WALL;
            }
            if (y - 1 >= 0 && TABLE[r1.center().x - 1][y - 1].equals(Tileset.NOTHING)) {
                TABLE[r1.center().x - 1][y - 1] = Tileset.WALL;
            }
            if (TABLE[r1.center().x][y].equals(Tileset.NOTHING)) {
                TABLE[r1.center().x][y] = Tileset.WALL;
                TABLE[r1.center().x + 1][y] = Tileset.WALL;
            }
            while (x <= r2.center().x) {
                TABLE[x][y] = Tileset.FLOOR;
                if (y + 1 <= HEIGHT && TABLE[x][y + 1].equals(Tileset.NOTHING)) {
                    TABLE[x][y + 1] = Tileset.WALL;
                }
                if ( y - 1 >= 0 && TABLE[x][y - 1].equals(Tileset.NOTHING)) {
                    TABLE[x][y - 1] = Tileset.WALL;
                }
                x += 1;
            }
        } else {
            while (y <= r2.center().y) {
                TABLE[r1.center().x][y] = Tileset.FLOOR;
                if (x + 1 <= WIDTH && TABLE[r1.center().x + 1][y].equals(Tileset.NOTHING)) {
                    TABLE[r1.center().x + 1][y] = Tileset.WALL;
                }
                if (x - 1 >= 0 && TABLE[r1.center().x - 1][y].equals(Tileset.NOTHING)) {
                    TABLE[r1.center().x - 1][y] = Tileset.WALL;
                }
                y += 1;
            }
            if (TABLE[r1.center().x - 1][y].equals(Tileset.NOTHING)) {
                TABLE[r1.center().x - 1][y] = Tileset.WALL;
            }
            if (y + 1 <= HEIGHT && TABLE[r1.center().x - 1][y + 1].equals(Tileset.NOTHING)) {
                TABLE[r1.center().x - 1][y + 1] = Tileset.WALL;
            }
            if (TABLE[r1.center().x][y].equals(Tileset.NOTHING)) {
                TABLE[r1.center().x][y] = Tileset.WALL;
                TABLE[r1.center().x + 1][y] = Tileset.WALL;
            }
            while (x <= r2.center().x) {
                TABLE[x][y] = Tileset.FLOOR;
                if (TABLE[x][y + 1].equals(Tileset.NOTHING)) {
                    TABLE[x][y + 1] = Tileset.WALL;
                }
                if (TABLE[x][y - 1].equals(Tileset.NOTHING)) {
                    TABLE[x][y - 1] = Tileset.WALL;
                }
                x += 1;
            }
        }
    }

    public void generateAvatar() {
        int roomChoice = RANDOM.nextInt(WIDTH);
        while (!roomContainer.containsKey(roomChoice)) {
            roomChoice = RANDOM.nextInt(WIDTH);
        }
        Room newRoom = roomContainer.get(roomChoice);
        TABLE[newRoom.center().x][newRoom.center().y] = Tileset.AVATAR;
        avatar = new Point(newRoom.center().x, newRoom.center().y);
    }

    public Point avatarPoint() {
        return avatar;
    }

    public void generateLockdoor() {
        int roomChoice = RANDOM.nextInt(WIDTH);
        while (!roomContainer.containsKey(roomChoice)) {
            roomChoice = RANDOM.nextInt(WIDTH);
        }
        Room newRoom = roomContainer.get(roomChoice);
        int direction = RANDOM.nextInt(4);
        switch (direction) {
            case 0:
                TABLE[newRoom.center().x + 1 - (newRoom.w + 1) / 2][newRoom.center().y] = Tileset.LOCKED_DOOR;
                break;
            case 1:
                TABLE[newRoom.center().x + (newRoom.w + 1) / 2][newRoom.center().y] = Tileset.LOCKED_DOOR;
                break;
            case 2:
                TABLE[newRoom.center().x][newRoom.center().y - (newRoom.h + 1) / 2] = Tileset.LOCKED_DOOR;
                break;
            case 3:
                TABLE[newRoom.center().x][newRoom.center().y + (newRoom.h + 1)/ 2] = Tileset.LOCKED_DOOR;
                break;
            default:
                break;
        }
    }

    public Boolean move(boolean userTurn) {
        Character input = null;
        InputSource userInput = new KeyboardInputSource();
        if (userInput.possibleNextInput()) {
            input = userInput.getNextKey();
        }
        switch (input) {
            case 'W':
                System.out.println("up");
                Point up = new Point(avatar.x, avatar.y + 1);
                movingAvatar(up);
                ter.renderFrame(TABLE);
                save += "W";
                return true;
            case 'S':
                System.out.println("down");
                Point down = new Point(avatar.x, avatar.y - 1);
                movingAvatar(down);
                ter.renderFrame(TABLE);
                save += "S";
                return true;
            case 'A':
                System.out.println("left");
                Point left = new Point(avatar.x - 1, avatar.y);
                movingAvatar(left);
                ter.renderFrame(TABLE);
                save += "A";
                return true;
            case 'D':
                System.out.println("right");
                Point right = new Point(avatar.x + 1, avatar.y);
                movingAvatar(right);
                ter.renderFrame(TABLE);
                save += "D";
                return true;
            case 'Q':
                System.out.println(save);
                saveFile(save);
                System.out.println("quit");
                System.exit(0);
                return false;
            default:
                System.out.println("default");
        }
        return true;
    }

    public void moveLoad(String user) {
        //Character input = null;
        //System.out.println("moveLoad");
        for (int i = 0; i < user.length(); i++) {
            //System.out.println("moveLoad for loop");
            StdDraw.pause(500);
            switch (user.charAt(i)) {
                case 'W':
                    //System.out.println("up");
                    Point up = new Point(avatar.x, avatar.y + 1);
                    movingAvatar(up);
                    ter.renderFrame(TABLE);
                    save += "W";
                    break;
                case 'S':
                    //System.out.println("down");
                    Point down = new Point(avatar.x, avatar.y - 1);
                    movingAvatar(down);
                    ter.renderFrame(TABLE);
                    save += "S";
                    break;
                case 'A':
                    //System.out.println("left");
                    Point left = new Point(avatar.x - 1, avatar.y);
                    movingAvatar(left);
                    ter.renderFrame(TABLE);
                    save += "A";
                    break;
                case 'D':
                    //System.out.println("right");
                    Point right = new Point(avatar.x + 1, avatar.y);
                    movingAvatar(right);
                    ter.renderFrame(TABLE);
                    save += "D";
                    break;
                case ':':
                    //System.out.println(save);
                    saveFile(save);
                    //System.out.println("quit");
                    System.exit(0);
                    break;
                default:
                    System.out.println("default");
            }
        }
    }

    private void movingAvatar(Point p) {
        if (TABLE[p.x][p.y].equals(Tileset.FLOOR)) {
            TABLE[avatar.x][avatar.y] = Tileset.FLOOR;
            TABLE[p.x][p.y] = Tileset.AVATAR;
            avatar.x = p.x;
            avatar.y = p.y;
        }
    }

    private static void saveFile(String s) {
        File file = new File("./save_data.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(s);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

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

    private class Room {
        private Point p;
        private int w;
        private int h;

        private Room(Point p, int w, int h) {
            this.w = w;
            this.h = h;
            this.p = p;
        }
        private Point center() {
            Point centerPoint = new Point(p.x + w / 2, p.y + h / 2);
            return centerPoint;
        }
    }

    private class Point {
        private int x;
        private int y;
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}

