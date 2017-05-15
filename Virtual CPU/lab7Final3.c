#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char *memPt;


const unsigned int MEMORY_MAX =  0X3E80;
const unsigned int MAX32 = 0xFFFFFFFF;
const unsigned int HEX_COLUMN = 16;
const unsigned REG_NUM = 16;
const unsigned memSize = 0x4000;
const unsigned int BITS_REG = 32;
const unsigned int BITS_INST = 16;
const unsigned int SIZE_INST = 4;

unsigned SP[13];
unsigned LP[14];
unsigned long PC = 0;

unsigned long reg[16];
int signF = 0;
int zeroF = 0;
int carryF = 0;
int stopF = 0;
int irF = 0;
unsigned long mbR = 0;
unsigned long maR = 0;
unsigned long irR = 0;
unsigned long ir0 = 0;
unsigned long ir1 = 0;

unsigned char MEMORY[16384] = "";

void IsCarry(int Rn1, int Rn2)
{
        if((reg[Rn1] > (MAX32 - reg[Rn2] - carryF)))
        {
                carryF = 1;
        }
}

void IsZero(int Rd)	//set the flag if result of ADD/SUB is 0
{
	if(reg[Rd] == 0)
        {
	        zeroF = 1;
        }

}

void IsSign(int Rd)	//check MSB in register if set or not
{
	signF = reg[Rd] >> 31;
}

void IsIR()
{
	if(irR != 0)
	{
		irF = 1;
	}
}

void ResetRegisters()
{
	int i;
	for(i = 0; i < 16; i++)
	{
		reg[i] = 0;	
	}

	PC = 0;
	maR = 0;
	mbR = 0;
	ir0 = 0;
	ir1 = 0;
	irR = 0;
	signF = 0;
	zeroF = 0;
	carryF = 0;
	stopF = 0;
	irF = 0;
	
}

void execute(char *opCode)
{
	char *operand;
	if(strcmp(opCode, "STOP") == 0)
        {
                PC++;
                stopF = 1;
                printf("\nExecuted STOP instruction\n\n");
        }

	
	if(strcmp(opCode, "LDR") == 0)		//comparing the opCode
	{
		PC++;

		//splitting the operands into separate strings 
		operand = strtok(NULL, "");
		char *op1 = strtok(operand, " ,R[]#");
		char *op2 = strtok(NULL, " ,R[]#");
		char *op3 = strtok(NULL, " ,R[]#");
			
		//convert the above strings to integers
		int Rd = atoi(op1);
		int Rn = atoi(op2); 
			
		//convert the value to load to HEX
		unsigned long loadNum = (unsigned long)strtoul(op3, NULL, 16);
								
		reg[Rd] = reg[Rn] + loadNum;	//LOAD instruction

		printf("\nLOADED 0x%X into R%d", loadNum, Rd);					
	}

	if(strcmp(opCode, "STR") == 0)
        {
                PC++;
                //splitting the operands into separate strings
                operand = strtok(NULL, "");
                char *op1 = strtok(operand, " ,R[]#");
                char *op2 = strtok(NULL, " ,R[]#");
                char *op3 = strtok(NULL, " ,R[]#");

                //convert the above strings to integers
                int Rd = atoi(op1);
                int Rn = atoi(op2);

                //convert the value to load to HEX
                unsigned long storeNum = (unsigned long)strtoul(op3, NULL, 16);

                reg[Rd] = reg[Rn] + storeNum;    //STORE instruction

                printf("\nSTORED 0x%X into R%d", storeNum, Rd);
        }

		
	if(strcmp(opCode, "ADD") == 0)
	{
		PC++;

		//get the registers needed for adding
		operand = strtok(NULL, "");
		char *first = strtok(operand, " ,R");
		char *second = strtok(NULL, " ,R");
		char *third = strtok(NULL, " R");
			
		//convert the strings above to integers for accessing register array
		int Rd = atoi(first);
		int Rn1 = atoi(second);
		int Rn2 = atoi(third);
			
		reg[Rd] = reg[Rn1] + reg[Rn2];
			
		IsCarry(Rn1, Rn2);
		IsZero(Rd);
		IsSign(Rd);	

		printf("\n\nADDED 0x%X and 0x%X\n", reg[Rn1], reg[Rn2]);
		printf("Result in R%d: 0x%X\n", Rd, reg[Rd]);
	}
	
	if(strcmp(opCode, "SUB") == 0)
    	{
		PC++;		

		operand = strtok(NULL, "");
		char *first = strtok(operand, " ,R");
		char *second = strtok(NULL, " ,R");
		char *third = strtok(NULL, " R");

		int Rd = atoi(first);
		int Rn1 = atoi(second);
		int Rn2 = atoi(third);

		reg[Rd] = reg[Rn1] - reg[Rn2];

		printf("\nSUBTRACTED 0x%X and 0x%X\n", reg[Rn1], reg[Rn2]);
                printf("Result in R%d: 0x%X\n\n", Rd, reg[Rd]);

    	}
}

