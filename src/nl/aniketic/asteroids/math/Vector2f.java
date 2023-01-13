package nl.aniketic.asteroids.math;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2f div(float value) {
        return new Vector2f(x / value, y / value);
    }

    public Vector2f getUnitVector() {
        return div(length());
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
