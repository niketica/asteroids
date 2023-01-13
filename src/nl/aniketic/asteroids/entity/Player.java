package nl.aniketic.asteroids.entity;

import nl.aniketic.asteroids.input.GameKey;
import nl.aniketic.asteroids.math.Vector2f;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends SpaceObject {

    private Vector2f[] vecModelShip;
    private boolean shooting;
    private int shootingCooldown = 10;
    private int curShootingCount = shootingCooldown;

    public Player(float x, float y, float dx, float dy, float angle) {
        super(x, y, dx, dy, 0, angle);
        speed *= 0.5f;

        float[] mx = new float[] { 0.0f, -2.5f, +2.5f }; // Ship Model Vertices
        float[] my = new float[] { -5.5f, +2.5f, +2.5f }; // Ship Model Vertices
        Vector2f[] vecModelCoordinates = new Vector2f[3];
        for (int i=0; i<3; i++) {
            vecModelCoordinates[i] = new Vector2f(mx[i], my[i]);
        }

        vecModelShip = scaleModel(vecModelCoordinates, 10.0f);
    }

    public void input() {
        // STEER
        if (GameKey.LEFT.isPressed()) {
            angle -= 1.0f * speed;
        }
        if (GameKey.RIGHT.isPressed()) {
            angle += 1.0f * speed;
        }

        // THRUST
        if (GameKey.UP.isPressed()) {
            // ACCELERATION changes VELOCITY (with respect to time)
            dx += Math.sin(angle) * 20.0f * speed;
            dy += -Math.cos(angle) * 20.0f * speed;
        }

        // SHOOTING
        shooting = false;
        if (curShootingCount < shootingCooldown) {
            curShootingCount++;
        } else {
            shooting = GameKey.SPACE.isPressed();
            if (shooting) {
                curShootingCount = 0;
            }
        }
    }

    public void update() {
        // VELOCITY changes POSITION (with respect to time)
        x += dx * speed;
        y += dy * speed;

        // Keep ship in game space
        x = wrapX(this.x);
        y = wrapY(this.y);
    }

    public void render(Graphics2D g2) {
        drawWireFrameModel(g2, vecModelShip, this.x, this.y, angle, 1.0f, Color.WHITE);
    }

    public boolean isShooting() {
        return shooting;
    }
}
