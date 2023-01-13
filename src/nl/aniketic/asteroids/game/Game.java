package nl.aniketic.asteroids.game;

import nl.aniketic.asteroids.display.DisplayManager;
import nl.aniketic.asteroids.entity.Asteroid;
import nl.aniketic.asteroids.entity.Bullet;
import nl.aniketic.asteroids.entity.Player;
import nl.aniketic.asteroids.input.KeyInput;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static final int RAND_MAX = 1;
    public static final int SMALLEST_ASTEROID_SIZE = 40;

    private boolean initialized;
    private Player player;

    private List<Asteroid> asteroids;
    private List<Bullet> bullets;

    private Random random;

    private int score;

    public Game() {
        DisplayManager displayManager = new DisplayManager(this);
        displayManager.createWindow();

        displayManager.addKeyListener(new KeyInput());

        GameLoop gameLoop = new GameLoop(this, displayManager);
        Thread thread = new Thread(gameLoop);
        thread.start();

        random = new Random();
    }

    public void start() {
        initialized = false;
        score = 0;

        Asteroid asteroid1 = new Asteroid(200.0f, 200.0f, 8.0f, -6.0f, 160, 0.0f);
        Asteroid asteroid2 = new Asteroid(1000.0f, 200.0f, -5.0f, 3.0f, 160, 0.0f);

        asteroids = new ArrayList<>();
        asteroids.add(asteroid1);
        asteroids.add(asteroid2);

        bullets = new ArrayList<>();

        player = new Player(DisplayManager.SCREEN_WIDTH / 2, DisplayManager.SCREEN_HEIGHT / 2, 0.0f, 0.0f, 0.0f);
        initialized = true;
    }

    public void input() {
        if (!initialized) {
            return;
        }

        player.input();
    }

    public void update() {
        if (!initialized) {
            return;
        }

        boolean dead = false;
        for (Asteroid a : asteroids) {
            if (a != null) {
                a.update();

                if (isPointInsideCircle(a.getX(), a.getY(), a.getnSize(), player.getX(), player.getY())) {
                    dead = true;
                }
            }
        }

        if (dead) {
            start();
            return;
        }

        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Asteroid> asteroidsToRemove = new ArrayList<>();
        List<Asteroid> newAsteroids = new ArrayList<>();
        bullets.forEach(b -> {
            if (b != null) {
                b.update();

                for (Asteroid a : asteroids) {
                    if (isPointInsideCircle(a.getX(), a.getY(), a.getnSize(), b.getX(), b.getY())) {
                        b.setX(-100);
                        asteroidsToRemove.add(a);
                        score += 100;

                        if (a.getnSize() > SMALLEST_ASTEROID_SIZE) {
                            float angle1 = (random.nextFloat() / RAND_MAX) * ((float)Math.PI * 2);
                            float angle2 = (random.nextFloat() / RAND_MAX) * ((float)Math.PI * 2);
                            Asteroid asteroid1 = new Asteroid(a.getX(), a.getY(), 10.0f * (float) Math.sin(angle1), 10.0f * (float) Math.cos(angle1), a.getnSize() / 2, 0.0f);
                            Asteroid asteroid2 = new Asteroid(a.getX(), a.getY(), 10.0f * (float) Math.sin(angle2), 10.0f * (float) Math.cos(angle2), a.getnSize() / 2, 0.0f);
                            newAsteroids.add(asteroid1);
                            newAsteroids.add(asteroid2);
                        }
                    }
                }

                if (b.getX() < 0 || b.getX() > DisplayManager.SCREEN_WIDTH || b.getY() < 0 || b.getY() > DisplayManager.SCREEN_HEIGHT) {
                    bulletsToRemove.add(b);
                }
            }
        });
        bullets.removeAll(bulletsToRemove);
        asteroids.removeAll(asteroidsToRemove);
        asteroids.addAll(newAsteroids);

        player.update();
        if (player.isShooting()) {
            Bullet bullet = new Bullet(player.getX(), player.getY(), 50.0f * (float) Math.sin(player.getAngle()), -50.0f * (float) Math.cos(player.getAngle()), 0, 0);
            bullets.add(bullet);
        }

        if (asteroids.isEmpty()) {
            score += 1000;

            Asteroid asteroid1 = new Asteroid(
                    30.0f * (float) Math.sin(player.getAngle() - (float) Math.PI / 2.0f),
                    30.0f * (float) Math.cos(player.getAngle() - (float) Math.PI / 2.0f),
                    10.0f * (float) Math.sin(player.getAngle()),
                    10.0f * (float) Math.cos(player.getAngle()),
                    160,
                    0.0f);
            Asteroid asteroid2 = new Asteroid(
                    30.0f * (float) Math.sin(player.getAngle() + (float) Math.PI / 2.0f),
                    30.0f * (float) Math.cos(player.getAngle() + (float) Math.PI / 2.0f),
                    10.0f * (float) Math.sin(-player.getAngle()),
                    10.0f * (float) Math.cos(-player.getAngle()),
                    160,
                    0.0f);

            asteroids.add(asteroid1);
            asteroids.add(asteroid2);
        }
    }

    public void render(Graphics2D g2) {
        if (!initialized) {
            return;
        }

        asteroids.forEach(a -> {
            if (a != null) {
                a.render(g2);
            }
        });
        bullets.forEach(b -> {
            if (b != null) {
                b.render(g2);
            }
        });
        player.render(g2);

        g2.drawString("SCORE: " + score, 2, 20);
    }

    public boolean isPointInsideCircle(float cx, float cy, float radius, float x, float y) {
        float distance = (float) Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
        return distance < radius;
    }
}
