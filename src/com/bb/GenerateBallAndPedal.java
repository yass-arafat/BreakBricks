/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bb;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author yassir
 */
public class GenerateBallAndPedal {

    public void draw(Graphics2D g, int pedalXpos, int pedalYPos, int ballXPos, int ballYPos) {
        
        //drwaing pedal
        {
            g.setColor(Color.black);
            g.fillRect(pedalXpos, pedalYPos, 50, 8);
        }
        
        //drwaing ball
        {
            g.setColor(Color.yellow);
            g.fillOval(ballXPos, ballYPos, 20, 20);
        }
    }
    
}