void fetch(void *loadBuff) 
{
	char *line;
	char *opCode;	

	//read line by line from the buffer(Memory) to get the opcode(instructions)
	while(line = strtok_r(loadBuff, "\n", &loadBuff))
	{
		opCode = strtok(line, " ");	//split the opcode and operand
		
		IsIR();		//check if the instruction register is active
		mbR = (unsigned long)line;
                irR = (unsigned long)line;		
		execute(opCode);

		if(stopF == 1)
		{
			break;
		}
	}
	reg[15] = PC;
}

void DisplayRegisters()
{
	int regSize = 16;
	reg[regSize];

	printf("R0\t\tR1\t\tR2\t\tR3\t\t\n");
	printf("%08X\t%08X\t%08X\t%08X\n", reg[0], reg[1], reg[2], reg[3]);
	printf("R4\t\tR5\t\tR6\t\tR7\t\t\n");
	printf("%08X\t%08X\t%08X\t%08X\n", reg[4], reg[5], reg[6], reg[7]);
	printf("R8\t\tR9\t\tR10\t\tR11\t\t\n");
	printf("%08X\t%08X\t%08X\t%08X\n", reg[8], reg[9], reg[10], reg[11]);
	printf("R12\t\tR13(SP)\t\tR14(LR)\t\tR15(PC)\t\t\n");
	printf("%08X\t%08X\t%08X\t%08X\n", reg[12], reg[13], reg[14], reg[15]);
 	printf("MAR\t\tMBR\t\tSIGN\t\tCARRY\t\tZERO\n");
	printf("%08X\t%08X\t%d\t\t%d\t\t%d\n", maR, mbR, signF, carryF, zeroF);
 	printf("IR\t\tIR FLAG\t\tIR0\t\tIR1\t\t\n");
	printf("%08X\t%d\t\t%04X\t\t%04X\n", irR, irF, ir0, ir1);
}



int LoadFile(void * memory, unsigned int max)
{
	char fileName[100];
	FILE *file;
	long fSize;

        printf("\n-----------LOAD FILE---------------\n");
	
	int check = 0;
	while(check == 0)
	{
		printf("enter a file name: ");
		scanf("%s", fileName);
		file = fopen(fileName, "r");

		if(file != NULL)
		{			
			fseek(file, 0, SEEK_END);
			fSize = ftell(file);
			fseek(file, 0, SEEK_SET);

			fread(memory, max, 1, file);
			fclose(file);	
			printf("Content: \n%s\n", memory);
	
			check = 1;
			memPt = memory;	//initialize the section of memory for memory Dumping
			
			return (int)fSize;
			//printf("Number of Bytes read: %X Hex, %d\n", (int)fSize, (int)fSize);			

			//return memory;
		}
		else 
		{
			printf("\nFile does not exist, try again\n\n");
		}
	}			
}


void WriteFile(void * memory)
{
	FILE *newFile;
	char fileName[100];
	char readBuffer[5000];
	int byteNum;

	printf("\n---------WRITE FILE-----------\n");
	printf("enter a file name: ");
	scanf("%s", fileName);
	printf("enter of Bytes to write: ");
	scanf("%d", &byteNum);


	newFile = fopen(fileName, "w");
	fwrite(memory, 1, byteNum, newFile);
	fclose(newFile);

	newFile = fopen(fileName, "r");
	//fseek(newFile, 0, SEEK_END);
	fread(readBuffer, sizeof(readBuffer), 1, newFile);
	fclose(newFile);

	printf("Contents: %s\n", readBuffer);
	

}

