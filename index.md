# Unnamed LD game

## Recomended hardware

On local party in Omsk this game was played with a special controller hardware - a joystick modified to shock player with electricity on some game events.
Here is a short description of how to make a device like the one used there.

Core of the device is a ampfiler and voltage converter circuit:

![Schematics](./img/schematics-01.png)

Input of the circuit is connected to one of channels of computer's audio output.
The sound played by game is ampfiled and converted to (relatievly) high but still (relatievly) safe voltage - around 40 Volts at the output.
Output of the circuit is connected to electrodes placed on joystick's buttons.
In original device Genius F-23 joystick is used and electrodes are placed on buttons 0 and 2.
Game forces a player to hold those two buttons thus providing contact between electrodes and player's fingers.

Ampfiler is implemented using two bipolar transistors (soviet transistors КТ837 (PNP) and КТ858 (NPN) transistors were used in original device but any couple of midle-power NPN and PNP transistors will work).
Voltage conversion is performed by a transformer from old-style power supply.
The transformer used in original device has secondary coil voltage around 16 volts when primary coil voltage is 220 volts (but is connected in opposite direction).
The circuit is powered by 2 battaries made of 3 AA cells each (it's possible to use more cells in the battery but the output voltage may become much more dangerous for player).

Resistors connected between bases and collectors of transistors are tuned to get better output voltage shape.

Electrodes on joystick buttons are made by putting a wire without insulation through holes drilled in buttons.
On inner side of button the wire is soldered and connected to a insulated wires connected to output of voltage converter.

*Author of this text is not responsible for any consequences of anybody repreoducing the described device*
