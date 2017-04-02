#ifndef COMMUNICATION_H
#define COMMUNICATION_H

/* communication */

#define MSG_SIZE 4

bool sending = false;
bool receiving = false;

byte in_message[MSG_SIZE];
byte out_message[MSG_SIZE];

void print_message(byte *data);

#endif
