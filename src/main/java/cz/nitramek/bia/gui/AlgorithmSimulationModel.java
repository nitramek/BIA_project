package cz.nitramek.bia.gui;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.Individual;
import cz.nitramek.bia.cz.nitramek.bia.util.Point3DHolder;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.surfaceplot.SurfacePlotModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@ToString
public class AlgorithmSimulationModel implements SurfacePlotModel {

    @Setter
    @Getter
    private Algorithm algorithm;

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

    @Getter
    private EvaluatingFunction evaluatingFunction;

    public AlgorithmSimulationModel(EvaluatingFunction evaluatingFunction, Algorithm algorithm) {
        this.algorithm = algorithm;
        this.evaluatingFunction = evaluatingFunction;
        this.xMin = this.evaluatingFunction.getOptimalXMin();
        this.xMax = this.evaluatingFunction.getOptimalXMax();
        this.yMin = this.evaluatingFunction.getOptimalYMin();
        this.yMax = this.evaluatingFunction.getOptimalYMax();
        this.zMin = (float) this.evaluatingFunction.getOptimalZMin();
        this.zMax = (float) this.evaluatingFunction.getOptimalZMax();
    }

    public AlgorithmSimulationModel(EvaluatingFunction evaluatingFunction) {
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
        if(this.algorithm == null){
            return Collections.emptyList();
        }else{
            return this.algorithm.getGeneration().stream()
                          .map(this::fromIndividual)
                          .collect(Collectors.toList());
        }
    }

    private Point3DHolder fromIndividual(Individual i) {
        double x = i.getParameters()[0];
        double y = i.getParameters()[1];
        return new Point3DHolder(x, y, 0);
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

}
