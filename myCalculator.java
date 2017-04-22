package test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class myCalculator  {
   private JFrame f;
   private JTextField tf =new JTextField(20);
   private long result;
   private boolean append=false;//distinguish the numbers and the character
   private char Operator='=';
   private JButton[] bt=new JButton[16];
   
   public myCalculator() {
       initComponent();
   }

   private void initComponent() {
       f = new JFrame("My calculator ");
       f.setLayout(new BorderLayout());   //The frame uses BorderLayout
       f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent evt) {
               System.exit(0);
           }
       });
       
       Panel centerPanel = new Panel();
       centerPanel.setLayout(new GridLayout(5, 3)); //The panel uses GridLayout
       
       NumberListener nl=new NumberListener();
       OperatorListener ol=new OperatorListener();

       bt[10]=new JButton("+");
       bt[11]=new JButton("-");
       bt[12]=new JButton("*");
       bt[13]=new JButton("/");
       bt[14]=new JButton("=");
       bt[15]=new JButton("c");

       for (int i=0;i<=9;i++){
           bt[i]=new JButton(String.valueOf(i));
           centerPanel.add(bt[i]);
           bt[i].addActionListener(nl);
           if (i%2==1){
              centerPanel.add(bt[(i+19)/2]);
              bt[(i+19)/2].addActionListener(ol);
           }
       }
       bt[15].addActionListener(ol);
       f.add(centerPanel, BorderLayout.CENTER);
       
       Panel northPanel = new Panel();
       
       tf.setEditable(false);
       JPanel p=new JPanel();
       p.add(tf);
       p.add(bt[15]);
       northPanel.add(p);
       f.add(northPanel, BorderLayout.NORTH);
   }
   public void go() {
       f.pack();
       f.setVisible(true);
       f.setLocationRelativeTo(null); 
   }
   
   public static void main(String[] args) {
       new myCalculator().go();
   }

  
   class NumberListener implements ActionListener{
       public void actionPerformed(ActionEvent e){
          if (!append) {
              tf.setText("");
              append=true;
          }
          String s=tf.getText();
          s+=e.getActionCommand();
          tf.setText(s);
          if (!bt[10].isEnabled()){
              for(int i=10;i<=14;i++) bt[i].setEnabled(true);
          }
       }
   }

  
   class OperatorListener implements ActionListener{
       public void actionPerformed(ActionEvent e){
           if (!append) return;
           for(int i=10;i<=14;i++) bt[i].setEnabled(false);
           String s=tf.getText();
           long num=Long.parseLong(s);//get the number of textfield
           append=false;  //set append 
           switch(Operator){
              case '+':result+=num;break;
              case '-':result-=num;break;
              case '*':result*=num;break;
              case '/':{
                 if (num==0) result=0;
                 else result/=num;
                 break;
              }
              case '=':result=num;break; 
              case 'c':tf.setText("");break;
           }
           
           tf.setText(String.valueOf(result)); 
           String op=e.getActionCommand();
           Operator=op.charAt(0); // set operator
       }
   }
}
