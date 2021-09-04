package duke;

import duke.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of tasks for duke.Duke.
 */
public class TaskList {
    private final Storage storage;
    private final List<Task> tasks;

    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = new ArrayList<>(storage.readTasks());
    }

    public void add(Task task) {
        this.tasks.add(task);
        this.saveToStorage();
    }

    public Task delete(int index) {
        if (index < 0 || index >= this.tasks.size()) {
            throw new DukeException("There is no task " + index);
        }

        Task task = this.tasks.get(index);
        this.tasks.remove(index);
        this.saveToStorage();
        return task;
    }

    public Task markAsDone(int index) {
        if (index < 0 || index >= this.tasks.size()) {
            throw new DukeException("There is no task " + index);
        }

        Task task = tasks.get(index);
        task.complete();
        this.saveToStorage();
        return task;
    }

    public int size() {
        return this.tasks.size();
    }

    public Task getTask(int index) {
        if (index < 0 || index >= this.tasks.size()) {
            throw new DukeException("There is no task " + index);
        }

        return tasks.get(index);
    }

    /**
     * Finds tasks with descriptions containing the prompt.
     * @param prompt Search string
     * @return List of results matching prompt
     */
    public List<FindResult> find(String prompt) {
        List<FindResult> results = new ArrayList<>();
        String promptLower = prompt.toLowerCase();
        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(promptLower)) {
                results.add(new FindResult(task, i));
            }
        }
        return results;
    }

    public void saveToStorage() {
        storage.saveTasks(tasks);
    }

    /**
     * Represents a found result.
     */
    public static class FindResult {
        /**
         * Task that was found.
         */
        public final Task task;

        /**
         * Index of the task in the full list.
         */
        public final int index;

        public FindResult(Task task, int index) {
            this.task = task;
            this.index = index;
        }
    }
}