void MemDump(void *memptr, unsigned offset, unsigned length) 
{
	int i;
	int currentPost;
	unsigned total;

	total = offset + length;

	printf("\n-----------MEMORY DUMP---------------\n");
	if(offset <= 5000 && total <= 5000)
	{
		for(i = offset; i < total; i+=16) 
		{
			currentPost = i;
			printf("\n%04X ", i);	//print the offset

			//print the Hex values at address locations
			while(currentPost < (i + 16))
			{
				if(currentPost != total)	//check if end of dump
				{
					printf("%02X ", ((unsigned char *)memptr)[currentPost]);	//print the hex numbers
					currentPost++;
				}
			}

			//printing the ASCII value at address location
			printf("\n      ");
			currentPost = i;
			while(currentPost < (i + 16))
			{
				if(currentPost != total)
				{
					if(isprint(((unsigned char*)memptr)[currentPost]))	//check if value printable or not 
					{
						printf("%c  ", ((unsigned char*)memptr)[currentPost]);
					}
					else
					{
						printf(".  ");
					}

					currentPost++;
				}
			}
			printf("\n");
		}
		printf("\n");

	}
	else
	{
		printf("\nExceeded max buffer size, 5000(1388 HEX)\n");
	}
}

void MemMod(void *memptr) 
{
	unsigned int address;
	unsigned char newValue;
	int check = 0;

	printf("\n-----------MEMORY MODIFY---------------\n");

	while(check == 0)
	{
		//entering address to change	
		printf("\nEnter a starting address(HEX): ");
		scanf(" %x", &address);

		if(address <= 0x1388)
		{
			printf("value(HEX) at 0x%X, is: %02X\n", address, ((unsigned char *)memptr)[address]);
			printf("value(ASCII) at 0x%X, is: %c\n", address, ((unsigned char *)memptr)[address]);

			//new value
			printf("\nEnter a new value: ");
			scanf(" %c", &newValue);

			if(newValue != '.')
			{
				((unsigned char *)memptr)[address] = newValue;
				printf("value(HEX) at 0x%X, is: %02X\n", address, ((unsigned char *)memptr)[address]);
				printf("value(ASCII) at 0x%X, is: %c\n", address, ((unsigned char *)memptr)[address]);
			}	
			else
			{
				printf("\nInvalid value entered, Exiting\n");
			}
			check = 1;
		}
		else 
		{
			printf("\nStarting address Exceeds buffer size, 5000(1388 HEX). Try again.......\n");
		}
	}
}


void Help()
{
	//char letter;
	
	printf("\nlist of all commands\n");
	printf("d\tdump memory\n");
    	printf("g\tgo -run the entire program\n");
    	printf("l\tload a file into memmory\n");
    	printf("m\tmemory modify\n");
    	printf("q\tquit\n");
    	printf("r\tdisplay registers\n");
    	printf("t\ttrace - execute one instruction\n");
    	printf("w\twrite file\n");
    	printf("z\treset all registers to zero\n");
    	printf("?, h\tdisplay list of commands\n");
	
	//scanf("%c", &letter);
	
	//return letter;
}



int main(void)
{
	char letter;
	char bufferL[5000];
	char bufferW[5000] = "testing write file\n";
	int BuffSize;
	int size;
	int check = 0;
	
	unsigned int offset;
	unsigned int length;

	char *pBufferL = bufferL;
	char *pBufferW = bufferW;

	memPt = bufferL;
	BuffSize = sizeof(bufferL);
	
	Help();
	
	while(check == 0)
	{
	
	printf("\nEnter a command: ");
	scanf(" %c", &letter);
	
	switch(letter)
	{
		case 'd':
		case 'D':
			printf("\nEnter an offset(HEX): ");
			scanf(" %x", &offset);
			printf("Enter a length(HEX): ");
			scanf(" %x", &length);

			MemDump(memPt, offset, length);
			break;
		case 'g':
		case 'G':
			ResetRegisters();
			fetch(bufferL);
			DisplayRegisters();
			break;
		case 'l':
		case 'L':
			size = LoadFile(pBufferL, BuffSize);
			printf("Number of Bytes read: %X Hex, %d\n", size, size);
			break;
		case 'm':
		case 'M':
			MemMod(memPt);
			break;
		case 'q':
		case 'Q':
			check = 1;
			break;
		case 'r':
		case 'R':
			DisplayRegisters();
			break;
		case 't':
		case 'T':
			break;
		case 'w':
		case 'W':
			WriteFile(pBufferW);
			break;
		case 'z':
		case 'Z':
			ResetRegisters();
			break;
		case 'h':
		case 'H':
		case '?':
			Help();
			break;
		default:
			printf("\n\nInvalid command, try again\n");	
	}

	}

	return 0;
}






