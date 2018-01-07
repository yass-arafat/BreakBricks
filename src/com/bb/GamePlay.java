/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bb;

import com.bb.pojo.Brick;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author yassir
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener{
    
    int row = 6;
    int col = 10;
    
    private boolean play = false;

    // Pedal info
    private int pedalCurrentXPos ;
    private int pedalCurrentYPos ;
    
    // Ball info
    private int ballCurrentXPos ;
    private int ballCurrentYPos ;
    private int ballXdir ;
    private int ballYdir ;
    
    int widthOfFrame ;
    int heightOfFrame ;
    
    GenerateBrick generateBrick;
    GenerateBallAndPedal ballAndPedal;
    
    private int delay;
    
    private final Timer timer;

    private int totalBricks ;
    private int lives;
    private int score;
    private boolean isGameOver = false;

    public GamePlay() {
        
        init();
        
        generateBrick = new GenerateBrick(row, col);
        ballAndPedal = new GenerateBallAndPedal();
        
        addKeyListener(this);
        
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        setDelay();
        
        timer = new Timer(delay, this);
        timer.start();
    }
    
    
    @Override
    public void paint(Graphics g){
        
        setBackground(g);
        
        generateBrick.draw((Graphics2D) g, row, col);
        
        ballAndPedal.draw((Graphics2D) g, pedalCurrentXPos, pedalCurrentYPos, ballCurrentXPos, ballCurrentYPos);
        
        setLives(g);
        
        setScores(g);

        if(isGameOver || totalBricks < 1){

            setGameOverText(g);

            setRestartGameText(g);
        }
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedSpace(e);
        pressedLeftArrow(e);
        pressedRightArrow(e);
        pressedEnter(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        timer.start();

        if(totalBricks < 1 && play)
        {
            play = false;
            isGameOver = true;
        }

        if(isBallOutsideBoundary()){
            play = false;
            isGameOver = true;
        }
        
        if(play){
            //if intersects with pedal then change the direction of ball
            if(new Rectangle(ballCurrentXPos, ballCurrentYPos, 20, 20).intersects(new Rectangle(pedalCurrentXPos, pedalCurrentYPos, 50, 8))){
                ballYdir  = -ballYdir;
            }
            
            destroyBrickIfIntersectsWithBall();
            
            changeBallPosition();
            
            changeBallDirection();
        }
        
        repaint();
    }

    private boolean isBallOutsideBoundary() {
        return ballCurrentYPos > heightOfFrame;
    }

    private void destroyBrickIfIntersectsWithBall() {
        
        int map[][] = generateBrick.map;
        
        A: for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    
                    if(map[i][j] > 0){
                        
                        int brickX = j * Brick.brickWidth + 120;
                        int brickY = i * Brick.brickHeight + 50;
                        int brickWidth = Brick.brickWidth;
                        int brickHeight = Brick.brickHeight;
                        
                        Rectangle brickrect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballrect = new Rectangle(ballCurrentXPos, ballCurrentYPos, 20, 20);
                        
                        if(ballrect.intersects(brickrect)){
                            
                            generateBrick.setBrickValue(0, i, j);
                            
                            totalBricks--;
                            score += 5;
                            
                            if(ballCurrentXPos + 19 <= brickrect.x || ballCurrentYPos + 1 >= brickrect.x + brickrect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            
                            break A;
                        }
                    }
                }
            }
    }

    private void pressedSpace(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            play = true;
        }
    }

    private void pressedLeftArrow(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moveLeft();
        }
    }

    private void pressedRightArrow(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moveRight();
        }
        
    }
    
    private void pressedEnter(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER){

            if(!play ){

                isGameOver = false;
                
                reset();
            }
        }
    }

    private void moveLeft() {
        
        if(isPedalCrossedLeftBoundary()){

            pedalCurrentXPos  = 20;

            if(!play)
            ballCurrentXPos = ( pedalCurrentXPos + 50 ) /2 ;

        }else{

            if(!play){
                ballCurrentXPos -= 20;
            }

            pedalCurrentXPos -= 20;
        }
    }

    private void moveRight() {
        
        if(isPedalCrossedRightBoundary()){
            
            pedalCurrentXPos = widthOfFrame - 50 ; // hold the position then

            if(!play)
            ballCurrentXPos = pedalCurrentXPos + 15 ;

        }else{

            if(!play){
                ballCurrentXPos += 20;
            }

            pedalCurrentXPos += 20; // move pedal 20 to right
        }
    }
    
    private void changeBallPosition() {
        ballCurrentXPos += ballXdir;
        ballCurrentYPos += ballYdir;
    }

    private void changeBallDirection() {
        
        if (ballCurrentXPos < 0) {
            ballXdir = -ballXdir;
        }
        if (ballCurrentYPos < 0) {
            ballYdir = -ballYdir;
        }
        if (ballCurrentXPos > widthOfFrame - 25) {
            ballXdir = -ballXdir;
        }
    }

    private void setBackground(Graphics g) {
        //background
        g.setColor(Color.GRAY);
        g.fillRect(1, 1, widthOfFrame, heightOfFrame);
    }

    private void setDelay() {
        delay = 8;
    }

    private void init() {
        
        initBallPosition();
        
        initBallDirection();
        
        initPedalPosition();

        if(totalBricks < 1 ) initBricksScoreAndLife();
        
        initFrame();
    }
    
    private void initBallPosition() {
        ballCurrentXPos = 225;
        ballCurrentYPos = 530;
    }

    private void initBallDirection() {
        ballXdir = -1;
        ballYdir = -2;
    }

    private void initPedalPosition() {
        pedalCurrentXPos = 210;
        pedalCurrentYPos = 550;
    }

    private void initFrame() {
        widthOfFrame = 900;
        heightOfFrame = 600;
    }

    private boolean isPedalCrossedRightBoundary() {
        return ( pedalCurrentXPos >= widthOfFrame - 55 ) ;
    }

    private boolean isPedalCrossedLeftBoundary() {
        return ( pedalCurrentXPos < 20 ) ;
    }

    private void reset() {

        if(totalBricks < 1){
            
            startNewGame();
        }
        else {
            
            lives++;
        }


        init();
        
        repaint();
    }

    private void startNewGame() {

        initBricksScoreAndLife();
        
        generateBrick = new GenerateBrick(row, col);
    }

    private void initBricksScoreAndLife() {
        totalBricks = row * col;
        score = 0;
        lives = 0;
    }

    private void setLives(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Lives: "+lives, 350, 30);
    }

    private void setScores(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, widthOfFrame - 200, 30);
    }

    private void setGameOverText(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Game Over \n Your Score is : "+score, widthOfFrame / 2 - 100, heightOfFrame / 2);
    }

    private void setRestartGameText(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Press Enter To Restart Game ", widthOfFrame / 2 - 100, heightOfFrame / 2 + 100);
    }


}
