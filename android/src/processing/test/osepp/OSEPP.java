package processing.test.osepp;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import android.content.Intent; 
import android.os.Bundle; 
import ketai.net.bluetooth.*; 
import ketai.ui.*; 
import ketai.net.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class OSEPP extends PApplet {







PImage noPress;
PImage A_PRESS;
PImage B_PRESS;
PImage C_PRESS;
PImage D_PRESS;
PImage DOWN_PRESS;
PImage UP_PRESS;
PImage LEFT_PRESS;
PImage RIGHT_PRESS;



boolean overBox = false;
float bx;
float by;
float screenX;
float screenY;


PFont fontMy;
boolean bReleased = true; //no permament sending when finger is tap
KetaiBluetooth bt;
boolean isConfiguring = true;
String info = "";
KetaiList klist;
ArrayList devicesDiscovered = new ArrayList();
//buttons right
byte [] A = { 'A'}; //send on to Arduino
byte [] B = { 'B'}; //send on to Arduino
byte [] C = { 'C'}; //send on to Arduino
byte [] D = { 'D'}; //send on to Arduino

//joystick
byte [] left = { 'L'}; //send LOW to Arduino
byte [] up = { 'U'}; //send LOW to Arduino
byte [] down = { 'd'}; //send LOW to Arduino
byte [] right = { 'R'}; //send LOW to Arduino

byte [] OFF = { 'O'}; //send LOW to Arduino

byte  [] pinNum;
int pinCycle;
 int diameter = 170; //the width of touch space for buttons


//********************************************************************
// The following code is required to enable bluetooth at startup.
//********************************************************************

public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 bt = new KetaiBluetooth(this);
}

public void onActivityResult(int requestCode, int resultCode, Intent data) {
 bt.onActivityResult(requestCode, resultCode, data);
}
////////////////////////////////////////////

public void setup() {
 
 //orientation(LANDSCAPE);
 bx = displayWidth/2;
 by = displayHeight/2;
 screenX = displayWidth;
 screenY = displayHeight;
 
  noPress = loadImage("noPress.png");
 A_PRESS = loadImage("A_PRESS.png");
  B_PRESS = loadImage("B_PRESS.png");
  C_PRESS = loadImage("C_PRESS.png");  
  D_PRESS = loadImage("D_PRESS.png");
  DOWN_PRESS= loadImage("DOWN.png");
  UP_PRESS= loadImage("UP.png");
  LEFT_PRESS= loadImage("LEFT.png");
  RIGHT_PRESS= loadImage("RIGHT.png");
  
  noPress.resize(displayWidth, displayHeight);
    A_PRESS.resize(displayWidth, displayHeight);
    B_PRESS.resize(displayWidth, displayHeight);
    C_PRESS.resize(displayWidth, displayHeight);
    D_PRESS.resize(displayWidth, displayHeight);

    DOWN_PRESS.resize(displayWidth, displayHeight);
    UP_PRESS.resize(displayWidth, displayHeight);
    LEFT_PRESS.resize(displayWidth, displayHeight);
    RIGHT_PRESS.resize(displayWidth, displayHeight);

 
  
 //start listening for BT connections
 bt.start();
 //at app start select device\u2026
 isConfiguring = true;
 //font size
 fontMy = createFont("Arial Bold.ttf", 45);
 textFont(fontMy);
}

