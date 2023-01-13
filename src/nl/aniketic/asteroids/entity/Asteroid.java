package nl.aniketic.asteroids.entity;

import nl.aniketic.asteroids.math.Vector2f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Asteroid extends SpaceObject {

    public static final float RAND_MAX = 1.0f;

    private Random random;
    private Vector2f[] modelAsteroid;

    public Asteroid(float x, float y, float dx, float dy, int nSize, float angle) {
        super(x, y, dx, dy, nSize, angle);
        random = new Random();

        int verts = 20;
        modelAsteroid = new Vector2f[verts];

        for (int i=0; i<verts; i++) {

            float n = (float) i / (float) verts;

//            float radius = 1.0f;
            float radius = random.nextFloat() / RAND_MAX * 0.4f + 0.8f;
            float a = (float) (n * (Math.PI * 2));
            float vX = (float) (radius * Math.sin(a));
            float vY = (float) (radius * Math.cos(a));
            modelAsteroid[i] = new Vector2f(vX, vY);
        }
    }

    public void update() {
        // VELOCITY changes POSITION (with respect to time)
        x += dx * speed;
        y += dy * speed;

        // Keep ship in game space
        x = wrapX(this.x);
        y = wrapY(this.y);

        angle += 0.5f * 0.005f;
    }

    public void render(Graphics2D g2) {
        drawWireFrameModel(g2, modelAsteroid, x, y, angle, nSize, Color.YELLOW);
    }
}
