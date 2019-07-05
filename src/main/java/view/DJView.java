package view;

import controller.ControllerInterface;
import model.BPMObserver;
import model.BeatModelInterface;
import model.BeatObserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DJView implements ActionListener, BeatObserver, BPMObserver {
    
    BeatModelInterface model;
    ControllerInterface controller;
    
    // BPM and beat view
    JFrame viewFrame;
    JPanel viewPanel;
        JLabel bpmOutputLabel;
        BeatBar beatBar;
        
    //User Interface controls view
    
    JFrame controlFrame;
    JPanel controlPanel;
        JLabel bpmLabel;
        JTextField bpmTextField;
        JButton setBPMButton;
        JButton increaseBPMButton;
        JButton decreaseBPMButton;
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem startMenuItem;
        JMenuItem stopMenuItem;
    
    public DJView(ControllerInterface controller, BeatModelInterface model) {
        this.model = model;
        this.controller = controller;
    }
    
    public void createView(){
        viewFrame = new JFrame("View");
        viewPanel = new JPanel(new GridLayout(1,2));
        
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(100,80);
        
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
            beatBar = new BeatBar();
            beatBar.setValue(0);
        JPanel bpmPanel = new JPanel(new GridLayout(2,1));
            bpmPanel.add(beatBar);
            bpmPanel.add(bpmOutputLabel);
        viewPanel.add(bpmPanel);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
    }
    public void createControls(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100,80));
        
        controlPanel = new JPanel(new GridLayout(1,2));
        
        menuBar = new JMenuBar();
            menu = new JMenu("DJ Control");
                startMenuItem = new JMenuItem("start");
                menu.add(startMenuItem);
                startMenuItem.addActionListener(e -> controller.start());
                
                stopMenuItem = new JMenuItem("stop");
                menu.add(stopMenuItem);
                stopMenuItem.addActionListener(e -> controller.stop());
                
                JMenuItem exit = new JMenuItem("quit");
                exit.addActionListener(e -> System.exit(0));
                menu.add(exit);
            menuBar.add(menu);
        controlFrame.setJMenuBar(menuBar);
        
        bpmLabel = new JLabel("Enter BPM : ", SwingConstants.RIGHT);
        bpmTextField = new JTextField(2);
        setBPMButton = new JButton("set");
        setBPMButton.setSize(new Dimension(10,40));
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");
        
        setBPMButton.addActionListener(this::actionPerformed);
        increaseBPMButton.addActionListener(this::actionPerformed);
        decreaseBPMButton.addActionListener(this::actionPerformed);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
            buttonPanel.add(decreaseBPMButton);
            buttonPanel.add(increaseBPMButton);
            
        JPanel enterPanel = new JPanel(new GridLayout(1,2));
            enterPanel.add(bpmLabel);
            enterPanel.add(bpmTextField);
        
        JPanel insideControlPanel = new JPanel(new GridLayout(3,1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
        
        controlPanel.add(insideControlPanel);
        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
        
        controlFrame.pack();
        controlFrame.setVisible(true);
        
    }
    
    public void enableStopMenuItem(){
        stopMenuItem.setEnabled(true);
    }
    
    public void disableStopMenuItem(){
        stopMenuItem.setEnabled(false);
    }
    
    public void enableStartMenuItem(){
        startMenuItem.setEnabled(true);
    }
    
    public void disableStartMenuItem(){
        startMenuItem.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == setBPMButton){
            int bpm = Integer.parseInt(bpmTextField.getText());
            controller.setBPM(bpm);
        }
        else if(e.getSource() == increaseBPMButton){
            controller.increaseBPM();
        }
        else if(e.getSource() == decreaseBPMButton){
            controller.decreaseBPM();
        }
    
    }
    
    @Override
    public void updateBPM() {
        if (model == null){
            return;
        }
        int bpm = model.getBPM();
        if(bpm == 0){
            bpmOutputLabel.setText("OffLine");
        }
        else {
            bpmOutputLabel.setText("Current BPM :" + bpm);
        }
    
    }
    
    @Override
    public void updateBeat() {
        
        if(beatBar!=null)
            beatBar.setValue(100);
    
    }
}
