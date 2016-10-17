package cz.nitramek.bia.cz.nitramek.bia.util;


import lombok.Data;

import java.util.Random;

@Data
public class Boundary {
    public final double min;
    public final double max;

    /**
     * @return Random in interval {@literal <}min,max>
     */
    public double randomDouble(Random random) {
        return random.nextDouble() * this.getRange() + this.min;
    }

    /**
     * Creates new Random so in a cycle is not very good to use
     *
     * @return Random in interval {@literal <}min,max>
     */
    public double randomDouble() {
        return randomDouble(new Random());
    }

    /**
     * Creation method is casting random double to int
     *
     * @return Random in interval {@literal <}min,max>
     */
    public int randomInt(Random random) {
        return (int) this.randomDouble(random);
    }

    public int randomInt() {
        return (int) this.randomDouble(new Random());
    }

    public boolean isInBoundary(double x) {
        return x >= this.min && x <= this.max;
    }


    public double getInRange(double x) {
        if (x < this.min) {
            return x + this.getRange();
        } else if (x > this.max) {
            return x - this.getRange();
        } else {
            return x;
        }

    }

    public double getRange() {
        return this.max - this.min;
    }
}
