import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.io.File;
import java.util.Scanner;

public class PlotPanel extends Panel {

    private int height = 500, width = 510, functionCase = 0;
    double end, step;
    double h0, mu, m, k, f0, w, c1, c2, phi0, top, bottom, scaleY;
    Double[] values = new Double[500];

    PlotPanel(double scale) {
        try {
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);
            h0 = sc.nextDouble();
            m = sc.nextDouble();
            mu = sc.nextDouble();
            k = sc.nextDouble();
            f0 = sc.nextDouble();
            w = sc.nextDouble();
            phi0 = sc.nextDouble();
            c1 = Math.cos((phi0 % (2 * Math.PI)) - Math.PI) * h0;
            c2 = Math.sin((phi0 % (2 * Math.PI)) - Math.PI) * h0;
            end = scale;
            step = scale / 500;
            functionCase = 1 + (int) Math.signum(mu * mu - 4 * k * m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double function(double x) {
        double firstPart = (((k / m - w * w) * Math.sin(w * x) - mu * w * Math.cos(w * x) / m) / ((k / m - w * w) * (k / m - w * w) + mu * mu * w * w / (m * m))) * (f0 / m);
        if (functionCase == 2) {
            return firstPart + (c1 * Math.exp(x * (-mu + Math.sqrt(mu * mu - 4 * k * m)) / (2 * m)) + c2 * x * Math.exp(x * (-mu - Math.sqrt(mu * mu - 4 * k * m)) / (2 * m)));
        } else if (functionCase == 1) {
            return firstPart + ((c1 + c2) * Math.exp(-mu * x / (2 * m)));
        } else if (functionCase == 0) {
            return firstPart + (Math.exp(-mu * x / (2 * m)) * (c1 * Math.cos(x * Math.sqrt(4 * k * m - mu * mu) / (4 * m * m)) + c2 * Math.sin(x * Math.sqrt(4 * k * m - mu * mu) / (4 * m * m))));
        }
        return -1;
    }

    public void paintFunction(Graphics g) {
        calculateFunction();
        clear(g);
        g.setColor(Color.red);
        drawFunction(g);
    }

    public void drawAxis(Graphics g) {
        g.drawLine(0, height / 2, width, height / 2);
        g.drawLine(10, 0, 10, height);
        g.drawString("x", 15, 40);
        g.drawString("t", 480, 240);
        g.drawString("t scale:" + end, 400, 50);
        g.drawString("x scale:" + scaleY, 400, 60);
        for (int i = 50; i <= 450; i += 50) {
            g.drawLine(5, i, 15, i);
            g.drawLine(10 + i, 255, 10 + i, 245);
        }
    }

    public void calculateFunction() {
        double min = function(0), max = function(0), currentY;
        values[0] = min;
        for (int x = 1; x < 500; x++) {
            currentY = function(x * step);
            if (currentY > max) {
                max = currentY;
            }
            if (currentY < min) {
                min = currentY;
            }
            values[x] = currentY;
        }
        top = max;
        bottom = min;
        scaleY = Math.max(Math.abs(min), Math.abs(max));
    }

    public void drawFunction(Graphics g) {
        try {
            if (0 > end) {
                return;
            }
            for (int x = 0; x < 499; x++) {
                int pointX = (x + 10), pointY = height / 2 - (int) (values[x] * 200 / scaleY),
                        pointX2 = (x + 11), pointY2 = height / 2 - (int) (values[x + 1] * 200 / scaleY);
                g.drawLine(pointX, pointY, pointX2, pointY2);
            }
        } catch (NumberFormatException numberFormatException) {
            // No operations
        }
    }

    public void clear(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        drawAxis(g);
    }
}