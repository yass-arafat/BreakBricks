/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bb;

import com.bb.pojo.Brick;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author yassir
 */
public class GenerateBrick {
    
    public int map[][];

    public GenerateBrick(int row, int col) {
        
        map = new int[row][col];
        
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                
                map[i][j]=1;
            }
        }
    }
    
    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }

    public void draw(Graphics2D g, int row, int col) {

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                
                if (map[r][c] == 1) {
                    //filling the rectangle with white color
                    {
                        g.setColor(Color.white);
                        g.fillRect(c * Brick.brickWidth + 120, r * Brick.brickHeight + 50, Brick.brickWidth, Brick.brickHeight);
                    }

                    //drawing a line with width = 3
                    g.setStroke(new BasicStroke(3));

                    //drawing the border of the rectangle with black color
                    {
                        g.setColor(Color.black);
                        g.drawRect(c * Brick.brickWidth + 120, r * Brick.brickHeight + 50, Brick.brickWidth, Brick.brickHeight);
                    }
                }

            }
        }
        
        
    }
}
