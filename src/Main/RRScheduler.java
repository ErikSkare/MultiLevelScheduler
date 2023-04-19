package Main;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RRScheduler extends Scheduler {
	private List<Task> tasks = new LinkedList<Task>();
	
	private Queue<Task> fifo = new LinkedList<Task>();
	
	private int slice;
	
	private int time = 0;
	
	private int runningTime = 0;
	
	public RRScheduler(List<Task> tasks, int slice) {
		this.slice = slice;
		this.tasks = tasks;
	}

	@Override
	public Task Step(boolean onlyRead) {
		if(onlyRead) {
			WaitAll();
			AddCurrentTasks();
			time++;
			return null;
		}
		
		Task current = fifo.peek();
		
		// Ha nem üres, akkor léptet.
		if(!fifo.isEmpty()) {
			current.Step();
			
			for(Task t : fifo)
				if(!t.equals(current))
					t.Wait();
			
			runningTime++;
		}
		
		AddCurrentTasks();
		
		if(!fifo.isEmpty()) {
			if(current.GetLength() == 0) {
				fifo.remove();
				runningTime = 0;
			} 
			else if(runningTime == slice) {
				fifo.add(fifo.remove());
				runningTime = 0;
			}
		}
		
		time++;
		
		return current;
	}

	@Override
	public void Interrupt() {}
	
	@Override
	public void WaitAll() {
		for(Task t : fifo)
			t.Wait();
	}
	
	@Override
	public boolean HasTask() {
		boolean flag = false;
		for(Task t : tasks)
			flag |= (t.GetLength() > 0);
		return flag;
	}
	
	@Override
	public boolean HasRunningTask() {
		return !fifo.isEmpty();
	}
	
	private void AddCurrentTasks() {
		for(Task t : tasks)
			if(t.GetStart() == time)
				fifo.add(t);
	}
}
