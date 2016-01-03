package jimmycook.game;

/**
 * Game end entity
 *
 * Has no sprite, is purely for game logic purposes
 */
public class End extends Entity {

    /**
     * Constructor
     * @param x int     coordinate on the x axis
     * @param dx int    vector for x axis
     */
    public End(int x, int dx){
        this.x = x;
        this.dx = dx;
        y = 0;
        height = 500;
        width = 10;
    }

    /**
     * Update the position of the entity using the vector
     */
    public void update(){
        x += dx*2;
    }
}
