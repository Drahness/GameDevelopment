package self.joanciscar.gamedevelopment;

public class PlayerBall extends Ball {
    private final Vector fingerPosition = new Vector(0,0); // this needs to be fully sincronized. maybe a sigle instance and dump the other instances to this.
    private boolean isActive;
    public PlayerBall(float radius) {
        super(radius);
    }
    public PlayerBall() {}
    public Vector getFingerPosition() {
        Vector cloned;
        synchronized (fingerPosition) { // TODO in odd cases can be null
            cloned = fingerPosition.getCopy();
        }
        return cloned;
    }

    public void setFingerPosition(Vector fingerPosition) {
        synchronized (this.fingerPosition) { // TODO i assume in odd cases can be null
            this.fingerPosition.copyOf(fingerPosition);
        }
    }

    public void setFingerActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void hitBall(float hitPower) {
        // tengo la posicion
        Vector ballPosition = this.getPosition();
        // donde esta el dedo, es una copia por sincronizacion.
        Vector finger = this.getFingerPosition();
        Vector velocidad = ballPosition.newVelocity(hitPower, finger);
        velocidad.inverse(); // invertimos la velocidad para que salga disparada en direccion contraria
        // a donde se encuentra el objeto.
        this.setVelocity(velocidad);
    }

    public boolean hasFingerPressed() {
        return isActive;
    }
}
