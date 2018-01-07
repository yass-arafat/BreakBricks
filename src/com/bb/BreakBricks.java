/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bb;

import java.awt.Color;
import javax.swing.JFrame;

/**
 * contains game Frame
 * initialize and revoke Gameplay
 * @author yassir
 */
public class BreakBricks extends JFrame{
    
    public static void main(String[] args) {
        
        int startingPosX = 240;
        int startingPosY = 10;
        int widthOfFrame = 900;
        int heightOfFrame = 600;
        
        //creating frame
        {
            JFrame jFrame = new JFrame("BreakBricks");
            
            jFrame.setBounds(startingPosX, startingPosY, widthOfFrame, heightOfFrame);
            jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
            jFrame.setResizable(false);
            jFrame.setVisible(true);
            
            jFrame.add(new GamePlay());
        }
    }
    
}
