package Main;

public abstract class Scheduler {
	abstract public Task Step(boolean onlyRead);
	
	abstract public void Interrupt();
	
	abstract public void WaitAll();
	
	abstract public boolean HasTask();
	
	abstract public boolean HasRunningTask();
}
