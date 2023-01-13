package nl.aniketic.asteroids.entity;

import nl.aniketic.asteroids.display.DisplayManager;
import nl.aniketic.asteroids.math.Vector2f;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class SpaceObject {

    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected int nSize;
    protected float angle;

    protected float speed = 0.2f;

    public SpaceObject(float x, float y, float dx, float dy, int nSize, float angle) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.nSize = nSize;
        this.angle = angle;
    }

    public int wrapX(int x) {
        if (x < 0) {
            return x + DisplayManager.SCREEN_WIDTH;
        }
        if (x > DisplayManager.SCREEN_WIDTH) {
            return x - DisplayManager.SCREEN_WIDTH;
        }
        return x;
    }

    public int wrapY(int y) {
        if (y < 0) {
            return y + DisplayManager.SCREEN_HEIGHT;
        }
        if (y > DisplayManager.SCREEN_HEIGHT) {
            return y - DisplayManager.SCREEN_HEIGHT;
        }
        return y;
    }

    public float wrapX(float x) {
        if (x < 0.0f) {
            return x + DisplayManager.SCREEN_WIDTH;
        }
        if (x > DisplayManager.SCREEN_WIDTH) {
            return x - DisplayManager.SCREEN_WIDTH;
        }
        return x;
    }

    public float wrapY(float y) {
        if (y < 0.0f) {
            return y + DisplayManager.SCREEN_HEIGHT;
        }
        if (y > DisplayManager.SCREEN_HEIGHT) {
            return y - DisplayManager.SCREEN_HEIGHT;
        }
        return y;
    }

    public Vector2f[] scaleModel(Vector2f[] vecModelCoordinates, float s) {
        // Create translated model of coordinate pairs
        Vector2f[] vecTransformedCoordinates = new Vector2f[vecModelCoordinates.length];
        for (int i = 0; i < vecTransformedCoordinates.length; i++) {
            Vector2f vecModelCoordinate = vecModelCoordinates[i];
            vecTransformedCoordinates[i] = new Vector2f(vecModelCoordinate.x, vecModelCoordinate.y);
        }

        for (int i = 0; i < vecModelCoordinates.length; i++) {
            vecTransformedCoordinates[i].x = vecTransformedCoordinates[i].x * s - vecTransformedCoordinates[i].y * 0;
            vecTransformedCoordinates[i].y = vecTransformedCoordinates[i].x * 0 + vecTransformedCoordinates[i].y * s;
        }

        return vecTransformedCoordinates;
    }

    public void drawWireFrameModel(Graphics2D g2, Vector2f[] vecModelCoordinates, float x, float y, float r, float s,
                                   Color color) {
        g2.setColor(color);

        // Create translated model of coordinate pairs
        Vector2f[] vecTransformedCoordinates = new Vector2f[vecModelCoordinates.length];
        for (int i = 0; i < vecTransformedCoordinates.length; i++) {
            Vector2f vecModelCoordinate = vecModelCoordinates[i];
            vecTransformedCoordinates[i] = new Vector2f(vecModelCoordinate.x, vecModelCoordinate.y);
        }

        // Rotate
        for (int i = 0; i < vecModelCoordinates.length; i++) {
            vecTransformedCoordinates[i].x =
                    (float) (vecModelCoordinates[i].x * Math.cos(r) - vecModelCoordinates[i].y * Math.sin(r));
            vecTransformedCoordinates[i].y =
                    (float) (vecModelCoordinates[i].x * Math.sin(r) + vecModelCoordinates[i].y * Math.cos(r));
        }

        // Scale
        for (int i = 0; i < vecModelCoordinates.length; i++) {
            vecTransformedCoordinates[i].x = vecTransformedCoordinates[i].x * s - vecTransformedCoordinates[i].y * 0;
            vecTransformedCoordinates[i].y = vecTransformedCoordinates[i].x * 0 + vecTransformedCoordinates[i].y * s;
        }

        // Translate
        for (int i = 0; i < vecModelCoordinates.length; i++) {
            vecTransformedCoordinates[i].x += x;
            vecTransformedCoordinates[i].y += y;
        }

        // Draw polygon
        for (int i = 0; i < vecModelCoordinates.length; i++) {
            int j = i + 1;
            float x1 = vecTransformedCoordinates[i % vecModelCoordinates.length].x;
            float y1 = vecTransformedCoordinates[i % vecModelCoordinates.length].y;
            float x2 = vecTransformedCoordinates[j % vecModelCoordinates.length].x;
            float y2 = vecTransformedCoordinates[j % vecModelCoordinates.length].y;
            drawBresenhamLine(g2, (int) x1, (int) y1, (int) x2, (int) y2);
        }
    }

    private void drawBresenhamLine(Graphics2D g2, int x1, int y1, int x2, int y2) {

        float rise = y2 - y1;
        float run = x2 - x1;
        float m = rise / run;

        float adjust = m >= 0 ? 1 : -1;
        float threshold = 0.5f;
        float offset = 0;

        if (m <= 1 && m >= -1) {
            float delta = Math.abs(m);
            int y = y1;

            if (x2 < x1) {
                int tempx = x1;
                x1 = x2;
                x2 = tempx;
                y = y2;
            }

            for (int x=x1; x<x2+1; x++) {
                int wrapX = wrapX(x);
                int wrapY = wrapY(y);
                g2.drawLine(wrapX, wrapY, wrapX, wrapY);
                offset += delta;
                if (offset >= threshold) {
                    y += adjust;
                    threshold += 1;
                }
            }
        } else {
            float delta = Math.abs(run / rise);
            int x = x1;
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
                x = x2;
            }

            for (int y=y1; y<y2+1; y++) {
                int wrapX = wrapX(x);
                int wrapY = wrapY(y);
                g2.drawLine(wrapX, wrapY, wrapX, wrapY);
                offset += delta;
                if (offset >= threshold) {
                    x += adjust;
                    threshold += 1;
                }
            }
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public int getnSize() {
        return nSize;
    }

    public float getAngle() {
        return angle;
    }

    public float getSpeed() {
        return speed;
    }
}
