package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside getMapRaster(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for getMapRaster in Rasterer.java.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        //System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        //System.out.println(requestParams);
        Map<String, Object> results = new HashMap<>();
        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                ///+ "your browser.");

        double lrlon = requestParams.get("lrlon");
        double ullon = requestParams.get("ullon");
        double ullat = requestParams.get("ullat");
        double lrlat = requestParams.get("lrlat");
        double width = requestParams.get("w");

        int depth = computeDepth(lrlon, ullon, lrlat, ullat, width);
        double londpp = computeLonDPP(depth);
        double latdpp = computeLatDPP(depth);
        int ullonX = computeX(ullon, londpp, depth);
        int lrlonX = computeX(lrlon, londpp, depth);
        int lrlatY = computeY(ullat, latdpp, depth);
        int ullatY = computeY(lrlat, latdpp, depth);
        /*System.out.println("depth: " + depth);
        System.out.println("londpp: " + londpp);
        System.out.println("latdpp: " + latdpp);
        System.out.println("starterX: " + ullonX);
        System.out.println("endX: " + lrlonX);
        System.out.println("starterY: " + lrlatY);
        System.out.println("endY: " + ullatY);*/

        String[][] render_grid = new String[ullatY - lrlatY + 1][lrlonX - ullonX + 1];
        render_grid = gridMaker(render_grid, ullonX, lrlonX, lrlatY, ullatY, depth);
        double raster_ul_lon = computeUllon(ullonX, londpp);
        double raster_lr_lon = computeLrlon(lrlonX, londpp);
        double raster_ul_lat = computeUllat(lrlatY, latdpp);
        double raster_lr_lat = computeLrlat(ullatY, latdpp);
        /*System.out.println("rater_ul_lon: " + raster_ul_lon);
        System.out.println("rater_lr_lon: " + raster_lr_lon);
        System.out.println("rater_ul_lat: " + raster_ul_lat);
        System.out.println("rater_lr_lat: " + raster_lr_lat);
        System.out.println(gridPrinter(render_grid, ullonX, lrlonX, lrlatY, ullatY, depth));*/

        boolean query_success = true;
        if (outOfBound(ullon, ullat, lrlon, lrlat)) {
            System.out.println("outOfbound");
            return queryFail();
        } else if (soZoomed(ullon, ullat, lrlon, lrlat)) {
            System.out.println("soZoomed");
            return hugeQuery();
        }

        //need to make simpler
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);
        results.put("render_grid", render_grid);
        return results;
    }

    /*Compute the depth of th nodes of the rastered image*/
    private int computeDepth(double lrlon, double ullon, double lrlat, double ullat, double width) {
        double depth;
        depth = (int) (Math.log(((ROOT_LRLON - ROOT_ULLON) / TILE_SIZE) /
                ((lrlon - ullon) / width)) / Math.log(2));
        /*System.out.println(depth_lon);
        depth_lat = (Math.log(((ROOT_ULLAT - ROOT_LRLAT) / TILE_SIZE) /
                ((ullat - lrlat) / width)) / Math.log(2));
        System.out.println(depth_lat);*/
        if (computeLonDPP(depth) > (lrlon - ullon) / width) {
            depth += 1;
        }
        if (depth >= 7) {
            return 7;
        } else {
            return (int) depth;
        }
    }

    /*Compute LonDPP based on the depth*/
    private double computeLonDPP(double depth) {
        double LonDPP;
        LonDPP = ((ROOT_LRLON - ROOT_ULLON) / TILE_SIZE) / Math.pow(2, depth);
        return LonDPP;
    }

    private double computeLatDPP(double depth) {
        double LatDPP;
        LatDPP = ((ROOT_ULLAT - ROOT_LRLAT) / TILE_SIZE) / Math.pow(2, depth);
        return LatDPP;
    }

    private boolean outOfBound(double ullon, double ullat, double lrlon, double lrlat) {
        return (lrlon <= ROOT_ULLON || ullon >= ROOT_LRLON
                || lrlat >= ROOT_ULLAT || ullat <= ROOT_LRLAT);
    }

    private boolean soZoomed(double ullon, double ullat, double lrlon, double lrlat) {
        return (ullon <= ROOT_ULLON && lrlon >= ROOT_LRLON
                && ullat >= ROOT_ULLAT && lrlat <= ROOT_LRLAT);
    }

    private Map<String, Object> hugeQuery() {
        Map<String, Object> results = new HashMap<>();
        String[] grid = new String[]{"d0_x0_y0.png"};
        results.put("render_grid", grid);
        results.put("raster_ul_lon", ROOT_ULLON);
        results.put("raster_ul_lat", ROOT_ULLAT);
        results.put("raster_lr_lon", ROOT_LRLON);
        results.put("raster_lr_lat", ROOT_LRLAT);
        results.put("depth", 0);
        results.put("query_success", true);
        return results;
    }

    private int computeX(double point, double londpp, int depth) {
        int x = 0;
        if (point < ROOT_ULLON) {
            return x;
        } else if (point > ROOT_LRLON) {
            x = ((int) Math.pow(2, depth)) - 1;
            return x;
        }
        while(!boundaryX(point, londpp, x)) {
            x += 1;
        }
        return x;
    }

    private int computeY(double point, double latdpp, int depth) {
        int y = 0;
        if (point > ROOT_ULLAT) {
            return y;
        } else if (point < ROOT_LRLAT) {
            y = ((int) Math.pow(2, depth)) - 1;
            return y;
        }
        while(!boundaryY(point, latdpp, y)) {
            y += 1;
        }
        return y;
    }

    private double computeUllon(int boundary, double londpp) {
        return ROOT_ULLON + (londpp * TILE_SIZE) * boundary;
    }

    private double computeLrlon(int boundary, double londpp) {
        return ROOT_ULLON + (londpp * TILE_SIZE) * (boundary + 1);
    }

    private double computeUllat(int boundary, double latdpp) {
        return ROOT_ULLAT - (latdpp * TILE_SIZE) * boundary;
    }

    private double computeLrlat(int boundary, double latdpp) {
        return ROOT_ULLAT - (latdpp * TILE_SIZE) * (boundary + 1);
    }

    private boolean boundaryX(double point, double londpp, int x) {
        return ROOT_ULLON + (londpp * TILE_SIZE) * x <= point &&
                ROOT_ULLON + ((londpp * TILE_SIZE) * (x + 1)) >= point;
    }

    private boolean boundaryY(double point, double latdpp, int y) {
        return ROOT_ULLAT - (latdpp * TILE_SIZE) * y >= point &&
                ROOT_ULLAT - ((latdpp * TILE_SIZE) * (y + 1)) <= point;
    }

    private String[][] gridMaker(String[][] grid, int xStart, int xEnd, int yStart, int yEnd, int depth) {
        int raw = 0, column = 0;
        for (int i = yStart; i <= yEnd; i++) {
            for (int j = xStart; j <= xEnd; j++) {
                String img = "d" + depth + "_x" + j + "_y" + i + ".png";
                grid[raw][column] = img;
                column += 1;
            }
            column = 0;
            raw += 1;
        }
        return grid;
    }

    private String gridPrinter(String[][] grid, int xStart, int xEnd, int yStart, int yEnd, int depth) {
        int raw = 0, column = 0;
        String img = "render_grid=[";
        for (int i = yStart; i <= yEnd; i++) {
            img = img + "[";
            for (int j = xStart; j <= xEnd; j++) {
                img = img + "[" + grid[raw][column] + "]";
                column += 1;
            }
            img = img + "], ";
            column = 0;
            raw += 1;
        }
        img = img + "]";
        return img;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }

    public static void main(String[] args) {
        RasterAPIHandler rasterer = new RasterAPIHandler();
        HashMap<String, Double> a = new HashMap<>();
        //-122.29980468 and -122.21191406 and between latitudes 37.82280243 and 37.89219554.
        //video:-122.241632, -122.24053, w = 892.0, h = 875.0, ullat 37.87655, lrlat 37.87548
        //lrlon=-122.24053369025242, ullon=-122.24163047377972, w=892.0,
        // h=875.0, ullat=37.87655856892288, lrlat=37.87548268822065

        //raster_ul_lon=-122.24212646484375, depth=7, raster_lr_lon=-122.24006652832031,
        //raster_lr_lat=37.87538940251607,
        //render_grid=[[d7_x84_y28.png, d7_x85_y28.png, d7_x86_y28.png],
        // [d7_x84_y29.png, d7_x85_y29.png, d7_x86_y29.png],
        // [d7_x84_y30.png, d7_x85_y30.png, d7_x86_y30.png]],
        //raster_ul_lat=37.87701580361881, query_success=true}


        //raster_ul_lon=-122.2998046875, depth=2, raster_lr_lon=-122.2119140625,
        //raster_lr_lat=37.82280243352756,
        //render_grid=[[d2_x0_y1.png, d2_x1_y1.png, d2_x2_y1.png, d2_x3_y1.png],
        // [d2_x0_y2.png, d2_x1_y2.png, d2_x2_y2.png, d2_x3_y2.png],
        // [d2_x0_y3.png, d2_x1_y3.png, d2_x2_y3.png, d2_x3_y3.png]],
        //raster_ul_lat=37.87484726881516, query_success=true}

        //-122.2998046875, -122.2119140625, 37.892195547244356, 37.82280243352756
        a.put("ullon", -122.30410170759153);
        a.put("lrlon", -122.2104604264636);
        a.put("w", 1091.0);
        a.put("h", 566.0);
        a.put("ullat", 37.870213571328854);
        a.put("lrlat", 37.8318576119893);
        System.out.println("Test Start");
        rasterer.processRequest(a, null);
        System.out.println("Test Done");
    }
}
