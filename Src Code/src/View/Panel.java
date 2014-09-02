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

package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import nmap.BoundingBox;

public class Panel extends JPanel {

    private static final long serialVersionUID = 3836375349724002422L;
    private final List<BoundingBox> regions;
    private final HashMap<Float, Color> colorMap;

    public Panel(List<BoundingBox> regions) {
        this.regions = regions;
        colorMap = new HashMap<>();
    }
    
    private Color getRandColor(){
        Random rand = new Random();
        // Will produce only bright / light colours:
        float r = (float) (rand.nextFloat()*0.5 + 0.5);
        float g = (float) (rand.nextFloat()*0.5 + 0.5);
        float b = (float) (rand.nextFloat()*0.5 + 0.5);
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        
        regions.stream().forEach((b) -> {
            float klass = b.getElement().getKlass();
            if(colorMap.containsKey(klass)){
                g.setColor(colorMap.get(klass));
            }else{
                Color c = getRandColor();
                colorMap.put(klass, c);
                g.setColor(c);
            }
            g.fillRect((int)b.x + 20, (int)b.y+20, (int)b.width, (int)b.height);
        });
        
        regions.stream().forEach((b) -> {
            g.setColor(Color.black);
            g.drawRect((int)b.x + 20, (int)b.y+20, (int)b.width, (int)b.height);
        });
        
        g.drawString("(0,0)", 0, 15);
    }
}
