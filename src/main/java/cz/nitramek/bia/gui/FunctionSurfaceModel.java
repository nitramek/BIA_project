package cz.nitramek.bia.gui;

import cz.nitramek.bia.function.Function;
import lombok.Setter;
import lombok.ToString;
import net.sf.surfaceplot.ISurfacePlotModel;


@ToString
public class FunctionSurfaceModel implements ISurfacePlotModel {

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

    private Function function;


    public FunctionSurfaceModel(Function function) {
        this.function = function;
        this.xMin = this.function.getOptimalXMin();
        this.xMax = this.function.getOptimalXMax();
        this.yMin = this.function.getOptimalYMin();
        this.yMax = this.function.getOptimalYMax();
        this.zMin = (float) this.function.getOptimalZMin();
        this.zMax = (float) this.function.getOptimalZMax();
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


    public void setFunction(Function function) {
        this.function = function;
    }
}
