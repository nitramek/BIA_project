package cz.nitramek.bia.gui;

import cz.nitramek.bia.function.Function;
import net.sf.surfaceplot.ISurfacePlotModel;


public class FunctionSurfaceModel implements ISurfacePlotModel {


    public int min = 5;
    private Function function;
    private float zMax = 40.f;

    public FunctionSurfaceModel(Function function) {
        this.function = function;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int getPlotMode() {
        return ISurfacePlotModel.PLOT_MODE_SPECTRUM;
    }

    @Override
    public float calculateZ(float x, float y) {
        return (float) this.function.getValue(x, y);
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
        return this.function.getOptimalXMin();
    }

    @Override
    public float getXMax() {
        return this.function.getOptimalXMax();
    }

    @Override
    public float getYMin() {
        return this.function.getOptimalYMin();
    }

    @Override
    public float getYMax() {
        return this.function.getOptimalYMax();
    }

    @Override
    public float getZMin() {
        return (float) this.function.getOptimalZMin();
    }

    @Override
    public float getZMax() {
        return (float) this.function.getOptimalZMax();
    }

    public void setZMax(float zMax) {
        this.zMax = zMax;
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


    public void setFunction(Function function) {
        this.function = function;
    }
}