public void draw() {
   // background(#00878F);
background(0xff62AEB2);



  /*
    float slideDist = constrain(mouseX, 40, width/1.3);
float PWM = map(slideDist, 40, width/1.3 , 0, 255);
 //at app start select device
 int PWM_SEND = int(PWM);
 fill(0);

rect(slideDist, height/1.4, 20, 40);
strokeWeight(10);
line(40, height/1.37, width/1.3, height/1.37);
byte [] PWM_VAL = new byte [PWM_SEND] ;
bt.broadcast(PWM_VAL);
*/

image(noPress, 0,displayHeight/4,displayWidth, displayHeight/1.5f);
textSize(32);
fill(0);


/*  Where are we pressing ? 
text("X position = ", bx/6, by/5); 
text(mouseX, bx/2, by/5); 
text("Y position = ", bx/6, by/3); 
text(mouseY, bx/2, by/3);
*/


if (mousePressed == true){
  
pressLoc();

} else{
}
     
 if (isConfiguring)
 {
  ArrayList names;
    //background(#D63222);
background(0xff00878F);
  klist = new KetaiList(this, bt.getPairedDeviceNames());
  isConfiguring = false;
 }
 else
 {
   //NEXUS 7 RESOLUTION 1920\u00d71200
   
   
   // A,B,C,D Buttons
   
 if (mouseX > (screenX/1.269f)-diameter && mouseX < (screenX/1.269f)+diameter && 
      mouseY > (screenY/1.38f)-diameter && mouseY < (screenY/1.38f)+diameter && mousePressed==true) {

image(D_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(D);
} 

else if (mouseX > (screenX/1.112f)-diameter && mouseX < (screenX/1.112f)+diameter && 
      mouseY > (screenY/1.69f)-diameter && mouseY < (screenY/1.69f)+diameter && mousePressed==true ) {
  image(C_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(C);
}

else if (mouseX > (screenX/1.268f)-diameter && mouseX < (screenX/1.268f)+diameter && 
      mouseY > (screenY/2.25f)-diameter && mouseY < (screenY/2.25f)+diameter && mousePressed==true ) {
  image(B_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(B);
}

else if (mouseX > (screenX/1.5f)-diameter && mouseX < (screenX/1.5f)+diameter && 
      mouseY > (screenY/1.7f)-diameter && mouseY < (screenY/1.7f)+diameter && mousePressed==true ) {
  image(A_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(A);
}

//joystick buttons
   //NEXUS 7 RESOLUTION 1920\u00d71200
   
else if (mouseX > (screenX/5.42f)-diameter && mouseX < (screenX/5.42f)+diameter && 
      mouseY > (screenY/1.4f)-diameter && mouseY < (screenY/1.4f)+diameter && mousePressed==true ) {
  image(DOWN_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(down);
}
else if (mouseX > (screenX/12.15f)-diameter && mouseX < (screenX/12.15f)+diameter && 
      mouseY > (screenY/1.4f)-diameter && mouseY < (screenY/1.4f)+diameter && mousePressed==true ) {
  image(LEFT_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(left);
}
else if (mouseX > (screenX/5.5f)-diameter && mouseX < (screenX/5.5f)+diameter && 
      mouseY > (screenY/2.26f)-diameter && mouseY < (screenY/2.26f)+diameter && mousePressed==true ) {
  image(UP_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(up);
}
//right press
else if (mouseX > (screenX/3.47f)-diameter && mouseX < (screenX/3.47f)+diameter && 
      mouseY > (screenY/1.78f)-diameter && mouseY < (screenY/1.78f)+diameter && mousePressed==true ) {
  image(RIGHT_PRESS,0,displayHeight/4,displayWidth, displayHeight/1.5f);
  bt.broadcast(right);
}

else
  {
  bt.broadcast(OFF);
  }
  
  
  
}
 }


public void pressLoc(){
  
  
 fill( 126, 126);
ellipse(mouseX, mouseY, diameter, diameter);
}

public boolean mouseOverRect() { // Test if mouse is over square
  return ((mouseX >= (width/2-100)) && (mouseX <= (width/2+100)) && (mouseY >= (height/2-100)) && (mouseY <= (height/2+100)));
}

public void onKetaiListSelection(KetaiList klist) {
 String selection = klist.getSelection();
 bt.connectToDeviceByName(selection);
 //dispose of list for now
 klist = null;
}

//Call back method to manage data received
public void onBluetoothDataEvent(String who, byte[] data) {
 if (isConfiguring)
 return;
 //received
 info += new String(data);
 //clean if string to long
 if(info.length() > 150)
 info = "";
}

/* ARDUINO CODE */  
  public void settings() {  size(displayWidth, displayHeight); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "OSEPP" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
