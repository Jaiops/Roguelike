package game;

import items.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Johan on 2016-02-17.
 */

public class GamePanel extends JPanel {
    static int TILESIZE = 32;
    private Game gameInstance;
    private FieldOfView fov;
    private Viewport vp;
    MessageLogger ml;
    BufferedImage image;

    public GamePanel() {

        gameInstance = new Game();
        URL imageURL = getClass().getResource("/DungeonCrawl_ProjectUtumnoTileset.png");
        try {
            image = ImageIO.read(imageURL);

        } catch (IOException e) {
            System.err.println("Error when loading sprite");
            e.printStackTrace();
        }
        fov = new FieldOfView();
        try {
            vp = new Viewport(20, 20, gameInstance.getM());

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public synchronized void keyPressed(KeyEvent e) {
        gameInstance.getAction(e);
        repaint();
    }

    private void setSeenTiles(ArrayList<Position> pos) {
        for (Position p : pos) {
            gameInstance.getM().getTiles()[p.getX()][p.getY()].setSeen();
        }

    }

    //TODO Cleanup possible?
    private void drawTiles(Graphics g) {

        Map m = gameInstance.getM();
        Tile[][] tiles = m.getTiles();
        ArrayList<Position> fovPos = fov.calculateFov(m, gameInstance.getC().getPos());
        setSeenTiles(fovPos);
        vp.setNewPos(gameInstance.getC().getPos());

        for (int rows = 0; rows < vp.getSizeY(); rows++) {
            for (int columns = 0; columns < vp.getSizeX(); columns++) {
                int y = rows + vp.getCurrentY();
                int x = columns + vp.getCurrentX();
                Position p = new Position(x, y);

                if (tiles[y][x].isBlocking()) {
                    drawSprite(g, new Position(x, y), tiles[y][x].getSpritePosition());

                    if (!fovPos.contains(p)) {
                        int alpha = 150; // 50% transparent
                        Color myColour = new Color(0, 0, 0, alpha);
                        g.setColor(myColour);
                        g.fillRect(columns * TILESIZE, rows * TILESIZE, TILESIZE, TILESIZE);
                    }
                } else {
                    if (fovPos.contains(p)) {

                        if (tiles[y][x].getClass() == Staircase.class) {
                            Staircase staircase = (Staircase) tiles[y][x];
                            drawSprite(g, new Position(x, y), staircase.getSpritePosition());
                        } else {

                            drawSprite(g, new Position(x, y), tiles[y][x].getSpritePosition());
                        }

                        if (tiles[y][x].getItems().size() > 0) {
                            g.setColor(Color.orange);
                            drawSprite(g, new Position(x, y), tiles[y][x].getItems().get(0).mapSprite);
                        }

                    } else {

                        drawSprite(g, new Position(x, y), tiles[y][x].getSpritePosition());
                        int alpha = 150; // 50% transparent
                        Color myColour = new Color(0, 0, 0, alpha);
                        g.setColor(myColour);
                        g.fillRect(columns * TILESIZE, rows * TILESIZE, TILESIZE, TILESIZE);

                    }
                }

                if (!gameInstance.getM().getTiles()[x][y].isSeen()) {
                    g.setColor(Color.black);
                    g.fillRect(columns * TILESIZE, rows * TILESIZE, TILESIZE, TILESIZE);

                }
            }
        }
    }

    private void drawInventory(Graphics g) {
        int index = gameInstance.getIs().getIndex();
        g.setColor(Color.black);
        g.fillRect(10, 10, 150, 150);
        int offset = 1;
        for (Item i : gameInstance.getC().getInventory()) {
            String itemString = i.name;
            if (index == offset - 1) {
                g.setColor(Color.yellow);

                int itemStatYOffset = 32;
                g.drawString("Stats: ", 300, itemStatYOffset);
                itemStatYOffset += 20;

                if (i.damage != 0) {

                    g.drawString("Attack: " + i.damage, 300, itemStatYOffset);
                    itemStatYOffset += 20;
                }
                if (i.defense != 0) {

                    g.drawString("Defense: " + i.defense, 300, itemStatYOffset);
                    itemStatYOffset += 20;

                }
                if (i.health != 0) {

                    g.drawString("Healing: " + i.health, 300, itemStatYOffset);
                    itemStatYOffset += 20;

                }
                if (i.itemSpeed != 0) {

                    g.drawString("Item Speed: " + i.itemSpeed, 300, itemStatYOffset);
                    itemStatYOffset += 20;

                }

            } else {
                g.setColor(Color.white);
            }

            g.drawString(itemString, 64 + 4, 16 + (32 * offset));
            drawSpriteWithoutViewPort(g, new Position(1, offset), i.mapSprite);

            offset++;
        }

    }

    private void drawMonsters(Graphics g) {
        //Player FoV
        ArrayList<Position> fovPos = fov.calculateFov(gameInstance.getM(), gameInstance.getC().getPos());

        CopyOnWriteArrayList<Monster> monsters = gameInstance.getM().getMonsters();

        for (Monster mon : monsters) {
            if (vp.withinViewport(mon.getPos()) && fovPos.contains(mon.getPos())) {
                drawSprite(g, mon.getPos(), mon.getSprite());
                drawHealthBar(g,
                        (mon.getPos().getX() - vp.getCurrentX()) * 32,
                        (mon.getPos().getY() - vp.getCurrentY()) * 32, mon);

            }
        }
    }


    /**
     * Used for everything on the map from tiles to monster
     *
     * @param g
     * @param pos    - Position on the map
     * @param sprite - Position for specific sprite on the spritesheet.
     */
    private void drawSprite(Graphics g, Position pos, Position sprite) {
        g.drawImage(image,
                (pos.getX() - vp.getCurrentX()) * TILESIZE,
                (pos.getY() - vp.getCurrentY()) * TILESIZE,
                (pos.getX() - vp.getCurrentX() + 1) * TILESIZE,
                (pos.getY() - vp.getCurrentY() + 1) * TILESIZE,

                sprite.getX() * TILESIZE,
                sprite.getY() * TILESIZE,
                (sprite.getX() + 1) * TILESIZE,
                (sprite.getY() + 1) * TILESIZE,
                null
        );
    }

    /**
     * Used for User interface, menus and stuff
     *
     * @param g
     * @param pos    - Position on the map
     * @param sprite - Position for specific sprite on the spritesheet.
     */
    private void drawSpriteWithoutViewPort(Graphics g, Position pos, Position sprite) {
        g.drawImage(image,
                (pos.getX() * TILESIZE),
                (pos.getY() * TILESIZE),
                (pos.getX() + 1) * TILESIZE,
                (pos.getY() + 1) * TILESIZE,

                sprite.getX() * TILESIZE,
                sprite.getY() * TILESIZE,
                (sprite.getX() + 1) * TILESIZE,
                (sprite.getY() + 1) * TILESIZE,
                null
        );
    }

    /**
     * Main draw function
     *
     * @param g Graphics used for drawing.
     */
    public synchronized void draw(Graphics g) {
        drawBackground(g);
        drawTiles(g);
        drawMonsters(g);

        g.setColor(Color.blue);
        Character c = gameInstance.getC();

        Position pos = c.getPos();
        Position sprite = c.getSprite();
        //DRAWS THE PLAYER
        drawSprite(g, pos, sprite);
        if (c.getArmor() != null) {

            drawSprite(g, pos, c.getArmor().equipSprite);
        }
        if (c.getMainHand() != null) {

            drawSprite(g, pos, c.getMainHand().equipSprite);
        }

        drawPlayerInformation(g, c);

        if (gameInstance.getIs() != null) {
            drawInventory(g);
        }
        drawMessages(g);

        drawDebug(g);

    }

    private void drawPlayerInformation(Graphics g, Character c) {
        g.drawString("Health: " + gameInstance.getC().getCurrentHealth() + "/" + gameInstance.getC
                ().getMaxHealth(), 500, 500);
        String turn = String.format("%.1f", c.getTurn());
        g.drawString("Turn: " + turn, 500, 530);
        g.drawString("GameLevel: " + gameInstance.currentMap, 500, 560);
        g.drawString("Player level: " + gameInstance.getC().getLevel(), 650, 500);
        g.drawString("Experience: " + gameInstance.getC().getExperience() + "/" + gameInstance.getC().getExperienceNeeded(), 650, 530);
        g.drawString("Attack: " + gameInstance.getC().getDamage(), 650, 540);
        g.drawString("Defense: " + gameInstance.getC().getDefense(), 650, 550);
    }

    private void drawDebug(Graphics g) {
        testMonsters(g, gameInstance.getM());
        testItems(g, gameInstance.getM());
        testBuffs(g, gameInstance.getM());
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRootPane().getWidth(), getHeight());
    }

    private void drawHealthBar(Graphics g, int x, int y, Character ch) {

        float percentage = (float) ch.getCurrentHealth() / (float) ch
                .getMaxHealth();
        if (percentage > 0.7) {
            g.setColor(Color.green);
        } else if (percentage > 0.4) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(x + 3, y + 26, (int) (26 * percentage), 5);
        g.setColor(Color.black);
        g.drawRect(x + 3, y + 26, 26, 5);
    }

    private void drawMessages(Graphics g) {
        g.setColor(Color.WHITE);
        ArrayList<String> msgs = MessageLogger.getInstance().getLog();

        for (int i = 0; i < 7; i++) {
            try {

                g.drawString(msgs.get(msgs.size() - i), 10, 800 - (i * 10));
            } catch (Exception e) {

            }
        }
    }

    private void testMonsters(Graphics g, Map m) {
        int i = 0;
        g.setColor(Color.WHITE);
        for (Monster n : m.getMonsters()) {
            g.drawString(n.getName() + "Turn: " + n.getTurn(), 600, 19 + (i * 20));
            i++;
        }

    }

    private void testBuffs(Graphics g, Map m) {
        int i = 0;
        g.setColor(Color.WHITE);
        g.drawString("Current Map: " + gameInstance.getC().getSpeed(), 700, 40);

        for (Buff b : gameInstance.getC().getBuffs()) {
            g.drawString("Buff : " + "Turn: " + b.getTurns(), 700, 19 + (i * 20));
            i++;
        }

    }

    private void testItems(Graphics g, Map m) {
        g.setColor(Color.WHITE);
        int x = 0;
        for (int i = 0; i < m.getTiles().length; i++) {
            for (int j = 0; j < m.getTiles().length; j++) {
                CopyOnWriteArrayList<Item> items = m.getTiles()[i][j].getItems();
                if (items.size() > 0) {
                    for (Item it : items) {
                        g.drawString(it.name + " " + i + " " + j, 600, 300 + (x * 20));
                        x++;
                    }
                }
            }
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
        g.setColor(Color.black);
    }
}
