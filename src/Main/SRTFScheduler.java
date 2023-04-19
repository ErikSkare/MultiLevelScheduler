package Main;

import java.util.LinkedList;
import java.util.List;

public class SRTFScheduler extends Scheduler{
	private List<Task> tasks = new LinkedList<Task>();
	
	private List<Task> queue = new LinkedList<Task>();
	
	private Task previous = null;
	
	private int time = 0;
	
	public SRTFScheduler(List<Task> tasks) {
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
		
		if(!queue.isEmpty()) {
			Task current = queue.get(0);
			
			current.Step();
			
			for(Task t : queue)
				if(!t.equals(current))
					t.Wait();
			
			if(current.GetLength() == 0)
				queue.remove(current);
			
			previous = current;
			
			AddCurrentTasks();
			Reorder();
		}
		
		time++;
		
		return previous;
	}
	
	private void Reorder() {
		if(queue.size() == 0 || previous == null) return;
		if(previous.equals(queue.get(0)) || !queue.contains(previous)) return;
		// Ha leváltották a legelső taszkot
		queue.remove(previous);
		Place(previous);
	}

	@Override
	public void Interrupt() { 
		if(queue.size() == 0) return;
		Task a = queue.get(0);
		queue.remove(a);
		Place(a);
	}

	@Override
	public void WaitAll() {
		for(Task t : queue)
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
		return !queue.isEmpty();
	}
	
	private void AddCurrentTasks() {
		for(Task t : tasks)
			if(t.GetStart() == time)
				Place(t);
	}
	
	private void Place(Task a) {
		int i = 0;
		while(i < queue.size() && queue.get(i).GetLength() <= a.GetLength())
			i++;
		queue.add(i, a);
	}
}
