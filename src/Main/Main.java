package Main;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			List<Task> tasks = new LinkedList<Task>();
			List<Task> oneTasks = new LinkedList<Task>();
			List<Task> zeroTasks = new LinkedList<Task>();
			
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if(line.equals("")) continue;
				
				String[] split = line.split(",");
				Task t = new Task(split[0].toCharArray()[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
				if(Integer.parseInt(split[1]) == 0)
					zeroTasks.add(t);
				else
					oneTasks.add(t);
				tasks.add(t);
			}
			
			RRScheduler s1 = new RRScheduler(oneTasks, 2);
			SRTFScheduler s0 = new SRTFScheduler(zeroTasks);
			
			List<Character> runned = new LinkedList<Character>();
			
			boolean isRunningPrimary = true;
			
			while(s1.HasTask() || s0.HasTask()) {
				boolean hasInterrupted = false;
				
				if(s1.HasRunningTask()) {
					if(!isRunningPrimary) {
						hasInterrupted = true;
						isRunningPrimary = true;
					}
					if(hasInterrupted)
						s0.Interrupt();
					Task t = s1.Step(false);
					s0.Step(true);
					if(t != null)
						runned.add(t.GetName());
				} else if(s0.HasRunningTask()) {
					Task t = s0.Step(false);
					s1.Step(true);
					isRunningPrimary = false;
					if(t != null)
						runned.add(t.GetName());
				} else {
					s0.Step(true);
					s1.Step(true);
				}
			}
			
			runned.add('@');
			
			tasks.sort((a, b) -> {
				if(a.GetStart() == b.GetStart())
					return a.GetName() - b.GetName();
				return a.GetStart() - b.GetStart();
			});
			
			for(int i = 0; i < runned.size() - 1; ++i) {
				if(!runned.get(i).equals(runned.get(i+1)))
					System.out.print(runned.get(i));
			}
			System.out.print('\n');
			for(int i = 0; i < tasks.size() - 1; ++i) {
				System.out.print(tasks.get(i).GetName() + ":" + tasks.get(i).GetWaitingTime() + ",");
			}
			System.out.print(tasks.get(tasks.size() - 1).GetName() + ":" + tasks.get(tasks.size() - 1).GetWaitingTime());
			System.out.print('\n');
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
