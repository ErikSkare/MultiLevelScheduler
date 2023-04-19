package Main;

public class Task {
	private char name;
	
	private int priority;
	
	private int start;
	
	private int length;
	
	private int waitingTime;
	
	public Task(char name, int priority, int start, int length) {
		this.name = name;
		this.priority = priority;
		this.start = start;
		this.length = length;
		this.waitingTime = 0;
	}
	
	public final int GetStart() { return start; }
	
	public final int GetLength() { return length; }
	
	public final int GetWaitingTime() { return waitingTime; }
	
	public final char GetName() { return name; }
	
	public final int GetPriority() { return priority; }
	
	public final void Step() { 
		this.length -= 1;
	}
	
	public final void Wait() {
		this.waitingTime += 1;
	}
}
