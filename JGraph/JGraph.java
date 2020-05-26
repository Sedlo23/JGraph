package JGraph;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 * The type J graph.
 */
public class JGraph extends JPanel implements MouseListener {

    private ArrayList<Point2D> data;

    private final int MAX_LENGTH_OF_DISPLAYED_NUMBERS;

    private String axisNameX;

    private String axisNameY;

    private double maxFoundNumberOfY;

    private int axisCountX;

    private double maxFoundNumberOfX;

    private int movingAvgNum;

    private double offsetX;

    private boolean movingAvg;

    private int axisCountY;

    /**
     * Instantiates a new J graph.
     */
    public JGraph() {
        this("X","Y",new ArrayList<>());
    }

    /**
     * Instantiates a new J graph.
     *
     * @param axisNameX the axis name x
     * @param axisNameY the axis name y
     */
    public JGraph(String axisNameX, String axisNameY) {
        this(axisNameX, axisNameY,new ArrayList<>());
    }

    /**
     * Instantiates a new J graph.
     *
     * @param axisNameX the axis name x
     * @param axisNameY the axis name y
     * @param p         the p
     */
    public JGraph(String axisNameX, String axisNameY, ArrayList<Point2D> p) {
        super();

        this.setAxisNameX(axisNameX);
        this.setAxisNameY(axisNameY);
        this.setData(p);
        this.setAxisCountY(4);
        this.setMovingAvg(false);
        this.setOffsetX(0);
        this.setMovingAvgNum(5);
        this.setMaxFoundNumberOfY(1);
        this.setAxisCountX(4);
        this.setMaxFoundNumberOfX(1);
        this.MAX_LENGTH_OF_DISPLAYED_NUMBERS =4;

        this.addMouseListener(this);

    }

    /**
     * Sets moving avg.
     *
     * @param movingAvg the moving avg
     */
    public void setMovingAvg(boolean movingAvg) {
        this.movingAvg = movingAvg;
    }

    /**
     * Gets moving avg num.
     *
     * @return moving avg num
     */
    public int getMovingAvgNum() {
        return movingAvgNum;
    }

    /**
     * Sets moving avg num.
     *
     * @param movingAvgNum the moving avg num
     */
    public void setMovingAvgNum(int movingAvgNum) {
        this.movingAvgNum = movingAvgNum;
    }

    /**
     * Gets axis count x.
     *
     * @return axis count x
     */
    public int getAxisCountX() {
        return axisCountX;
    }

    /**
     * Sets axis count x.
     *
     * @param axisCountX the axis count x
     */
    public void setAxisCountX(int axisCountX) {
        if(axisCountX >0)
        this.axisCountX = axisCountX;
    }

    /**
     * Gets axis count y.
     *
     * @return axis count y
     */
    public int getAxisCountY() {
        return axisCountY;
    }

    /**
     * Sets axis count y.
     *
     * @param axisCountY the axis count y
     */
    public void setAxisCountY(int axisCountY) {
        if(axisCountY >0)
        this.axisCountY = axisCountY;
    }


    /**
     * Avg double.
     *
     * @param array the array
     * @return double
     */
    public static double Avg(double[] array) {

        double sum = 0;

        for (double tmp : array)
            sum += tmp;

        return sum / array.length;
    }

