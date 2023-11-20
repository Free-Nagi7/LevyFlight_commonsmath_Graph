import java.util.Random;
@Grab(group='org.apache.commons', module='commons-math', version='3.6.1');
import org.apache.commons.math.util.MathUtils;
package org.apache.commons.math.special;

public class LF {
    
    public double LFstep(){
        double levyStep = 0;
        double beta = 1.5;
        double simga =  doSigma(beta);
        levyStep = getStep(simga, beta);
        return levyStep;
    }
    
    public double getStep(double sigma, double beta){
        double u = new Random().nextGaussian()*sigma;
        double v = new Random().nextGaussian();
        return u/Math.pow(Math.abs(v), (1/beta));
    }
    
     public  double doSigma(double beta){
         double term1 = logGamma(beta+1)*Math.sin((Math.PI*beta)/2);
         double term2 = logGamma((beta+1)/2)*beta*Math.pow(2,(beta-1)/2);
         return Math.pow((term1/term2),(1/beta));
    }
     
     public double logGamma(double x){
         double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
         double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                        + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                        +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
         return Math.exp(tmp + Math.log(ser * Math.sqrt(2 * Math.PI)));
    }

}