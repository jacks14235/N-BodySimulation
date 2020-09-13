public class NBody {

    public static void main(String[] args) {
        double totalTime = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        // number of bodies from txt
        int n = StdIn.readInt();
        // set up canvas dimensions
        double radius = StdIn.readDouble();
        StdDraw.setXscale(-1 * radius, radius);
        StdDraw.setYscale(-1 * radius, radius);
        StdDraw.enableDoubleBuffering();

        // create arrays of length n with information about each planet
        double[][] position = new double[n][2];
        double[][] velocity = new double[n][2];
        double[] mass = new double[n];
        String[] image = new String[n];

        // array to save forces so that they can be applied after all forces are
        // solved for
        double[][] force = new double[n][2];

        // read input for each planet and add info to the arrays
        for (int i = 0; i < n; i++) {
            position[i][0] = StdIn.readDouble();
            position[i][1] = StdIn.readDouble();
            velocity[i][0] = StdIn.readDouble();
            velocity[i][1] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // iteration of time
        double t = 0;
        while (t < totalTime) {
            t += dt;
            // iterates through planet receiving force
            for (int i = 0; i < n; i++) {
                double G = 6.67 * Math.pow(10, -11);
                // total force [x, y] exerted on body a
                double[] f = new double[2];
                // iterates through other planet causing force
                for (int j = 0; j < n; j++) {
                    // don't count force on itself
                    if (i == j) {
                        continue;
                    }
                    // distance between i and j
                    double distance = Math
                            .sqrt(Math.pow(position[i][0] - position[j][0], 2) + Math
                                    .pow(position[i][1] - position[j][1], 2));

                    // magnitude of gravitational force
                    double magnitude = ((G * mass[i] * mass[j]) /
                            (Math.pow(distance, 2)));

                    // add magnitude * x component
                    f[0] += magnitude * ((position[j][0] - position[i][0]) /
                            distance);

                    // add magnitude * y component
                    f[1] += magnitude * ((position[j][1] - position[i][1]) /
                            distance);

                }
                // saves force so that the others can be calculated before movement
                force[i][0] = f[0];
                force[i][1] = f[1];
            }
            // movement simulation
            for (int i = 0; i < n; i++) {
                // change velocity based on force
                velocity[i][0] += (force[i][0] / mass[i]) * dt;
                velocity[i][1] += (force[i][1] / mass[i]) * dt;
                // change position based on velocity
                position[i][0] += velocity[i][0] * dt;
                position[i][1] += velocity[i][1] * dt;
            }
            // redraw planets
            StdDraw.clear();
            //StdDraw.pause(25);
            StdDraw.picture(0, 0, "starfield.jpg");
            // draw all bodies
            for (int i = 0; i < n; i++) {
                StdDraw.picture(position[i][0], position[i][1], image[i]);
            }
            StdDraw.show();

        }
        // output final parameters of each planet
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          position[i][0], position[i][1],
                          velocity[i][0], velocity[i][1],
                          mass[i], image[i]);
        }

    }
}
