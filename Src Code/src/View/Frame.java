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
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import nmap.BoundingBox;

public class Frame extends JFrame{

    private static final long serialVersionUID = -2820128471828412433L;
    private final Panel canvas;
    private final int canvasWidth;
    private final int canvasHeight;

    public Frame(int canvasWidth, int canvasHeight, List<BoundingBox> regions, String title) {
        this.canvasWidth = canvasWidth + 40;
        this.canvasHeight = canvasHeight + 40;

        setTitle(title);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(this.canvasWidth + 50,this.canvasHeight + 70));
        setMinimumSize(new java.awt.Dimension(this.canvasWidth + 50,this.canvasHeight + 70));
        setPreferredSize(new java.awt.Dimension(this.canvasWidth + 50,this.canvasHeight + 70));
        setLocationRelativeTo(null);

        canvas = new Panel(regions);
        canvas.setBackground(Color.WHITE);
        canvas.setPreferredSize(new Dimension(this.canvasWidth,this.canvasHeight));

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING
            ).addGap(0,this.canvasWidth, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING
            ).addGap(0,this.canvasHeight, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(
                    canvas,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE
                ).addContainerGap(264, Short.MAX_VALUE)
            )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING
            ).addGroup(
                layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(
                    canvas,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE
                ).addContainerGap(128, Short.MAX_VALUE)
            )
        );

        pack();
    }
}
