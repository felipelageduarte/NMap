/* ***** BEGIN LICENSE BLOCK *****
 *
 * Nmap: A Novel Neighborhood Preservation Space-filling Algorithm
 *
 * Copyright (c) 2012-2014 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * you can redistribute it and/or modify it under the terms of the GNU General 
 * Public License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *
 * Nmap is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * This code was developed by members of Visualization, Imaging and Computer Graphics
 * (http://http://vicg.icmc.usp.br/) at Instituto de Ciencias
 * Matematicas e de Computacao - ICMC - (http://www.icmc.usp.br) of 
 * Universidade de Sao Paulo, Sao Carlos/SP, Brazil. The initial developer 
 * of the original code is Felipe S. L. G. Duarte <felipelageduarte@usp.br>.
 *
 * Contributor(s): Felipe S. L. G. Duarte <felipelageduarte@usp.br>
 *                 Fabio Sikansi <fhenrique@usp.br>
 *                 Francisco M. Fatore <fmfatore@usp.br>
 *                 Samuel G. Fadel <samuel.fadel@usp.br>
 *                 Fernando V. Paulovich <paulovic@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along 
 * with Nmap. If not, see <http://www.gnu.org/licenses/>.
 *
 * based on the publication:
 * 
 *  @Article{nmap,
 *   Title                    = {Nmap: A Novel Neighborhood Preservation Space-filling Algorithm},
 *   Author                   = {Duarte, Felipe S. L. G. and Sikansi, Fabio H.  G. and Fatore, 
 * 								 Francisco M. and Fadel, Samuel G. and Paulovich, Fernando V.},
 *   Journal                  = {IEEE Transactions on Visualization and Computer Graphics},
 *   Year                     = {2014},
 * 
 *   Month                    = {August},
 *   Number                   = {99},
 *   Pages                    = {1},
 *   Volume                   = {PP},
 * 
 *   keywords                 = {Color;Conferences;Image color analysis;Indexes;Layout;Shape;Visualization}, 
 *   ISSN                     = {1077-2626}
 *   Doi                      = {10.1109/TVCG.2014.2346276},
 * }
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * ***** END LICENSE BLOCK ***** */

