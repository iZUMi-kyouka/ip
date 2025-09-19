# Rumi User Guide

[](./docs/Ui.png)
Rumi is a your cute personal assistant that will keep track of your daily to-dos!

## Adding todos
Todos are the most basic type of task. It is simply the task name and its status.

Example: `todo clean bedroom`
Adds a todo 'clean bedroom' whose status is pending.

```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Right away, Master! I've added this to your to-do list:
        [T][ ] clean bedroom
    You now have 5 task(s) awaiting your attention~
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Adding deadlines
Deadlines are a type of task that must be done by a certain time. Deadlines must contain both the task name and the deadline by which it must be done.

Example: `deadline submit CS2103T iP /by 19/9/2025 4pm`
Adds a deadline 'submit CS2103T iP' which is set to be due on 19 September 2025, 4pm.

```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Right away, Master! I've added this to your to-do list:
        [D][ ] submit CS2103T iP (by: 19-09-2025 04:00pm)
    You now have 4 task(s) awaiting your attention~
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Adding events
Events are todos that also include the time from when the task takes place, and the time the said task ends. These properties are called 'from' and 'to'.

Example: `event Minami concert /from 19/9/2025 8pm /to 19/9/2025 11pm` 
Adds an event from 8pm to 11pm on 19 September 2025.

```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Right away, Master! I've added this to your to-do list:
        [D][ ] submit CS2103T iP (by: 19-09-2025 04:00pm)
    You now have 4 task(s) awaiting your attention~
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Listing all tasks
Shows the current task list containing all todos, deadlines, and events.

Example: `list`
Marks the task with the index 1 as not done.
```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    You have entrusted me with 5 task(s), Master~
    Here's the list, all neat and tidy just for you ♥.
            1. [D][ ] submit CS2103T iP (by: 19-09-2025 04:00pm)
            2. [T][ ] clean bedroom
            3. [E][ ] YOASOBI concert (from: 21-12-2025 08:00pm --> to: 21-12-2025 11:00pm)
            4. [D][ ] IT2900 discussion forum (by: tonight)
            5. [D][ ] add more tests to iP (by: next Monday)
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Deleting a task 
Deleting a task removes it from the to-do list, regardless of its status.

Assuming the state of the task list is as shown below,
```
list
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    You have entrusted me with 3 task(s), Master~
    Here's the list, all neat and tidy just for you ♥.
            1. [D][ ] submit CS2103T iP (by: 19-09-2025 04:00pm)
            2. [T][ ] clean bedroom
            3. [E][ ] Minami concert (from: 19-09-2025 08:00pm --> to: 19-09-2025 11:00pm)
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

Example: `delete 3`
Removes the task with the index number 3.
```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Roger, Master! I've deleted this from your to-do list:
        [E][ ] Minami concert (from: 19-09-2025 08:00pm --> to: 19-09-2025 11:00pm)
    You now have 2 task(s) awaiting your attention~
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```


## Marking a task as done
Marking a task changes its status from pending to done.

Assuming the state of the task list is as shown below,
```
list
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    You have entrusted me with 2 task(s), Master~
    Here's the list, all neat and tidy just for you ♥.
            1. [D][ ] submit CS2103T iP (by: 19-09-2025 04:00pm)
            2. [T][ ] clean bedroom
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

Example: `mark 1`
Marks the task with the index 1 as done.
```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Wonderful! I've marked this task as complete, Master~
        ✔ [D][X] submit CS2103T iP (by: 19-09-2025 04:00pm)
    You're doing amazing!
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Unmarking a task marked as done
Unmarking a done task changes its state from done back to pending.

Assuming the state of the task list is as shown below,
```
list
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    You have entrusted me with 2 task(s), Master~
    Here's the list, all neat and tidy just for you ♥.
            1. [D][X] submit CS2103T iP (by: 19-09-2025 04:00pm)
            2. [T][ ] clean bedroom
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

Example: `unmark 1`
Marks the task with the index 1 as not done.
```
unmark 1
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Understood, Master. I've marked this task as not done yet~
        ✘ [D][ ] submit CS2103T iP (by: 19-09-2025 04:00pm)
    Let me know when it’s done!
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Exiting the application
Exit the application by saying a bye to Rumi!

Example: `bye`
Exits the Rumi chatbot.
```
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Thank you for allowing me to serve you today, Master. I shall await your return with great anticipation~
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```