package com.koleman.zoom.logic.model.world;

import com.koleman.zoom.logic.model.shape.ShapeFactory;
import com.koleman.zoom.logic.model.shape.ZoomShape;

/**
 * Author Koleman Nix
 * Created On 6/17/13
 */
public class WorldEncoder {

    public static ZoomShape[][] decode(String input) {
        ZoomShape[][] result = new ZoomShape[8][8];
        int xCounter = 0;
        int yCounter = 0;
        for (int i = 0; i < 256; i+=4) {
            String subStr = input.substring(i, i + 4);
            ZoomShape decoded;
            if (subStr.equals("0000")) {
                decoded = null;
            }
            else {
                decoded = ShapeFactory.decode(subStr);
            }
            result[xCounter][yCounter] = decoded;
            xCounter++;
            if (xCounter == 8) {
                xCounter = 0;
                yCounter++;
            }
        }
        return result;
    }

    /**
     * Encoding Scheme: Each shape is represented by 3 digits, which can have a value of 1 2 or 3.
     * i.e., Small red square is a 111, Small Green Square is a 121, small green circle is a 123.
     */
    public static String encode(ZoomShape[][] input) {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                String encoded;
                if (input[x][y] == null) {
                    encoded = "0000";
                } else {
                    encoded = input[x][y].encode();
                }
                builder.append(encoded);
            }
        }
        return builder.toString();
    }

}