    /**
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        for (int tt=0;tt<3;tt++)
        {

        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(0, getHeight());

        g2.setFont(new Font("TimesRoman", Font.PLAIN,12));

        g2.setColor(Color.blue);


        for(int i = 0; i<= getAxisCountY(); i++)
        {
            double value= ((double) getMaxFoundNumberOfY() /(double) getAxisCountY())*i;
            g2.drawString(formatNum(Math.round(value*100d)/100d), 2, -((this.getHeight()-100)/ getAxisCountY())*i-50);


        }

        for(int i = 0; i<= getAxisCountX(); i++)
        {

            double value= ((double) getMaxFoundNumberOfX() /(double) getAxisCountX())*i;
            g2.drawString( formatNum(Math.round((value+ getOffsetX())*100d)/100d),((this.getWidth()-200)/ getAxisCountX())*i+100, -10);

        }

        g2.drawString(getAxisNameX(), getWidth() - 50, -50);

        g2.drawString(getAxisNameY(), 50,-getHeight()+20);

        g2.translate(+100, -50);

        g2.scale(1, -1);

        double scX=((getWidth()-200)/(getMaxFoundNumberOfX()));

        double scY=((getHeight()-100)/((getMaxFoundNumberOfY())));

        g2.setColor(Color.BLACK);

        ArrayList<Point2D> point2DS;

            if (isMovingAvg())
                {
                    point2DS =getMovingAvgGraph();
                }
            else
                {
                    point2DS=getPlainGraph();
                }

        g2.setColor(Color.LIGHT_GRAY);

        for(int i = 0; i<= getAxisCountX(); i++)
        {
            g2.draw(new Line2D.Double(i*(getMaxFoundNumberOfX() / getAxisCountX())*scX,0,i*(getMaxFoundNumberOfX() / getAxisCountX())*scX, getMaxFoundNumberOfY() *scY));

        }

        for(int i = 0; i<= getAxisCountY(); i++)
        {
            g2.draw(new Line2D.Double(0,i*((getMaxFoundNumberOfY())/ getAxisCountY())*scY, getMaxFoundNumberOfX() *scX,i*((getMaxFoundNumberOfY())/ getAxisCountY())*scY));
        }

        g2.setColor(Color.BLACK);

        for (int x= 0;x<point2DS.size()-1;x++)
        {
            g2.drawLine((int)point2DS.get(x).getX(),(int)point2DS.get(x).getY(),(int)point2DS.get(x+1).getX(),(int)point2DS.get(x+1).getY());
        }


        g2.translate(-100, -50);

        }
    }

    private ArrayList <Point2D> getPlainGraph()
    {
        double scX=((getWidth()-200)/(getMaxFoundNumberOfX()));
        double scY=((getHeight()-100)/((getMaxFoundNumberOfY())));


        ArrayList<Point2D> point2DS=new ArrayList<>();
        for (int i = 0; i < getData().size(); i+=1)
        {
            Point2D tmp = new Point2D.Double(getData().get(i).getX()*scX, getData().get(i).getY()*scY);

            point2DS.add(tmp);

            setMaxFoundNumberOfY(Math.max(tmp.getY()/scY, getMaxFoundNumberOfY()));

            setMaxFoundNumberOfX(Math.max(tmp.getX()/scX, getMaxFoundNumberOfX()));
        }
        return point2DS;
    }

    private ArrayList <Point2D> getMovingAvgGraph()
    {
        double scX=((getWidth()-200)/(getMaxFoundNumberOfX()));
        double scY=((getHeight()-100)/((getMaxFoundNumberOfY())));

        ArrayList<Point2D> point2DS=new ArrayList<>();
        for (int i = 0; i < getData().size(); i++)
        {
            if (i > getMovingAvgNum()) {
                double[] avg = new double[getMovingAvgNum()];
                double[] avg2 = new double[getMovingAvgNum()];


                for (int z = 0; z < getMovingAvgNum(); z++)
                    avg[z] = getData().get(i - z).getY();

                for (int z = 0; z < getMovingAvgNum(); z++)
                    avg2[z] = getData().get(i - z - 1).getY();


                Point2D tmp = new Point2D.Double(getData().get(i).getX()*scX, Avg(avg)*scY);
                point2DS.add(tmp);

                setMaxFoundNumberOfY(Math.max(tmp.getY()/scY, getMaxFoundNumberOfY()));

                setMaxFoundNumberOfX(Math.max(tmp.getX()/scX, getMaxFoundNumberOfX()));

            }
        }
        return point2DS;
    }

    private String formatNum(double number) {
        String out = null;
        for (int i = 0; i < getMAX_LENGTH_OF_DISPLAYED_NUMBERS(); i++ ) {
            String format = "%." + i + "G";
            out = String.format(format, number);
            if ( out.length() == getMAX_LENGTH_OF_DISPLAYED_NUMBERS()) {
                return out;
            }
        }
        return out;
    }

    /**
     * Gets data.
     *
     * @return data
     */
    public ArrayList<Point2D> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(ArrayList<Point2D> data) {
        this.data=(data);
    }

    /**
     * Is moving avg boolean.
     *
     * @return boolean
     */
    public boolean isMovingAvg() {
        return movingAvg;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * Gets max length of displayed numbers.
     *
     * @return the max length of displayed numbers
     */
    public int getMAX_LENGTH_OF_DISPLAYED_NUMBERS() {
        return MAX_LENGTH_OF_DISPLAYED_NUMBERS;
    }

    /**
     * Gets axis name x.
     *
     * @return the axis name x
     */
    public String getAxisNameX() {
        return axisNameX;
    }

    /**
     * Sets axis name x.
     *
     * @param axisNameX the axis name x
     */
    public void setAxisNameX(String axisNameX) {
        this.axisNameX = axisNameX;
    }

    /**
     * Gets axis name y.
     *
     * @return the axis name y
     */
    public String getAxisNameY() {
        return axisNameY;
    }

    /**
     * Sets axis name y.
     *
     * @param axisNameY the axis name y
     */
    public void setAxisNameY(String axisNameY) {
        this.axisNameY = axisNameY;
    }

    /**
     * Gets max found number of y.
     *
     * @return the max found number of y
     */
    public double getMaxFoundNumberOfY() {
        return maxFoundNumberOfY;
    }

    /**
     * Sets max found number of y.
     *
     * @param maxFoundNumberOfY the max found number of y
     */
    public void setMaxFoundNumberOfY(double maxFoundNumberOfY) {
        this.maxFoundNumberOfY = maxFoundNumberOfY;
    }

    /**
     * Gets max found number of x.
     *
     * @return the max found number of x
     */
    public double getMaxFoundNumberOfX() {
        return maxFoundNumberOfX;
    }

    /**
     * Sets max found number of x.
     *
     * @param maxFoundNumberOfX the max found number of x
     */
    public void setMaxFoundNumberOfX(double maxFoundNumberOfX) {
        this.maxFoundNumberOfX = maxFoundNumberOfX;
    }

    /**
     * Gets offset x.
     *
     * @return the offset x
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * Sets offset x.
     *
     * @param offsetX the offset x
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

}
