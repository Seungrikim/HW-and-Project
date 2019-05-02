package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.TileEngine.TERenderer;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Calendar;

public class World {
    private int WIDTH;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;
    private String save;
    private Point avatar;
    private String beforeLoad = "";
    private String afterLoad = "";
    private TERenderer ter = new TERenderer();
    private static TETile[][] TABLE;
    private HashMap<Integer, Room> roomContainer = new HashMap<>();
    private LinkedList<Room> treeList = new LinkedList<>();

    public World(TETile[][] table, int width, int height, String seed) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.TABLE = table;
        makeSeed(seed.toLowerCase());
        this.RANDOM = new Random(SEED);
        fillNothing();
        generatWorld();
        generateAvatar();
        generateLockdoor();
    }

    public void secondWorld(TETile[][] table, int width, int height, long seed) {
        ter.initialize(WIDTH, HEIGHT, 0, 0);
        fillNothing();
        generatWorld();
        generateAvatar();
        generateLockdoor();
        ter.renderFrame(TABLE);

    }

    public void replay() {
        if (!beforeLoad.equals("")) {
            moveReplay(beforeLoad);
        }
        if (!afterLoad.equals("")) {
            moveReplay(afterLoad);
        }
    }

    public void load() {
        if (!beforeLoad.equals("")) {
            moveLoad(beforeLoad);
        }
        if (!afterLoad.equals("")) {
            moveLoad(afterLoad);
        }
    }

    private void makeSeed(String input) {
        String seed = "";
        String load = "";
        int index = 0;
        if (input.charAt(0) == 'n') {
            while (input.charAt(index) != 's') {
                index += 1;
            }
            save = input.substring(0, index) + 's';
            seed = input.substring(1, index);
            SEED = Long.parseLong(seed);
            seed = "";
            index += 1;
            for (int i = index; i < input.length(); i++) {
                seed = seed + input.charAt(i);
            }
            beforeLoad = seed;
        } else {
            load = loadFile();
            while (load.charAt(index) != 's') {
                index += 1;
            }
            save = load.substring(0, index) + 's';
            seed = load.substring(1, index);
            SEED = Long.parseLong(seed);
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
        for (int i = 3; i < WIDTH - 3; i += 1) {
            int numberOfrectangle = RANDOM.nextInt(2);
            for (int j = 0; j < numberOfrectangle; j++) {
                int y = RANDOM.nextInt(HEIGHT - 10) + 2;
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
        return (tile[xStart][yStart].equals(Tileset.NOTHING)
                && tile[xEnd][yEnd].equals(Tileset.NOTHING)
                && tile[xEnd][yStart].equals(Tileset.NOTHING)
                && tile[xStart][yEnd].equals(Tileset.NOTHING)
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
                if (y - 1 >= 0 && TABLE[x][y - 1].equals(Tileset.NOTHING)) {
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

    public TETile[][] table() {
        return TABLE;
    }

    public void generateLockdoor() {
        int x = RANDOM.nextInt(WIDTH - 2) + 1;
        int y = RANDOM.nextInt(HEIGHT - 2) + 1;
        while (!(TABLE[x][y].equals(Tileset.WALL) && lockDoorchekcer(x, y))) {
            x = RANDOM.nextInt(WIDTH);
            y = RANDOM.nextInt(HEIGHT);
        }
        TABLE[x][y] = Tileset.LOCKED_DOOR;
    }

    private boolean lockDoorchekcer(int x, int y) {
        return TABLE[x + 1][y].equals(Tileset.FLOOR) || TABLE[x - 1][y].equals(Tileset.FLOOR)
                || TABLE[x][y + 1].equals(Tileset.FLOOR) || TABLE[x][y - 1].equals(Tileset.FLOOR);
    }

    public TETile[][] move() {
        Character input;
        while (true) {
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            hud(x, y);
            clock();
            StdDraw.enableDoubleBuffering();
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                input = Character.toUpperCase(StdDraw.nextKeyTyped());
                switch (input) {
                    case 'W':
                        Point up = new Point(avatar.x, avatar.y + 1);
                        movingAvatar(up);
                        save += "W";
                        if (TABLE[avatar.x][avatar.y + 1].equals(Tileset.LOCKED_DOOR)) {
                            TABLE[avatar.x][avatar.y + 1] = Tileset.UNLOCKED_DOOR;
                            //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                            //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                            return TABLE;
                        }
                        return TABLE;
                    case 'S':
                        Point down = new Point(avatar.x, avatar.y - 1);
                        movingAvatar(down);
                        if (TABLE[avatar.x][avatar.y - 1].equals(Tileset.LOCKED_DOOR)) {
                            TABLE[avatar.x][avatar.y - 1] = Tileset.UNLOCKED_DOOR;
                            //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                            ///secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                            return TABLE;
                        }
                        save += "S";
                        return TABLE;
                    case 'A':
                        Point left = new Point(avatar.x - 1, avatar.y);
                        movingAvatar(left);
                        if (TABLE[avatar.x - 1][avatar.y].equals(Tileset.LOCKED_DOOR)) {
                            TABLE[avatar.x - 1][avatar.y] = Tileset.UNLOCKED_DOOR;
                            //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                            //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                            return TABLE;
                        }
                        save += "A";
                        return TABLE;
                    case 'D':
                        Point right = new Point(avatar.x + 1, avatar.y);
                        movingAvatar(right);
                        if (TABLE[avatar.x + 1][avatar.y].equals(Tileset.LOCKED_DOOR)) {
                            TABLE[avatar.x + 1][avatar.y] = Tileset.UNLOCKED_DOOR;
                            //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                            //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                            return TABLE;
                        }
                        save += "D";
                        return TABLE;
                    case ':':
                        while (true) {
                            if (StdDraw.hasNextKeyTyped()) {
                                if (Character.toUpperCase(StdDraw.nextKeyTyped()) == 'Q') {
                                    saveFile(save);
                                    System.exit(0);
                                    return TABLE;
                                }
                            }
                        }
                    default:
                        return TABLE;
                }
            }
        }
    }

    private long randomSeed() {

        return RANDOM.nextLong();
    }

    private void hud(int x, int y) {
        if (x > 0 && x < WIDTH && y > 0 && y < HEIGHT) {
            if (TABLE[x][y].equals(Tileset.NOTHING)) {
                ter.renderFrame(TABLE);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(5, HEIGHT - 2, "Nothing");
            } else if (TABLE[x][y].equals(Tileset.WALL)) {
                ter.renderFrame(TABLE);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(5, HEIGHT - 2, "Wall");
            } else if (TABLE[x][y].equals(Tileset.FLOOR)) {
                ter.renderFrame(TABLE);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(5, HEIGHT - 2, "Floor");
            } else if (TABLE[x][y].equals(Tileset.AVATAR)) {
                ter.renderFrame(TABLE);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(5, HEIGHT - 2, "Avatar");
            } else if (TABLE[x][y].equals(Tileset.LOCKED_DOOR)) {
                ter.renderFrame(TABLE);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(5, HEIGHT - 2, "Locked Door");
            }
        }
    }

    public void clock() {
        String timeStamp
                = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.text(5, HEIGHT - 1, timeStamp);
    }

    public void moveReplay(String user) {
        ter.initialize(WIDTH, HEIGHT, 0, 0);
        for (int i = 0; i < user.length(); i++) {
            StdDraw.pause(170);
            switch (user.charAt(i)) {
                case 'w':
                    Point up = new Point(avatar.x, avatar.y + 1);
                    movingAvatar(up);
                    if (TABLE[avatar.x][avatar.y + 1].equals(Tileset.LOCKED_DOOR)) {
                        TABLE[avatar.x][avatar.y + 1] = Tileset.UNLOCKED_DOOR;
                        //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                        //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                    }
                    ter.renderFrame(TABLE);
                    save += "w";
                    break;
                case 's':
                    Point down = new Point(avatar.x, avatar.y - 1);
                    movingAvatar(down);
                    if (TABLE[avatar.x][avatar.y - 1].equals(Tileset.LOCKED_DOOR)) {
                        TABLE[avatar.x][avatar.y - 1] = Tileset.UNLOCKED_DOOR;
                        //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                        //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                    }
                    ter.renderFrame(TABLE);
                    save += "s";
                    break;
                case 'a':
                    Point left = new Point(avatar.x - 1, avatar.y);
                    movingAvatar(left);
                    if (TABLE[avatar.x - 1][avatar.y].equals(Tileset.LOCKED_DOOR)) {
                        TABLE[avatar.x - 1][avatar.y] = Tileset.UNLOCKED_DOOR;
                        //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                        //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                    }
                    ter.renderFrame(TABLE);
                    save += "a";
                    break;
                case 'd':
                    Point right = new Point(avatar.x + 1, avatar.y);
                    movingAvatar(right);
                    if (TABLE[avatar.x + 1][avatar.y].equals(Tileset.LOCKED_DOOR)) {
                        TABLE[avatar.x + 1][avatar.y] = Tileset.UNLOCKED_DOOR;
                        //TETile[][] newtable = new TETile[WIDTH][HEIGHT];
                        //secondWorld(newtable, WIDTH, HEIGHT, randomSeed());
                    }
                    ter.renderFrame(TABLE);
                    save += "d";
                    break;
                case ':':
                    saveFile(save);
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public void moveLoad(String user) {
        for (int i = 0; i < user.length(); i++) {
            switch (user.charAt(i)) {
                case 'w':
                    Point up = new Point(avatar.x, avatar.y + 1);
                    movingAvatar(up);
                    save += "w";
                    break;
                case 's':
                    Point down = new Point(avatar.x, avatar.y - 1);
                    movingAvatar(down);
                    save += "s";
                    break;
                case 'a':
                    Point left = new Point(avatar.x - 1, avatar.y);
                    movingAvatar(left);
                    save += "a";
                    break;
                case 'd':
                    Point right = new Point(avatar.x + 1, avatar.y);
                    movingAvatar(right);
                    save += "d";
                    break;
                case ':':
                    saveFile(save);
                    break;
                default:
            }
        }
    }



    private void movingAvatar(Point p) {
        if (TABLE[p.x][p.y].equals(Tileset.FLOOR)) {
                //|| TABLE[p.x][p.y].equals(Tileset.UNLOCKED_DOOR)) {
            TABLE[avatar.x][avatar.y] = Tileset.FLOOR;
            TABLE[p.x][p.y] = Tileset.AVATAR;
            avatar.x = p.x;
            avatar.y = p.y;
        }
    }

    //@Source from saveDemo
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

