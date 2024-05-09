/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.im.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author migue
 */
public class ImagePanelPGM extends JPanel{
    public final int WIDTH = 640;
    public final int HEIGHT = 360;
    private PGMImage img;
    private int heightMove = 0;
    private int widthMove = 0;
    private int resolution = 1;
    
    public ImagePanelPGM(Image img) {
        this.img = (PGMImage) img;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int color = 0;
        for (int h = 0; h < HEIGHT; h++) {
            for (int w = 0; w < WIDTH; w++) {
                g.setColor(new Color(color, color, color));
                g.fillRect(w + widthMove, h + heightMove, 1, 1);
            }
        }
        int width = (int)img.getWidth();
        int height = (int)img.getHeight();
        int[][] arr = img.pgmToIntArray();
//        System.out.println(heightMove);
//        System.out.println(widthMove);
        int widthMove = this.widthMove + (this.WIDTH - width*resolution)/2;
        int heightMove = this.heightMove + (this.HEIGHT - height*resolution)/2;
//        System.out.println(heightMove);
//        System.out.println(widthMove);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                color = arr[h][w];
                g.setColor(new Color(color, color, color));
                g.fillRect(w*resolution + widthMove, h*resolution + heightMove, resolution, resolution);
            }
        }
        
    }

    public PGMImage getImg() {
        return img;
    }

    public void setImg(PGMImage img) {
        this.img = img;
    }

    public int getHeightMove() {
        return heightMove;
    }

    public void setHeightMove(int heightMove) {
        this.heightMove = heightMove;
    }

    public int getWidthMove() {
        return widthMove;
    }

    public void setWidthMove(int widthMove) {
        this.widthMove = widthMove;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}
