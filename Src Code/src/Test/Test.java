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

package Test;

import View.Frame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nmap.BoundingBox;
import nmap.Element;
import nmap.NMap;

public class Test {

    //parse CSV file
    private static List<Element> loadCSV(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Element> data = new ArrayList<>();
        Random random = new Random();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] cels = line.split(cvsSplitBy);
                Element d = null;
                
                if (cels.length == 4) {
                    d = new Element(
                        Float.parseFloat(cels[1]),
                        Float.parseFloat(cels[2]),
                        Integer.parseInt(cels[0]),
                        Float.parseFloat(cels[3]),
                        random.nextFloat() //random Weight for each element
                    );
                } else if (cels.length == 5) {
                    d = new Element(
                        Float.parseFloat(cels[1]),
                        Float.parseFloat(cels[2]),
                        Integer.parseInt(cels[0]),
                        Float.parseFloat(cels[3]),
                        Float.parseFloat(cels[4])
                    );
                } else {
                    System.err.println("Problems while parsing csv file");
                    System.exit(-1);
                }
                
                data.add(d);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Problems while parsing csv file");
            System.err.println(e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Problems while parsing csv file");
            System.err.println(e.getMessage());
            System.exit(-1);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        return data;
    }

    public static void main(String[] args) {

        String csvFile = "../DataSet/caso03.csv";
        List<Element> data = loadCSV(csvFile);
        
        int visualSpaceWidth  = 800;
        int visualSpaceHeight = 600;

        NMap nmap = new NMap(visualSpaceWidth, visualSpaceHeight);
        
        //NMap Alternate Cut Aproach
        List<BoundingBox> ac = nmap.alternateCut(data);
        Frame frameAC = new Frame(visualSpaceWidth, visualSpaceHeight, ac, "NMap Alternate Cut");
        frameAC.setVisible(true);
        
        //NMap Equal Weights Aproach
        List<BoundingBox> ew = nmap.equalWeight(data);
        Frame frameEW = new Frame(visualSpaceWidth, visualSpaceHeight, ew, "NMap Equal Weights");
        frameEW.setVisible(true);
    }
}
