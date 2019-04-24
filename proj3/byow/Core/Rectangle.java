package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Rectangle {
    private int WIDTH;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;
    private TETile[][] TABLE;
    private HashMap<Point, Room> roomContainer = new HashMap<>();
    private LinkedList<Room> treeList = new LinkedList<>();

    public Rectangle(TETile[][] table, int width, int height, long seed) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.SEED = seed;
        this.RANDOM = new Random(SEED);
        this.TABLE = table;
        TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        fillNothing(TABLE);
        generatWorld(TABLE);
        //ter.renderFrame(TABLE);
    }

    public void fillNothing(TETile[][] tile) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tile[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void generatWorld(TETile[][] tile) {
        int count = 0;
        for (int i = 0; i < WIDTH - 8; i += 3) {
            int numberOfrectangle = RANDOM.nextInt(2);
            for (int j = 0; j < numberOfrectangle; j++) {
                int y = RANDOM.nextInt(HEIGHT - 8);
                int yEnd = y + 2 + RANDOM.nextInt(6);
                int xEnd = i + 2 + RANDOM.nextInt(6);
                if (boundary(tile, i, y, xEnd, yEnd)) {
                    count += 1;
                    Point newPoint =  new Point(i, y);
                    Room newRoom = new Room(newPoint, xEnd - i, yEnd - y);
                    roomContainer.put(newRoom.center(), newRoom);
                    treeList.addLast(newRoom);
                    generateRoom(tile, i, y, xEnd, yEnd);
                    if (count > 1) {
                        hallwayMaker(tile, treeList.removeFirst(), treeList.get(0));
                    }
                }
            }
        }
    }

    private void generateRoom(TETile[][] tile, int xStart, int yStart, int xEnd, int yEnd) {
        for (int i = yStart; i <= yEnd; i++) {
            for (int j = xStart; j <= xEnd; j++) {
                if (i == yStart || i == yEnd || j == xStart || j == xEnd) {
                    tile[j][i] = Tileset.WALL;
                } else {
                    tile[j][i] = Tileset.FLOOR;
                }
            }
        }
    }

    private boolean boundary(TETile[][] tile, int xStart, int yStart, int xEnd, int yEnd) {
        return (tile[xStart][yStart] == Tileset.NOTHING && tile[xEnd][yEnd] == Tileset.NOTHING
                && tile[xEnd][yStart] == Tileset.NOTHING && tile[xStart][yEnd] == Tileset.NOTHING);
    }

    private void hallwayMaker(TETile[][] tile, Room r1, Room r2) {
        /*if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
            while (p1.x != p2.x) {
                tile[p1.x][p1.y] = Tileset.FLOOR;
                p1.x += 1;
            }
            while (p1.)
        } else */
        int x = r1.center().x;
        int y = r1.center().y;
        if (y > r2.center().y) {
            while (y != r2.center().y) {
                tile[r1.center().x][y] = Tileset.FLOOR;
                if (tile[r1.center().x + 1][y] == Tileset.NOTHING) {
                    tile[r1.center().x + 1][y] = Tileset.WALL;
                }
                if (tile[r1.center().x - 1][y] == Tileset.NOTHING) {
                    tile[r1.center().x - 1][y] = Tileset.WALL;
                }
                y -= 1;
            }
            tile[r1.center().x - 1][y] = Tileset.WALL;
            while (x != r2.center().x + 1) {
                tile[x][y] = Tileset.FLOOR;
                if (tile[x][y + 1] == Tileset.NOTHING) {
                    tile[x][y + 1] = Tileset.WALL;
                }
                if (tile[x][y - 1] == Tileset.NOTHING) {
                    tile[x][y - 1] = Tileset.WALL;
                }
                x += 1;
            }
        } else {
            while (y != r2.center().y + 1) {
                tile[r1.center().x][y] = Tileset.FLOOR;
                if (tile[r1.center().x + 1][y] == Tileset.NOTHING) {
                    tile[r1.center().x + 1][y] = Tileset.WALL;
                }
                if (tile[r1.center().x - 1][y] == Tileset.NOTHING) {
                    tile[r1.center().x - 1][y] = Tileset.WALL;
                }

                y += 1;
            }
            tile[r1.center().x - 1][y] = Tileset.WALL;
            while (x != r2.center().x + 1) {
                tile[x][y] = Tileset.FLOOR;
                if (tile[x][y + 1] == Tileset.NOTHING) {
                    tile[x][y + 1] = Tileset.WALL;
                }
                if (tile[x][y - 1] == Tileset.NOTHING) {
                    tile[x][y - 1] = Tileset.WALL;
                }
                x += 1;
            }
        }
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

