import javax.swing.*;    // make the Swing GUI classes available
import java.awt.*;       // used for Color and GridLayout classes
import java.awt.event.*; // used for ActionEvent and  ActionListener classes

/**
 * This applet demonstrates several GUI components that are available in the
 * Swing GUI library.
 */
public class GUIDemo extends JApplet implements ActionListener {

   private JTextArea transcript; // a message will be posted to this text area
                                 // each time an event is generated by some
                                 // some user action

   public void init() { // set up the applet

      JPanel content = new JPanel();
      setContentPane(content);

      content.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

      content.setLayout(new GridLayout(1, 2, 3, 3));
           // I will put the transcript area in the right half of the
           // applet. The left half will be occupied by a grid of
           // four lines. Each line contains a component and
           // a label for that component. Note that for a JApplet,
           // any components added to the applet should be put into
           // its "contentPane" rather than directly into the applet.

      transcript = new JTextArea();
      transcript.setEditable(false);
      transcript.setMargin(new Insets(4, 4, 4, 4));
      JPanel left = new JPanel();
      left.setLayout(new GridLayout(4, 2, 3, 3));
      left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      content.add(left);
      content.add(new JScrollPane(transcript));

      JLabel lab = new JLabel("Push Button:   ", JLabel.RIGHT);
      left.add(lab);
      JButton b = new JButton("Click Me!");
      b.addActionListener(this);
      left.add(b);

      lab = new JLabel("Checkbox:   ", JLabel.RIGHT);
      left.add(lab);
      JCheckBox c = new JCheckBox("Click me!");
      c.addActionListener(this);
      left.add(c);

      lab = new JLabel("Text Field:   ", JLabel.RIGHT);
      left.add(lab);
      JTextField t = new JTextField("Type here!");
      t.addActionListener(this);
      left.add(t);

      lab = new JLabel("Pop-up Menu:   ", JLabel.RIGHT);
      left.add(lab);
      JComboBox m = new JComboBox();
      m.addItem("First Option");
      m.addItem("Second Option");
      m.addItem("Third Option");
      m.addItem("Fourth Option");
      m.addActionListener(this);
      left.add(m);

   } // end init()

   private void post(String message) { // add a line to the transcript
      transcript.append(message + "\n");
   }

   /**
    * Respond to an ActionEvent from one of the GUI components in the applet.
    * In each case, a message about the event is posted to the transcript.
    * (This method is part of the ActionListener interface.)
    */
   public void actionPerformed(ActionEvent evt) {
      Object target = evt.getSource(); // which component produced this event?
      if (target instanceof JButton) {
         post("Button was clicked.\n");
      } else if (target instanceof JTextField) {
         post("Pressed return in TextField\nwith contents:\n    "
                                          + evt.getActionCommand() + "\n");
      } else if (target instanceof JCheckBox) {
         if (((JCheckBox) target).isSelected())
            post("Checkbox was turned on.\n");
         else
            post("Checkbox was turned off.\n");
      } else if (target instanceof JComboBox) {
         Object item = ((JComboBox) target).getSelectedItem();
         post("Item \"" + item + "\" selected\nfrom pop-up menu.\n");
      }
   }

} // end class GUIDemo
