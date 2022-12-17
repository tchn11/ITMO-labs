#include <sys/ioctl.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <errno.h>
#include <fcntl.h>
#include <sys/poll.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


struct thread_msg{
    unsigned long		sp;

	unsigned short		es;
	unsigned short		ds;
	unsigned short		fsindex;
	unsigned short		gsindex;

	unsigned long		fsbase;
	unsigned long		gsbase;

	unsigned long           virtual_dr6;

	unsigned long           ptrace_dr7;

	unsigned long		cr2;
	unsigned long		trap_nr;
	unsigned long		error_code;

	unsigned int        pkru;
};

struct net_msg{
    unsigned int		flags;
    unsigned long		mem_end;
	unsigned long		mem_start;
	unsigned long		base_addr;
    int                 irq;
    unsigned long       state;
    unsigned char       if_port;
    unsigned char       dma;
};

struct message {
    struct thread_msg th;
    struct net_msg nt;
    pid_t pid;
};

#define WR_VALUE _IOWR('a','a',struct message*)

int main(int argc, char *argv[]) {
	struct message msg;
	int fde;

 	msg.pid = atoi(argv[1]);

	printf("\nOpening Driver\n");
	printf("%d\n", msg.pid);
	fde = open("/dev/etx_device", O_RDWR);

	if(fde < 0) {
		printf("Cannot open device file...\n");
		printf("%d\n", errno);
		return 0;
	}

	printf("Writing data to Driver\n");
	ioctl(fde, WR_VALUE, (struct message*) &msg);

	printf ("Net flags: %ld\n", msg.nt.flags);
	printf ("Net mem end: %ld\n", msg.nt.mem_end);
	printf ("Net mem start: %ld\n", msg.nt.mem_start);
	printf ("Net base addr: %ld\n", msg.nt.base_addr);
	printf ("Net irq: %ld\n", msg.nt.irq);
	printf ("Net dma: %ld\n", msg.nt.dma);
	printf ("Net flags: %ld\n", msg.nt.flags);
	printf ("Net port (if exist): %ld\n", msg.nt.if_port);
	printf ("Net state: %ld\n", msg.nt.state);
	printf ("\n");

	printf ("Thread cr2: %ld\n", msg.th.cr2);
	printf ("Thread ds: %ld\n", msg.th.ds);
	printf ("Thread error_code: %ld\n", msg.th.error_code);
	printf ("Thread es: %ld\n", msg.th.es);
	printf ("Thread fsbase: %ld\n", msg.th.fsbase);
	printf ("Thread fsindex: %ld\n", msg.th.fsindex);
	printf ("Thread gsbase: %ld\n", msg.th.gsbase);
	printf ("Thread gsindex: %ld\n", msg.th.gsindex);
	printf ("Thread pkru: %ld\n", msg.th.pkru);
	printf ("Thread ptrace_dr7: %ld\n", msg.th.ptrace_dr7);
	printf ("Thread sp: %lu\n", msg.th.sp);
	printf ("Thread trap_nr: %ld\n", msg.th.trap_nr);
	printf ("Thread cirtual_dr6: %ld\n", msg.th.virtual_dr6);

	printf("\nClosing Driver\n");
	close(fde);
}