package nmap;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NMap {

    private final BoundingBox visualSpace;

    private static boolean HORIZONTAL = true;
    private static boolean VERTICAL = false;
    
    public NMap(Rectangle r) {
        this.visualSpace = new BoundingBox(r.x,r.y,r.width,r.height);
    }
    
    public NMap(BoundingBox visualSpace) {
        this.visualSpace = visualSpace;
    }

    public NMap(int w, int h) {
        this.visualSpace = new BoundingBox(0, 0, w, h);
    }
    
    /*
     * 
     */
    private void normalize(List<Element> D){
        float maxx = Float.MIN_VALUE;
        float minx = Float.MAX_VALUE;
        
        float maxy = Float.MIN_VALUE;
        float miny = Float.MAX_VALUE;
        
        for(Element d : D){
            maxx = Math.max(maxx, d.x);
            minx = Math.min(minx, d.x);
            maxy = Math.max(maxy, d.y);
            miny = Math.min(miny, d.y);
        }
        
        for(Element d : D){
            d.x = (((d.x - minx)/(maxx - minx))*(visualSpace.width)) + visualSpace.x;
            d.y = (((d.y - miny)/(maxy - miny))*(visualSpace.height)) + visualSpace.y;
        }
    }

    /*
     * 
     */
    private void sortByX(List<Element> D) {
        Collections.sort(D, (Element e1, Element e2) -> {
            if(e1.x > e2.x) return 1;
            else if(e1.x < e2.x) return -1;
            else return 0;
        });
    }

    private void sortByY(List<Element> D) {
        Collections.sort(D, (Element e1, Element e2) -> {
            if(e1.y > e2.y) return 1;
            else if(e1.y < e2.y) return -1;
            else return 0;
        });
    }

    /*
     * 
     */
    private List<BoundingBox> alternateCut(BoundingBox R, List<Element> D, boolean bisection) {
        
        List<BoundingBox> elementsAreas = new ArrayList<>();

        if (D.size() == 1) {
            BoundingBox bb = new BoundingBox(R.x, R.y, R.width, R.height, D.get(0));
            elementsAreas.add(bb);
        
        } else {
            
            if (bisection == HORIZONTAL) sortByX(D);
            else sortByY(D);

            int cutElement = D.size() / 2;
            List<Element> Da = D.subList(0, cutElement);
            List<Element> Db = D.subList(cutElement, D.size());

            double pA = 0.0;
            for (Element dA : Da) pA += dA.getWeight();
            double pB = 0.0;
            for (Element dB : Db) pB += dB.getWeight();

            BoundingBox Ra = null;
            BoundingBox Rb = null;

            if (bisection == HORIZONTAL) {
                double wRa = (pA / (pA + pB)) * R.width;
                double wRb = (pB / (pA + pB)) * R.width;

                float bh = (Da.get(Da.size() - 1).x + Db.get(0).x) / 2;
                Ra = new BoundingBox(R.x, R.y, bh - R.x, R.height);
                Rb = new BoundingBox(R.x + Ra.width, R.y, R.width - Ra.width, R.height);
                
                AffineTransform HRa = new AffineTransform(wRa/Ra.width, 0, 0, 1, R.x *(1-(wRa/Ra.width)), 0);
                for (Element dA : Da) HRa.transform(dA, dA);
                Ra.setBounds(HRa.createTransformedShape(Ra).getBounds2D());
                
                AffineTransform HRb = new AffineTransform(wRb/Rb.width, 0, 0, 1, (R.x + R.width)*(1-(wRb/Rb.width)), 0);
                for (Element dB : Db) HRb.transform(dB, dB);
                Rb.setBounds(HRb.createTransformedShape(Rb).getBounds2D());
                
            } else if (bisection == VERTICAL) {
                
                double hRa = (pA / (pA + pB)) * R.height;
                double hRb = (pB / (pA + pB)) * R.height;
                
                float bv = (Da.get(Da.size() - 1).y + Db.get(0).y) / 2;
                Ra = new BoundingBox(R.x, R.y, R.width, bv - R.y);
                Rb = new BoundingBox(R.x, R.y + Ra.height, R.width, R.height - Ra.height);
                
                AffineTransform VRa = new AffineTransform(1, 0, 0, hRa/Ra.height, 0, R.y *(1-(hRa/Ra.height)));
                for (Element dA : Da) VRa.transform(dA, dA);
                Ra.setBounds(VRa.createTransformedShape(Ra).getBounds2D());
                
                AffineTransform VRb = new AffineTransform(1, 0, 0, hRb/Rb.height, 0, (R.y + R.height)*(1-(hRb/Rb.height)));
                for (Element dB : Db) VRb.transform(dB, dB);
                Rb.setBounds(VRb.createTransformedShape(Rb).getBounds2D());
            }
            
            elementsAreas.addAll(alternateCut(Ra, Da, !bisection));
            elementsAreas.addAll(alternateCut(Rb, Db, !bisection));
        }

        return elementsAreas;
    }

    public List<BoundingBox> alternateCut(List<Element> D) {
        
        /*
         * normaliza input data in the visual Space area
         */
        normalize(D);

        /*
         * The direction of the first bisection is decided based on the width 
         * (R_w) and height (R_h) of R. If R_w > R_h the bisection is horizontal, 
         * vertical otherwise.
         */
        boolean bisection;
        if (visualSpace.width > visualSpace.height) {
            bisection = HORIZONTAL;
        } else {
            bisection = VERTICAL;
        }

        /*
         * NMap Alternate Cut Aproach
         */
        return alternateCut(visualSpace, D, bisection);
    }

    /*
     * 
     */
    private List<BoundingBox> equalWeight(BoundingBox R, List<Element> D) {
        List<BoundingBox> elementsAreas = new ArrayList<>();

        if (D.size() == 1) {
            BoundingBox bb = new BoundingBox(R.x, R.y, R.width, R.height, D.get(0));
            elementsAreas.add(bb);
        } else {

            //get the direction to bisects
            boolean bisection = VERTICAL;
            if (R.width > R.height) bisection = HORIZONTAL;
            
            if (bisection == HORIZONTAL) sortByX(D);
            else sortByY(D);

            double pA = 0.0, pB = 0.0, pAAux = 0.0, pBAux = 0.0;
            int cutElement = 1;
            for (Element d : D) pBAux += d.getWeight();
            
            double minDiff = Double.MAX_VALUE;
            for(int i = 1; i < D.size(); ++i){	
                pAAux += D.get(i - 1).getWeight(); 
                pBAux -= D.get(i - 1).getWeight();
                if(Math.abs(pAAux - pBAux) < minDiff){
                    minDiff = Math.abs(pAAux - pBAux);
                    cutElement = i;
                    pA = pAAux; 
                    pB = pBAux;
                }
            }
            
            List<Element> Da = D.subList(0, cutElement);
            List<Element> Db = D.subList(cutElement, D.size());

            BoundingBox Ra = null;
            BoundingBox Rb = null;

            if (bisection == HORIZONTAL) {
                double wRa = (pA / (pA + pB)) * R.width;
                double wRb = (pB / (pA + pB)) * R.width;

                float bh = (Da.get(Da.size() - 1).x + Db.get(0).x) / 2;
                Ra = new BoundingBox(R.x, R.y, bh - R.x, R.height);
                Rb = new BoundingBox(R.x + Ra.width, R.y, R.width - Ra.width, R.height);
                
                AffineTransform HRa = new AffineTransform(wRa/Ra.width, 0, 0, 1, R.x *(1-(wRa/Ra.width)), 0);
                for (Element dA : Da) HRa.transform(dA, dA);
                Ra.setBounds(HRa.createTransformedShape(Ra).getBounds2D());
                
                AffineTransform HRb = new AffineTransform(wRb/Rb.width, 0, 0, 1, (R.x + R.width)*(1-(wRb/Rb.width)), 0);
                for (Element dB : Db) HRb.transform(dB, dB);
                Rb.setBounds(HRb.createTransformedShape(Rb).getBounds2D());
                
            } else if (bisection == VERTICAL) {
                
                double hRa = (pA / (pA + pB)) * R.height;
                double hRb = (pB / (pA + pB)) * R.height;
                
                float bv = (Da.get(Da.size() - 1).y + Db.get(0).y) / 2;
                Ra = new BoundingBox(R.x, R.y, R.width, bv - R.y);
                Rb = new BoundingBox(R.x, R.y + Ra.height, R.width, R.height - Ra.height);
                
                AffineTransform VRa = new AffineTransform(1, 0, 0, hRa/Ra.height, 0, R.y *(1-(hRa/Ra.height)));
                for (Element dA : Da) VRa.transform(dA, dA);
                Ra.setBounds(VRa.createTransformedShape(Ra).getBounds2D());
                
                AffineTransform VRb = new AffineTransform(1, 0, 0, hRb/Rb.height, 0, (R.y + R.height)*(1-(hRb/Rb.height)));
                for (Element dB : Db) VRb.transform(dB, dB);
                Rb.setBounds(VRb.createTransformedShape(Rb).getBounds2D());
            }
            
            elementsAreas.addAll(equalWeight(Ra, Da));
            elementsAreas.addAll(equalWeight(Rb, Db));
        }

        return elementsAreas;
    }

    public List<BoundingBox> equalWeight(List<Element> D) {
        normalize(D);
        return equalWeight(visualSpace, D);
    }
}
