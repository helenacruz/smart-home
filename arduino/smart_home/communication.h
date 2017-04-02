#ifndef COMMUNICATION_H
#define COMMUNICATION_H

/* communication */

#define MSG_SIZE 6

bool sending = false;
bool receiving = false;

char in_message[MSG_SIZE];
char out_message[MSG_SIZE];

void print_message(char *data);

#endif
