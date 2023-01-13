package nl.aniketic.asteroids.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends SpaceObject {

    public Bullet(float x, float y, float dx, float dy, int nSize, float angle) {
        super(x, y, dx, dy, nSize, angle);
    }

    public void update() {
        // VELOCITY changes POSITION (with respect to time)
        x += dx * speed;
        y += dy * speed;
    }

    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        int posX = (int) x;
        int posY = (int) y;
        g2.drawLine(posX, posY, posX, posY);
    }

    public void setX(float x) {
        this.x = x;
    }
}
