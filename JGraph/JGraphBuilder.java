package JGraph;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class JGraphBuilder {
    private String axisNameX = "X";
    private String axisNameY = "Y";
    private ArrayList<Point2D> p = new ArrayList<>();

    public JGraphBuilder setAxisNameX(String axisNameX) {
        this.axisNameX = axisNameX;
        return this;
    }

    public JGraphBuilder setAxisNameY(String axisNameY) {
        this.axisNameY = axisNameY;
        return this;
    }

    public JGraphBuilder setP(ArrayList<Point2D> p) {
        this.p = p;
        return this;
    }

    public JGraph createJGraph() {
        return new JGraph(axisNameX, axisNameY, p);
    }
}