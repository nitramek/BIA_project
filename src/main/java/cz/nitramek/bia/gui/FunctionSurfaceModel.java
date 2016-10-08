package cz.nitramek.bia.gui;

import cz.nitramek.bia.cz.nitramek.bia.util.Point3DHolder;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Setter;
import lombok.ToString;
import net.sf.surfaceplot.SurfacePlotModel;

import java.util.Collections;
import java.util.List;


@ToString
public class FunctionSurfaceModel implements SurfacePlotModel {

    @Setter
    private float xMin;
    @Setter
    private float xMax;
    @Setter
    private float yMin;
    @Setter
    private float yMax;
    @Setter
    private float zMin;
    @Setter
    private float zMax;

    private EvaluatingFunction evaluatingFunction;


    public FunctionSurfaceModel(EvaluatingFunction evaluatingFunction) {
        this.evaluatingFunction = evaluatingFunction;
        this.xMin = this.evaluatingFunction.getOptimalXMin();
        this.xMax = this.evaluatingFunction.getOptimalXMax();
        this.yMin = this.evaluatingFunction.getOptimalYMin();
        this.yMax = this.evaluatingFunction.getOptimalYMax();
        this.zMin = (float) this.evaluatingFunction.getOptimalZMin();
        this.zMax = (float) this.evaluatingFunction.getOptimalZMax();

    }

    @Override
    public List<Point3DHolder> getExtraPoints() {
        Point3DHolder point = new Point3DHolder(0.f, 0.f, 0.f);
        return Collections.singletonList(point);
    }


    @Override
    public int getPlotMode() {
        return SurfacePlotModel.PLOT_MODE_SPECTRUM;
    }

    @Override
    public float calculateZ(float x, float y) {
        return (float) this.evaluatingFunction.getValue(x, y);
    }

    @Override
    public boolean isBoxed() {
        return true;
    }

    @Override
    public boolean isMesh() {
        return true;
    }

    @Override
    public boolean isScaleBox() {
        return false;
    }

    @Override
    public boolean isDisplayXY() {
        return true;
    }

    @Override
    public boolean isDisplayZ() {
        return true;
    }

    @Override
    public boolean isDisplayGrids() {
        return true;
    }

    @Override
    public int getCalcDivisions() {
        return 75;
    }

    @Override
    public int getDispDivisions() {
        return 75;
    }

    @Override
    public float getXMin() {
        return this.xMin;
    }

    @Override
    public float getXMax() {
        return this.xMax;
    }

    @Override
    public float getYMin() {
        return this.yMin;
    }

    @Override
    public float getYMax() {
        return this.yMax;
    }

    @Override
    public float getZMin() {
        return this.zMin;
    }

    @Override
    public float getZMax() {
        return this.zMax;
    }

    @Override
    public String getXAxisLabel() {
        return "X";
    }

    @Override
    public String getYAxisLabel() {
        return "Y";
    }

    @Override
    public String getZAxisLabel() {
        return "Z";
    }


    public void setEvaluatingFunction(EvaluatingFunction evaluatingFunction) {
        this.evaluatingFunction = evaluatingFunction;
    }
}
