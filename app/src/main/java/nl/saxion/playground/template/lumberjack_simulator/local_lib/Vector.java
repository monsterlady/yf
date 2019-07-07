package nl.saxion.playground.template.lumberjack_simulator.local_lib;

/**
 * @author Mark Kravchuk
 * Own class to store Entitie`s coordinates position
 */

public class Vector {

    public float x;
    public float y;

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector(){
        x = 0f;
        y = 0f;
    }
}
