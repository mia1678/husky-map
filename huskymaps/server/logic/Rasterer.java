package huskymaps.server.logic;

import huskymaps.params.RasterRequest;
import huskymaps.params.RasterResult;

import java.util.Objects;

import static huskymaps.Constants.MIN_X_TILE_AT_DEPTH;
import static huskymaps.Constants.MIN_Y_TILE_AT_DEPTH;
import static huskymaps.Constants.MIN_ZOOM_LEVEL;
import static huskymaps.Constants.LAT_PER_TILE;
import static huskymaps.Constants.LON_PER_TILE;
import static huskymaps.Constants.ROOT_ULLAT;
import static huskymaps.Constants.ROOT_ULLON;


/** Application logic for the RasterAPIHandler. */
public class Rasterer {

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
     * @param request RasterRequest
     * @return RasterResult
     */
    public static RasterResult rasterizeMap(RasterRequest request) {
        double ullat = request.ullat;  // upper edge    latitudes
        double lrlat = request.lrlat;  // lower edge
        double ullon = request.ullon;  // left edge     longitudes
        double lrlon = request.lrlon;  // right edge
        int depth = request.depth;

        //  0.3862045 /  0.00274658203 = 140
        int xleft = (int) ((ullon - ROOT_ULLON) / LON_PER_TILE[depth]);
        // (47.754097979680026 - 47.655195) / 0.00185076002 = 53
        int yupper = (int) ((ROOT_ULLAT - ullat) / LAT_PER_TILE[depth]);
        // 0.3937935 / 0.00274658203 = 143
        int xright = (int) ((lrlon - ROOT_ULLON) / LON_PER_TILE[depth]);
        // 55
        int ylower = (int) ((ROOT_ULLAT - lrlat) / LAT_PER_TILE[depth]);

        Rasterer.Tile[][] tiles = new Rasterer.Tile[Math.abs(yupper - ylower) + 1][Math.abs(xright - xleft) + 1];
        for (int y = 0; y < ylower - yupper + 1; y++) {
            for (int x = 0; x < xright - xleft + 1; x++) {
                tiles[y][x] = new Tile(depth, xleft + x, yupper + y);
            }
        }
        return new RasterResult(tiles);
    }

    public static class Tile {
        public final int depth;
        public final int x;
        public final int y;

        public Tile(int depth, int x, int y) {
            this.depth = depth;
            this.x = x;
            this.y = y;
        }

        public Tile offset() {
            return new Tile(depth, x + 1, y + 1);
        }

        /**
         * Return the latitude of the upper-left corner of the given slippy map tile.
         * @return latitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lat() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyY = MIN_Y_TILE_AT_DEPTH[depth] + y;
            double latRad = Math.atan(Math.sinh(Math.PI * (1 - 2 * slippyY / n)));
            return Math.toDegrees(latRad);
        }

        /**
         * Return the longitude of the upper-left corner of the given slippy map tile.
         * @return longitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lon() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyX = MIN_X_TILE_AT_DEPTH[depth] + x;
            return slippyX / n * 360.0 - 180.0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tile tile = (Tile) o;
            return depth == tile.depth &&
                    x == tile.x &&
                    y == tile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, x, y);
        }

        @Override
        public String toString() {
            return "d" + depth + "_x" + x + "_y" + y + ".jpg";
        }
    }
}
