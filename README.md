## What is TaskTrackerCLI?
### TaskTrackerCLI is a project that I got from roadmap.sh, I'm using it as a way to learn more about back-end.

## How to use?
### First, you i'll need java 17 on your PC and keep in mind that is a CLI app.
### For run this, use: **java -jar TaskTrackerCLI.jar [argument-0] [optional-sub-arguments]**

## List of arguments:
 - add [task description] -> Add a new task in database
 - update [task id] [new task description] -> Update task with this add, add a new description
 - delete [task id] -> Delete task with this id
 - mark-in-progress [task id] -> Mark task with this id in progress
 - mark-done [task id] -> Mark task with this id as done
 - list -> List all tasks in database
 - list done -> List all tasks with status: DONE
 - list todo -> List all tasks with status: TO-DO
 - list in-progress -> List all tasks with status: in progress
 
## How to run with arguments? 
### Example: **java -jar TaskTrackerCLI.jar add "Get pizza for me and my friends!"**
