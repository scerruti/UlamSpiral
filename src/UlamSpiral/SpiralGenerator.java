package UlamSpiral;

import EratosthenesSieve.EratosthenesSieve;

/**
 * Created by stephen on 6/23/16.
 */
public class SpiralGenerator implements Runnable {
    private final long start;
    private final long width;
    private final UlamSpiralController controller;

    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT;

        public Direction getNextDirection() {
            switch (this) {
                case UP:
                    return LEFT;
                case DOWN:
                    return RIGHT;
                case LEFT:
                    return DOWN;
                case RIGHT:
                    return UP;
                default:
                    return UP;
            }
        }
        public long getSideLength(long sideLength) {
            switch (this) {
                case UP:
                case DOWN:
                    return  sideLength;
                case LEFT:
                case RIGHT:
                    return sideLength+1;
                default:
                    return 0;
            }
        }
        public long getX(long x) {
            switch (this) {
                case RIGHT:
                    return x+1;
                case LEFT:
                    return x-1;
                default:
                    return x;
            }
        }
        public long getY(long y) {
            switch (this) {
                case UP:
                    return y-1;
                case DOWN:
                    return y+1;
                default:
                    return y;
            }
        }
    }
    public SpiralGenerator(UlamSpiralController ulamSpiralController, long start, long width) {
        this.start = start;
        this.width = width;
        this.controller = ulamSpiralController;
    }

    @Override
    public void run() {
        Direction direction = Direction.RIGHT;
        long sideLength = 0;
        long currentSide = 0;
        long x = width / 2;
        long y = width / 2;
        long maxValue = width * width + start;
        controller.setMaxProgress(maxValue);
        EratosthenesSieve.sieve(maxValue, controller);

        controller.setProgressMessage("Plotting spiral");
        controller.setMaxProgress(width*width);
        for (long index = 0; index < width * width; index++) {
            long point = start + index;
            controller.drawPoint(point, EratosthenesSieve.isPrime(point), x, y);
            controller.incrementProgress();

            currentSide++;
            if (currentSide >= sideLength) {
                sideLength = direction.getSideLength(sideLength);
                direction = direction.getNextDirection();
                currentSide = 0;
            }
            x = direction.getX(x);
            y = direction.getY(y);
        }

        controller.spiralCompleted();
    }
}
