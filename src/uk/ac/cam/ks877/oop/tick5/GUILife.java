package uk.ac.cam.ks877.oop.tick5;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class GUILife extends JFrame implements ListSelectionListener {
    private World world = null;
    private PatternStore store;
    private ArrayList<World> cachedWorlds = new ArrayList<>();
    private GamePanel gamePanel;
    private JButton playButton = new JButton("Play");
    private java.util.Timer timer;
    private boolean playing;
    private JPanel gamePanelBig;
    private JLabel genCount;

    public GUILife(PatternStore ps) throws IOException, PatternFormatException {
        super("Game of Life");
        store=ps;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024,768);
        add(createPatternsPanel(),BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(),BorderLayout.CENTER);
    }

    private void moveBack() {
        if (world == null) {
            System.out.println("Please select a pattern to play (l to list):");
        }
        else {
            if (world.getGenerationCount() == 0) {
                this.world = cachedWorlds.get(0);
                gamePanel.display(this.world);
            } else {
                int currentGen = world.getGenerationCount();
                world = cachedWorlds.get(currentGen - 1);
                gamePanel.display(this.world);
            }
            updateGenLabel();
        }
    }

    private void moveForward() {
        if (world == null) {
            System.out.println("Please select a pattern to play (l to list):");
        }
        // if next generation hasn't been already computed then use function
        // add world to cachedWorlds
        else if(world.getGenerationCount() + 1 >= cachedWorlds.size()) {
            try {
                world = copyWorld(true);
                world.nextGeneration();
                cachedWorlds.add(world);
                gamePanel.display(this.world);
            }
            catch(CloneNotSupportedException e) {
                System.out.println("Something went wrong with cloning");
            }
        }
        else {
            int currentGen = world.getGenerationCount();
            world = cachedWorlds.get(currentGen + 1);
            gamePanel.display(this.world);

        }
        updateGenLabel();
    }

    private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
        if(!useCloning) {
            if(this.world instanceof ArrayWorld) {
                return new ArrayWorld((ArrayWorld)world);
            }
            else {
                return new PackedWorld((PackedWorld)world);
            }
        }
        else {
            return world.clone();
        }
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {
        gamePanel = new GamePanel(this.world);
        gamePanel.display(this.world);
        addBorder(gamePanel,"Game Panel");
        String genLabel = getGenLabel();
        genCount = new JLabel(genLabel);
        genCount.setFont(new Font("Arial", Font.PLAIN, 15));
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(genCount, BorderLayout.PAGE_END);
        gamePanelBig = gamePanel;
        return gamePanel;
    }

    private String getGenLabel() {
        if(world != null) {
            int gen = world.getGenerationCount();
            return "Generation: " + Integer.toString(gen);
        }
        return "";
    }

    private void updateGenLabel() {
        genCount.setText(getGenLabel());
    }

    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel(new GridLayout());
        addBorder(patt,"Patterns");
        DefaultListModel<Pattern> pat = new DefaultListModel<>();
        List<Pattern> patterns = store.getPatternsNameSorted();
        int length = patterns.size();
        for(int i=0; i<length; i++) {
            Pattern element = patterns.get(i);
            pat.addElement(element);
        }
        JList<Pattern> patList = new JList<>(pat);
        patList.setFont(new Font("Arial", Font.PLAIN, 15));
        patList.addListSelectionListener(this::valueChanged);
        patt.add(new JScrollPane(patList));

        // TODO
        return patt;
    }

    private JPanel createControlPanel() {
        JPanel ctrl =  new JPanel(new GridLayout());
        addBorder(ctrl,"Controls");
        JButton back = new JButton("< Back");
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.addActionListener(e -> {
            // addGenerationCount();
            moveBack();
            stopPlaying();
        });
        ctrl.add(back, BorderLayout.EAST);
        ctrl.add(back);
        playButton.addActionListener(e -> runOrPause());
        playButton.setFont(new Font("Arial", Font.PLAIN, 20));
        ctrl.add(playButton);
        JButton forward = new JButton("Forward >");
        forward.setFont(new Font("Arial", Font.PLAIN, 20));
        forward.addActionListener(e -> {
            // addGenerationCount();
            moveForward();
            stopPlaying();
        });
        ctrl.add(forward, BorderLayout.WEST);

        return ctrl;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (playing) {
            stopPlaying();
        }
        JList<Pattern> list = (JList<Pattern>) e.getSource();
        Pattern pattern = list.getSelectedValue();
        if (pattern.getHeight() * pattern.getWidth() <= 64) {
            world = new PackedWorld(pattern);
        }
        else {
            world = new ArrayWorld(pattern);
        }
        updateGenLabel();
        cachedWorlds = new ArrayList<>();
        cachedWorlds.add(world);
        gamePanel.display(this.world);


    }

    private void runOrPause() {
        if (playing) {
            timer.cancel();
            playing=false;
            playButton.setText("Play");
        }
        else {
            playing=true;
            playButton.setText("Stop");
            timer = new java.util.Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    moveForward();
                }
            }, 0, 500);
        }
    }

    private void stopPlaying() {
        timer.cancel();
        playing=false;
        playButton.setText("Play");
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException, PatternFormatException{
        try {
            PatternStore ps = new PatternStore("https://www.cl.cam.ac.uk/teaching/1819/OOProg/ticks/life.txt");
            GUILife gui = new GUILife(ps);
            gui.setVisible(true);
        }
        catch (IOException ioe) {
            System.out.println("Failed to load pattern store");
        }
    }
}